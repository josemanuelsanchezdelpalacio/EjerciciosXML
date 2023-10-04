import code.EscribirXML_DOM;
import code.LeerSAX;
import code.LeerXML_DOM;
import libs.Leer;

public class Main {
    public static void main(String[] args) {
        boolean salir = false;
        int opcion;
        do {
            System.out.println("0. Salir");
            System.out.println("1. Leer XML con DOM");
            System.out.println("2. Escribir XML con DOM");
            System.out.println("3. Leer XML con SAX");

            opcion = Leer.pedirEntero("Introduce una opción: ");

            switch (opcion) {
                case 0 -> {salir = true;}
                case 1 -> {LeerXML_DOM.leerArchivo();}
                case 2 -> {EscribirXML_DOM.escribir();}
                case 3 -> {LeerSAX.leer();}
                default -> {System.out.println("La opcion seleccionada no existe");}
            }
        } while (!salir);
    }

}