package code;

import libs.Leer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeerXML_DOM {

    public static void leerArchivo(){
        //Path p = Path.of(Leer.pedirCadena("Introduzca la ruta del archivo xml a leer: "));
        Path p = Path.of("src/resources/concesionario.xml");
        if (Files.exists(p)) {
            if (p.toString().endsWith(".xml")) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder parser = factory.newDocumentBuilder();
                    Document document = parser.parse(p.toFile());

                    //accedemos al contenido del xml con nodos o con elementos
                    Element raiz = document.getDocumentElement();
                    System.out.println("El elemento raiz del archivo es " + raiz.getNodeName());
                    NodeList nodosDoc = raiz.getChildNodes();
                    //recorro los nodos elemento del nodo raiz concesionario -- <coche>
                    for(int i = 0; i<nodosDoc.getLength(); i++){
                        if(nodosDoc.item(i).getNodeType() == Node.ELEMENT_NODE){
                            System.out.println("Coche " + (i+1) + ":");
                            //recorro de nuevo los nodos elemento de <coche>
                            NodeList datosCoche = nodosDoc.item(i).getChildNodes();
                            for(int j = 0; j<datosCoche.getLength();j++){
                                //solo recorre los nodos elemento
                                if(datosCoche.item(j).getNodeType()==Node.ELEMENT_NODE){
                                    //printeo la etiqueta
                                    System.out.println(nodosDoc.item(i).getNodeName());
                                    //printeo el contenido de texto de la etiqueta
                                    System.out.println(nodosDoc.item(i).getTextContent());
                                    System.out.println();
                                }
                            }
                        }
                    }

                    System.out.println("--------------------------------------------------------------------------");

                    //y si leemos conociendo la estructura del documento, podemos acceder directamente
                    //a los nodos con la informacion que nos interesa
                    NodeList coches = raiz.getElementsByTagName("coche");
                    NodeList marcas = raiz.getElementsByTagName("marca");
                    NodeList modelos = raiz.getElementsByTagName("modelo");
                    NodeList cilindradas = raiz.getElementsByTagName("cilindrada");

                    ArrayList<Coche> arrayCoches = new ArrayList<>();
                    Coche cocheAux;

                    for(int i=0; i< marcas.getLength(); i++){
                        System.out.println("Coche " + (i+1) + ":");
                        cocheAux = new Coche();
                        Element coche = (Element) coches.item(i);
                        System.out.println("\t ID: " + coche.getAttribute("id"));
                        cocheAux.setID(Integer.valueOf(coche.getAttribute("id")));
                        System.out.println("\t Marca: " + marcas.item(i).getTextContent());
                        cocheAux.setMarca(marcas.item(i).getTextContent());
                        System.out.println("\t Modelo: " + modelos.item(i).getTextContent());
                        cocheAux.setMarca(modelos.item(i).getTextContent());
                        System.out.println("\t Cilindradas: " + cilindradas.item(i).getTextContent());
                        cocheAux.setCilindrada(Double.parseDouble(cilindradas.item(i).getTextContent()));
                        arrayCoches.add(cocheAux);
                    }

                    for(Coche c : arrayCoches){
                        System.out.println(c.getMarca());
                    }
                } catch (ParserConfigurationException e) {
                    System.err.println("No se ha podido crear el archivo: " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("Error al leer el archivo: " + e.getMessage());
                } catch (SAXException e){
                    System.out.println("No se ha podido parsear el archivo: " + e.getMessage());
                }
            }else{
                System.out.println("No es un xml");
            }
        } else {
            System.out.println("El archivo no existe: " + p);
        }
    }
}
