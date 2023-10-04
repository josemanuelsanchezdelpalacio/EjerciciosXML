package code;

import libs.Leer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.print.attribute.Attribute;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.util.ArrayList;

import static libs.FicheroEscribible.ficheroEscribible;

public class EscribirXML_DOM {

    public static void escribir(){

        Coche coche1 = new Coche(1, "Peugeot", "306", 1.9 );
        Coche coche2 = new Coche(2, "Mercedes", "GLA", 1.7 );
        Coche coche3 = new Coche(3, "Renault", "Twingo", 1.1 );
        ArrayList<Coche> coches = new ArrayList<>();
        coches.add(coche1);
        coches.add(coche2);
        coches.add(coche3);

        Path p = Path.of(Leer.pedirCadena("Introduce ruta fichero: "));

        if (ficheroEscribible(p)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                //creamos el document vacio
                Document document = builder.newDocument();
                //metemos en el document los elementos de xml
                //elemento raiz
                Node nodoRaiz = document.createElement("coches");
                document.appendChild(nodoRaiz);

                //creamos tantos elementos coches como coches tenemos
                for(Coche coche : coches){
                    //creamos una etiqueta coche por coche
                    Element eticCoche = document.createElement("coche");
                    nodoRaiz.appendChild(eticCoche);

                    /*Element eticMarca = document.createElement("marca");
                    eticCoche.appendChild(eticMarca);
                    eticMarca.appendChild(document.createTextNode(coche.getMarca()));
                    Element eticModelo = document.createElement("modelo");
                    eticCoche.appendChild(eticModelo);
                    eticModelo.appendChild(document.createTextNode(coche.getModelo()));
                    Element eticCilin = document.createElement("cilindrada");
                    eticCoche.appendChild(eticCilin);
                    eticCilin.appendChild(document.createTextNode(String.valueOf(coche.getCilindrada())));*/

                    //creo y añado las etiquetas
                    crearElemento(document, eticCoche, "marca", coche.getMarca());
                    crearElemento(document, eticCoche, "modelo", coche.getModelo());
                    crearElemento(document, eticCoche, "cilindrada", String.valueOf(coche.getCilindrada()));

                    //añadir atributo
                    eticCoche.setAttribute("ID", String.valueOf(coche.getID()));

                    documentToXML(document, p);

                }

            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //metodo para crear un elemento y añadirlo al padre
    private static void crearElemento(Document document, Element padre, String etiqueta, String contenido) {
        Element elemento = document.createElement(etiqueta);
        elemento.appendChild(document.createTextNode(contenido));
        padre.appendChild(elemento);
    }

    static void documentToXML(Document document, Path p){
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(document);
            Result result = new StreamResult(p.toFile());
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http:xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
