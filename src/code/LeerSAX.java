package code;

import libs.Leer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static libs.FicheroEscribible.ficheroEscribible;

public class LeerSAX {

    public static void leer() {
        Path p = Path.of(Leer.pedirCadena("Introduce ruta fichero: "));
        ArrayList<Coche> cochesXML = new ArrayList<>();

        if (ficheroEscribible(p)) {
            SAXParserFactory saxPF = SAXParserFactory.newInstance();
            CocheHandler cocheHandler = new CocheHandler();
            try {
                SAXParser parser = saxPF.newSAXParser();
                parser.parse(p.toFile(),cocheHandler);
                cochesXML = cocheHandler.getCoches();
                for (Coche c : cochesXML){
                    System.out.println(c.toString());
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
