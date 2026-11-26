
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.KXml2Driver;

/*
 * Restaurante: int id, int idCadena, boolean activo, int empleados,
 *             String direccion (150 caracteres), long facturacion.
 * Tamaño: 321 bytes.
 *
 * Cadena: int id, String nombre (30 caracteres).
 * Tamaño: 64 bytes.
 *
 * Borrado lógico en el binario: ID negativo.
 */
public class practica4 {

    static final String CARPETA = "Fich";
    static final String FICHERO_RESTAURANTES = CARPETA + "/practica_4_restaurantes.dat";
    static final String FICHERO_CADENAS = CARPETA + "/practica_4_cadenas.dat";
    static final String FICHERO_XML = CARPETA + "/restaurantes.xml";
    static final String FICHERO_XML_MASIVO = CARPETA + "/restaurantes_masivo.xml";
    static final String FICHERO_XSTREAM = CARPETA + "/restaurante_xstream.xml";
    static final String FICHERO_XSL = CARPETA + "/restaurantes.xsl";
    static final String FICHERO_HTML = CARPETA + "/restaurantes.html";

    static final int LONG_DIRECCION = 150;
    static final int TAM_REGISTRO = 321;
    static final int LONG_NOMBRE_CADENA = 30;
    static final int TAM_REGISTRO_CADENA = 64;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new File(CARPETA).mkdirs();

        try (RandomAccessFile raf = new RandomAccessFile(FICHERO_RESTAURANTES, "rw"); RandomAccessFile rafCadenas = new RandomAccessFile(FICHERO_CADENAS, "rw")) {

            inicializarDatosSiVacios(raf, rafCadenas);

            int opcion;
            do {
                System.out.println("\n==============================================================");
                System.out.println("       GESTIÓN DE RESTAURANTES Y FICHEROS XML");
                System.out.println("==============================================================");
                System.out.println("1. Generar XML desde el fichero binario");
                System.out.println("2. Modificar la facturación de un restaurante en el XML");
                System.out.println("3. Eliminar un restaurante del XML");
                System.out.println("4. Serializar y deserializar un restaurante con XStream");
                System.out.println("5. Generar XML masivo (6 cadenas y 100 restaurantes)");
                System.out.println("6. Consultar datos mediante XPath");
                System.out.println("7. Generar HTML mediante XSLT");
                System.out.println("8. Salir");
                System.out.println("--------------------------------------------------------------");
                opcion = pedirInt("Seleccione una opción: ");

                try {
                    switch (opcion) {
                        case 1:
                            generarXmlDesdeBinario(raf, rafCadenas);
                            break;
                        case 2:
                            modificarFacturacionXml();
                            break;
                        case 3:
                            eliminarRestauranteXml();
                            break;
                        case 4:
                            xstreamEjemplo();
                            break;
                        case 5:
                            generarXmlMasivo();
                            break;
                        case 6:
                            consultarXPath();
                            break;
                        case 7:
                            generarHtmlXslt();
                            break;
                        case 8:
                            System.out.println("Hasta luego.");
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
                } catch (Exception e) {
                    System.out.println("No se pudo completar la operación: " + e.getMessage());
                }
            } while (opcion != 8);

        } catch (IOException e) {
            System.out.println("Error con los ficheros binarios: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    // Datos de partida: solo se crean si los ficheros están vacíos.
    // -----------------------------------------------------------------
    private static void inicializarDatosSiVacios(RandomAccessFile raf, RandomAccessFile rafCadenas) throws IOException {
        if (rafCadenas.length() == 0) {
            String[] nombres = {"La Parrilla Dorada", "Pasta Bella", "Sushi Express"};
            for (int i = 0; i < nombres.length; i++) {
                rafCadenas.writeInt(i + 1);
                escribirTexto(rafCadenas, nombres[i], LONG_NOMBRE_CADENA);
            }
        }
        if (raf.length() == 0) {
            String[] direcciones = {
                    "Calle Mayor 1, Madrid", "Avenida de la Constitución 22, Sevilla",
                    "Calle Larga 5, Valencia", "Plaza de España 3, Barcelona",
                    "Calle del Sol 10, Granada", "Paseo de Gracia 45, Barcelona",
                    "Calle Alcalá 100, Madrid", "Gran Vía 8, Bilbao",
                    "Calle Real 17, Zaragoza", "Rambla de Catalunya 60, Barcelona",
                    "Calle Serrano 34, Madrid", "Avenida del Puerto 12, Valencia",
                    "Calle Toledo 76, Madrid", "Calle San Fernando 9, Sevilla",
                    "Paseo Marítimo 2, Málaga"
            };
            for (int i = 0; i < direcciones.length; i++) {
                escribirRestaurante(raf, new Restaurante(i + 1, (i / 5) + 1,
                        i % 4 != 2, 6 + i, direcciones[i], 54000L + i * 12500L));
            }
            System.out.println("Se han creado datos de ejemplo en la carpeta " + CARPETA + ".");
        }
    }

    // -----------------------------------------------------------------
    // 1. Binario -> XML DOM
    // -----------------------------------------------------------------
    private static void generarXmlDesdeBinario(RandomAccessFile raf, RandomAccessFile rafCadenas) throws Exception {
        Document doc = nuevoDocumento();
        Element raiz = doc.createElement("restaurantes");
        doc.appendChild(raiz);

        int total = (int) (raf.length() / TAM_REGISTRO);
        int incluidos = 0;
        for (int i = 0; i < total; i++) {
            long base = (long) i * TAM_REGISTRO;
            raf.seek(base);
            int id = raf.readInt();
            if (id <= 0) {
                continue; // ignora registros borrados
            }
            Restaurante r = leerRestaurante(raf, base);
            Element e = doc.createElement("restaurante");
            agregarTexto(doc, e, "id", r.id);
            agregarTexto(doc, e, "idCadena", r.idCadena);
            agregarTexto(doc, e, "nombreCadena", nombreCadena(rafCadenas, r.idCadena));
            agregarTexto(doc, e, "activo", r.activo);
            agregarTexto(doc, e, "empleados", r.empleados);
            agregarTexto(doc, e, "direccion", r.direccion);
            agregarTexto(doc, e, "facturacion", r.facturacion);
            raiz.appendChild(e);
            incluidos++;
        }
        guardarXml(doc, FICHERO_XML);
        System.out.println("XML generado: " + FICHERO_XML + " (" + incluidos + " restaurantes).");
    }

    // -----------------------------------------------------------------
    // 2. Modificar facturación en XML con DOM + XPath
    // -----------------------------------------------------------------
    private static void modificarFacturacionXml() throws Exception {
        Document doc = cargarXml(FICHERO_XML);
        int id = pedirInt("Código del restaurante: ");
        long nuevaFacturacion = pedirLong("Nueva facturación: ");

        XPath xpath = XPathFactory.newInstance().newXPath();
        Node nodo = (Node) xpath.evaluate("/restaurantes/restaurante[id='" + id + "']",
                doc, XPathConstants.NODE);
        if (nodo == null) {
            System.out.println("No existe el restaurante " + id + " en el XML.");
            return;
        }
        ((Element) nodo).getElementsByTagName("facturacion").item(0)
                .setTextContent(Long.toString(nuevaFacturacion));
        guardarXml(doc, FICHERO_XML);
        System.out.println("Facturación modificada correctamente.");
    }

    // -----------------------------------------------------------------
    // 3. Eliminar un nodo restaurante del XML
    // -----------------------------------------------------------------
    private static void eliminarRestauranteXml() throws Exception {
        Document doc = cargarXml(FICHERO_XML);
        int id = pedirInt("Código del restaurante a eliminar del XML: ");

        XPath xpath = XPathFactory.newInstance().newXPath();
        Node nodo = (Node) xpath.evaluate("/restaurantes/restaurante[id='" + id + "']",
                doc, XPathConstants.NODE);
        if (nodo == null) {
            System.out.println("No existe el restaurante " + id + " en el XML.");
            return;
        }
        nodo.getParentNode().removeChild(nodo);
        guardarXml(doc, FICHERO_XML);
        System.out.println("Restaurante eliminado del XML. El fichero binario no se modifica.");
    }

    // -----------------------------------------------------------------
    // 4. Serializar y deserializar un restaurante con XStream
    // -----------------------------------------------------------------
    private static void xstreamEjemplo() throws Exception {
        //Objeto de simulación
        Restaurante r = new Restaurante(999, 2, true, 18,
                "Calle de Ejemplo 25, Madrid", 245000L);

        XStream xstream = new XStream(new KXml2Driver());

        // Permitir la serialización de la clase Restaurante
        xstream.allowTypes(new Class[]{Restaurante.class});

        // Establecer el nombre de la etiqueta XML
        xstream.alias("restaurante", Restaurante.class);

        // Serializar el objeto a XML
        String xml = xstream.toXML(r);

        // Guardar el XML en un archivo
        escribirArchivoTexto(FICHERO_XSTREAM, xml);

        // Deserializar el XML y recuperar el objeto
        Restaurante copia = (Restaurante) xstream.fromXML(xml);

        System.out.println("XML serializado en: " + FICHERO_XSTREAM);
        System.out.println("Objeto deserializado: " + copia);
    }

    // -----------------------------------------------------------------
    // 5. XML de ejemplo masivo: 6 cadenas y 100 restaurantes
    // -----------------------------------------------------------------
    private static void generarXmlMasivo() throws Exception {
        Document doc = nuevoDocumento();
        Element raiz = doc.createElement("restaurantes");
        doc.appendChild(raiz);

        Element cadenas = doc.createElement("cadenas");
        raiz.appendChild(cadenas);
        String[] nombres = {"La Parrilla Dorada", "Pasta Bella", "Sushi Express",
                "Verde y Punto", "Burger Central", "Mar de Sabores"};
        for (int i = 0; i < nombres.length; i++) {
            Element c = doc.createElement("cadena");
            agregarTexto(doc, c, "id", i + 1);
            agregarTexto(doc, c, "nombre", nombres[i]);
            cadenas.appendChild(c);
        }

        Element lista = doc.createElement("locales");
        raiz.appendChild(lista);
        String[] ciudades = {"Madrid", "Barcelona", "Valencia", "Sevilla", "Málaga",
                "Bilbao", "Zaragoza", "Granada", "Alicante", "Valladolid"};
        for (int i = 1; i <= 100; i++) {
            int idCadena = ((i - 1) % 6) + 1;
            Element e = doc.createElement("restaurante");
            agregarTexto(doc, e, "id", i);
            agregarTexto(doc, e, "idCadena", idCadena);
            agregarTexto(doc, e, "nombreCadena", nombres[idCadena - 1]);
            agregarTexto(doc, e, "activo", i % 9 != 0);
            agregarTexto(doc, e, "empleados", 5 + (i * 7) % 36);
            agregarTexto(doc, e, "direccion", "Calle " + (i + 10) + ", " + ciudades[(i - 1) % ciudades.length]);
            agregarTexto(doc, e, "facturacion", 45000L + (i * 13750L));
            agregarTexto(doc, e, "tieneTerraza", i % 3 == 0);
            agregarTexto(doc, e, "valoracion", String.format(java.util.Locale.US, "%.1f", 3.0 + (i % 20) / 10.0));
            lista.appendChild(e);
        }

        guardarXml(doc, FICHERO_XML_MASIVO);
        System.out.println("XML masivo generado: " + FICHERO_XML_MASIVO);
        System.out.println("Incluye 6 cadenas y 100 restaurantes.");
    }

    // -----------------------------------------------------------------
    // 6. Consultas XPath predefinidas y consulta libre
    // -----------------------------------------------------------------
    private static void consultarXPath() throws Exception {
        Document doc = cargarXml(FICHERO_XML_MASIVO);
        XPath xpath = XPathFactory.newInstance().newXPath();

        System.out.println("Consultas XPath:");
        System.out.println("1. Restaurantes de la cadena 2 con facturación > 100000");
        System.out.println("2. Direcciones de restaurantes con terraza");
        System.out.println("3. Restaurantes con más de 20 empleados");
        System.out.println("4. Introducir una expresión XPath");
        int opcion = pedirInt("Opción: ");

        String expresion;
        switch (opcion) {
            case 1:
                expresion = "/restaurantes/locales/restaurante[idCadena='2' and number(facturacion)>100000]";
                break;
            case 2:
                expresion = "/restaurantes/locales/restaurante[tieneTerraza='true']/direccion";
                break;
            case 3:
                expresion = "/restaurantes/locales/restaurante[number(empleados)>20]";
                break;
            case 4:
                expresion = pedirTexto("Expresión XPath (sobre restaurantes_masivo.xml): ");
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        NodeList resultados = (NodeList) xpath.evaluate(expresion, doc, XPathConstants.NODESET);
        System.out.println("Resultados (" + resultados.getLength() + "):");
        for (int i = 0; i < resultados.getLength(); i++) {
            Node n = resultados.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                if ("restaurante".equals(e.getTagName())) {
                    System.out.println("Restaurante " + texto(e, "id") + " | cadena "
                            + texto(e, "nombreCadena") + " | " + texto(e, "direccion")
                            + " | facturación " + texto(e, "facturacion"));
                } else {
                    System.out.println(n.getTextContent());
                }
            } else {
                System.out.println(n.getNodeValue());
            }
        }
    }

    // -----------------------------------------------------------------
    // 7. XSLT -> HTML
    // -----------------------------------------------------------------
    private static void generarHtmlXslt() throws Exception {
        File xml = new File(FICHERO_XML_MASIVO);
        if (!xml.exists()) {
            System.out.println("Primero ejecuta la opción 5 para generar el XML masivo.");
            return;
        }
        crearXsl();
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new javax.xml.transform.stream.StreamSource(FICHERO_XSL));
        transformer.setOutputProperty(OutputKeys.METHOD, "html");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new javax.xml.transform.stream.StreamSource(FICHERO_XML_MASIVO),
                new StreamResult(new File(FICHERO_HTML)));
        System.out.println("HTML generado: " + FICHERO_HTML);
    }

    private static void crearXsl() throws IOException {
        String xsl = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n"
                + "<xsl:output method=\"html\" encoding=\"UTF-8\" indent=\"yes\"/>\n"
                + "<xsl:template match=\"/\">\n"
                + "<html><head><meta charset=\"UTF-8\"/><title>Restaurantes</title>"
                + "<style>body{font-family:Arial;margin:30px;background:#f5f5f5}table{border-collapse:collapse;width:100%}"
                + "th,td{border:1px solid #bbb;padding:8px;text-align:left}th{background:#dce9df}</style></head><body>"
                + "<h1>Listado de restaurantes</h1><table><tr><th>ID</th><th>Cadena</th><th>Activo</th>"
                + "<th>Empleados</th><th>Dirección</th><th>Facturación</th><th>Terraza</th></tr>"
                + "<xsl:for-each select=\"restaurantes/locales/restaurante\">"
                + "<tr><td><xsl:value-of select=\"id\"/></td><td><xsl:value-of select=\"nombreCadena\"/></td>"
                + "<td><xsl:value-of select=\"activo\"/></td><td><xsl:value-of select=\"empleados\"/></td>"
                + "<td><xsl:value-of select=\"direccion\"/></td><td><xsl:value-of select=\"facturacion\"/></td>"
                + "<td><xsl:value-of select=\"tieneTerraza\"/></td></tr></xsl:for-each>"
                + "</table></body></html></xsl:template></xsl:stylesheet>";
        escribirArchivoTexto(FICHERO_XSL, xsl);
    }

    // -----------------------------------------------------------------
    // Métodos de ficheros y XML
    // -----------------------------------------------------------------
    private static Restaurante leerRestaurante(RandomAccessFile raf, long base) throws IOException {
        raf.seek(base);
        int id = raf.readInt();
        int idCadena = raf.readInt();
        boolean activo = raf.readBoolean();
        int empleados = raf.readInt();
        String direccion = leerTexto(raf, LONG_DIRECCION);
        long facturacion = raf.readLong();
        return new Restaurante(id, idCadena, activo, empleados, direccion, facturacion);
    }

    private static void escribirRestaurante(RandomAccessFile raf, Restaurante r) throws IOException {
        raf.seek(raf.length());
        raf.writeInt(r.id);
        raf.writeInt(r.idCadena);
        raf.writeBoolean(r.activo);
        raf.writeInt(r.empleados);
        escribirTexto(raf, r.direccion, LONG_DIRECCION);
        raf.writeLong(r.facturacion);
    }

    private static String nombreCadena(RandomAccessFile rafCadenas, int id) throws IOException {
        int total = (int) (rafCadenas.length() / TAM_REGISTRO_CADENA);
        for (int i = 0; i < total; i++) {
            rafCadenas.seek((long) i * TAM_REGISTRO_CADENA);
            int idLeido = rafCadenas.readInt();
            String nombre = leerTexto(rafCadenas, LONG_NOMBRE_CADENA);
            if (idLeido == id) {
                return nombre;
            }
        }
        return "(cadena desconocida)";
    }

    private static Document nuevoDocumento() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    private static Document cargarXml(String ruta) throws Exception {
        File f = new File(ruta);
        if (!f.exists()) {
            throw new IOException("No existe " + ruta + ". Genera primero el XML correspondiente.");
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        return factory.newDocumentBuilder().parse(f);
    }

    private static void guardarXml(Document doc, String ruta) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(ruta)));
    }

    private static void agregarTexto(Document doc, Element padre, String etiqueta, Object valor) {
        Element e = doc.createElement(etiqueta);
        e.setTextContent(String.valueOf(valor));
        padre.appendChild(e);
    }

    private static String texto(Element e, String etiqueta) {
        NodeList lista = e.getElementsByTagName(etiqueta);
        return lista.getLength() == 0 ? "" : lista.item(0).getTextContent();
    }

    private static void escribirTexto(RandomAccessFile raf, String texto, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(texto == null ? "" : texto);
        if (sb.length() > longitud) {
            sb.setLength(longitud);
        }
        while (sb.length() < longitud) {
            sb.append(' ');
        }
        raf.writeChars(sb.toString());
    }

    private static String leerTexto(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }

    private static void escribirArchivoTexto(String ruta, String contenido) throws IOException {
        java.nio.file.Files.write(java.nio.file.Paths.get(ruta),
                contenido.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    // -----------------------------------------------------------------
    // Teclado
    // -----------------------------------------------------------------
    private static String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private static int pedirInt(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido.");
            }
        }
    }

    private static long pedirLong(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido.");
            }
        }
    }
}
