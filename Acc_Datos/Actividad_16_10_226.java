import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Actividad_16_10_26 {

    // 4 + 80 + 80 + 4 + 1 + 2 + 2 = 173 bytes
    static final int TAM_REGISTRO = 173;
    static final int LONG_CADENA = 40;

    static final String FICHERO_BIN = "empleados.dat";
    static final String FICHERO_XML = "empleados.xml";

    public static void main(String[] args) {
        File bin = new File(FICHERO_BIN);

        // Si no existe el binario, lo creamos con datos de prueba
        if (!bin.exists()) {
            crearBinarioDePrueba();
        }

        try (RandomAccessFile raf = new RandomAccessFile(bin, "r")) {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("empleados");
            doc.appendChild(raiz);

            long numRegistros = raf.length() / TAM_REGISTRO;

            for (int i = 0; i < numRegistros; i++) {
                raf.seek((long) i * TAM_REGISTRO);

                int codigo = raf.readInt();
                String nombre = leerCadena(raf);
                String correo = leerCadena(raf);
                float salario = raf.readFloat();
                boolean carnet = raf.readBoolean();
                short departamento = raf.readShort();
                char tipo = raf.readChar();

                // Código y tipo como atributos
                Element empleado = doc.createElement("empleado");
                empleado.setAttribute("codigo", String.valueOf(codigo));
                empleado.setAttribute("tipo", String.valueOf(tipo));

                empleado.appendChild(crearElemento(doc, "nombre", nombre));
                empleado.appendChild(crearElemento(doc, "correo", correo));
                empleado.appendChild(crearElemento(doc, "salario", String.valueOf(salario)));
                empleado.appendChild(crearElemento(doc, "carnet", String.valueOf(carnet)));
                empleado.appendChild(crearElemento(doc, "departamento", String.valueOf(departamento)));

                raiz.appendChild(empleado);
            }

            // Escribir el XML a fichero
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            t.transform(new DOMSource(doc), new StreamResult(new File(FICHERO_XML)));

            System.out.println("XML generado: " + FICHERO_XML + " (" + numRegistros + " empleados)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lee 40 chars (80 bytes) y quita el relleno
    static String leerCadena(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LONG_CADENA; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim(); // trim quita espacios y '\0'
    }

    static Element crearElemento(Document doc, String nombre, String valor) {
        Element e = doc.createElement(nombre);
        e.appendChild(doc.createTextNode(valor));
        return e;
    }

    // ---------- Utilidades para probar ----------

    static void escribirCadena(RandomAccessFile raf, String s) throws IOException {
        StringBuilder sb = new StringBuilder(s);
        sb.setLength(LONG_CADENA); // rellena con '\0' o recorta a 40
        raf.writeChars(sb.toString());
    }

    static void escribirEmpleado(RandomAccessFile raf, int cod, String nom, String mail,
                                 float sal, boolean carnet, short dep, char tipo) throws IOException {
        raf.writeInt(cod);
        escribirCadena(raf, nom);
        escribirCadena(raf, mail);
        raf.writeFloat(sal);
        raf.writeBoolean(carnet);
        raf.writeShort(dep);
        raf.writeChar(tipo);
    }

    static void crearBinarioDePrueba() {
        try (RandomAccessFile raf = new RandomAccessFile(FICHERO_BIN, "rw")) {
            escribirEmpleado(raf, 1, "Ana Lopez", "ana@empresa.com", 1800.50f, true, (short) 10, 'T');
            escribirEmpleado(raf, 2, "Luis Perez", "luis@empresa.com", 2100.00f, false, (short) 20, 'C');
            escribirEmpleado(raf, 3, "Marta Gil", "marta@empresa.com", 1650.75f, true, (short) 30, 'S');
            System.out.println("Binario de prueba creado: " + FICHERO_BIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}