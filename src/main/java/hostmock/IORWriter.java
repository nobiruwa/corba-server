package hostmock;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IORWriter {
    String filePath;
    String xPath;
    public IORWriter(String filePath, String xPath) {
        this.filePath = filePath;
        this.xPath = xPath;
    }

    private Document load() throws IOException, ParserConfigurationException, SAXException {
        try (FileInputStream fis = new FileInputStream(this.filePath)) {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(fis);
            return xmlDocument;
        } catch (IOException e) {
            throw e;
        }
    }
    private Document update(Document document, String ior) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = (XPathExpression)xPath.compile(this.xPath);
        NodeList result = (NodeList)expr.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < result.getLength(); i++) {
            Node node = result.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element)node;
                NodeList children = element.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    element.removeChild(children.item(j));
                }
                element.appendChild(document.createTextNode(ior));
            }
        }
        return document;
    }

    private void save(Document document) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult streamResult =  new StreamResult(new File(this.filePath));
        transformer.transform(source, streamResult);
    }
    public void write(String ior) {
        try {
            this.save(this.update(this.load(), ior));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
