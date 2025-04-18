import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XML_JSON {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element staff = document.createElement("staff");
        document.appendChild(staff);

        Element employee = document.createElement("employee");
        staff.appendChild(employee);
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode("1"));
        employee.appendChild(id);
        Element firstName = document.createElement("firstName");
        firstName.appendChild(document.createTextNode("John"));
        employee.appendChild(firstName);
        Element lastName = document.createElement("lastName");
        lastName.appendChild(document.createTextNode("Smith"));
        employee.appendChild(lastName);
        Element country = document.createElement("country");
        country.appendChild(document.createTextNode("USA"));
        employee.appendChild(country);
        Element age = document.createElement("age");
        age.appendChild(document.createTextNode("25"));
        employee.appendChild(age);

        Element employee1 = document.createElement("employee");
        staff.appendChild(employee1);
        Element id1 = document.createElement("id");
        id1.appendChild(document.createTextNode("2"));
        employee1.appendChild(id1);
        Element firstName1 = document.createElement("firstName");
        firstName1.appendChild(document.createTextNode("Ivan"));
        employee1.appendChild(firstName1);
        Element lastName1 = document.createElement("lastName");
        lastName1.appendChild(document.createTextNode("Petrov"));
        employee1.appendChild(lastName1);
        Element country1 = document.createElement("country");
        country1.appendChild(document.createTextNode("RU"));
        employee1.appendChild(country1);
        Element age1 = document.createElement("age");
        age1.appendChild(document.createTextNode("23"));
        employee1.appendChild(age1);

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("data.xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(domSource, streamResult);

        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);
        list.forEach(System.out::println);

        String json2 = ConverterToJSON.listToJson(list, "2");
        System.out.println(json2);

    }

    private static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                Employee employee = new Employee((Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent())),
                        (element.getElementsByTagName("firstName").item(0).getTextContent()),
                        (element.getElementsByTagName("lastName").item(0).getTextContent()),
                        (element.getElementsByTagName("country").item(0).getTextContent()),
                        (Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent())));

                list.add(employee);
            }
        }
        return list;
    }
}
