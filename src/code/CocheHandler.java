package code;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class CocheHandler extends DefaultHandler {

    private ArrayList<Coche> coches = new ArrayList<>();
    private Coche cocheAux;

    //para almacenar el texto contenido en un nodo texto
    private StringBuilder buffer = new StringBuilder();

    //para que nos devuelva un array de coches
    public ArrayList<Coche> getCoches() {
        return coches;
    }

    //qName = nombre etiqueta

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName){
            case "coches":
                break;
            case "coche":
                cocheAux = new Coche();
                break;
            case "marca", "modelo", "cilindrada":
                //cuando se llega al cierre de la etiqueta se vacia el buffer
                buffer.delete(0, buffer.length());
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName){
            case "coches":
                break;
            case "coche":
                coches.add(cocheAux);
                break;
            case "marca":
                //almaceno el contenido del buffer
                cocheAux.setMarca(buffer.toString());
                break;
            case "modelo":
                cocheAux.setModelo(buffer.toString());
                break;
            case "cilindrada":
                cocheAux.setCilindrada(Double.parseDouble(buffer.toString()));
                break;
        }
    }

    //se lee el texto de una etiqueta
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }

}
