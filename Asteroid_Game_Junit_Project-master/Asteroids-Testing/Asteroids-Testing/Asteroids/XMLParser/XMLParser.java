
import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLParser {

    // method stripExtension from https://stackoverflow.com/questions/924394/how-to-get-the-filename-without-the-extension-in-java
    static String stripExtension (String str) {
        // Handle null case specially.
        if (str == null) return null;
        // Get position of last '.'.
        int pos = str.lastIndexOf(".");
        // If there wasn't any '.' just return the string as is.
        if (pos == -1) return str;
        // Otherwise return the string, up to the dot.
        return str.substring(0, pos);
    }


    public static void main(String[] args) {

        File folder = new File("IndividualCoverageReport\\XML");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile())
            try {
                File inputFile = new File(folder + "\\" + listOfFiles[i].getName());
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("sourcefile");
                Node n=null;
                Element eElement=null;

                //Output Stream
                File outputFolder = new File("IndividualCoverageReport\\txt");
                try {
                    Writer writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(outputFolder + "\\"+stripExtension(listOfFiles[i].getName())+".txt" ), "utf-8"));

                    //loop through node list for a given XML File
                    for (int x = 0; x < nodeList.getLength(); x++) {
                        n = nodeList.item(x);

                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            eElement = (Element) n.getChildNodes();
                            String methodName = eElement.getAttribute("name");
                            int lineCount = eElement.getElementsByTagName("line").getLength();
                            for (int y = 0; y < lineCount; y++) {
                                int currentCI = Integer.parseInt(eElement.getElementsByTagName("line").item(y).getAttributes().getNamedItem("ci").getNodeValue());
                                if (currentCI > 0) {
                                    int lineNumber = Integer.parseInt(eElement.getElementsByTagName("line").item(y).getAttributes().getNamedItem("nr").getNodeValue());
                                    writer.write(methodName+ "\t"+ lineNumber );
                                    writer.write(System.getProperty( "line.separator" ));
                                }
                            }
                        }
                    }
                    writer.close();
                }catch (IOException ex){
                    System.out.println("Error at output stream to a file");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
