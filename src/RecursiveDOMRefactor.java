import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecursiveDOMRefactor {

  public static final String CLASS_TYPE = "class";

  private final DocumentBuilderFactory documentBuilderFactory;
  private final DocumentBuilder documentBuilder;
  private final Document document;
  private final Map<String, Integer> fields;

  public RecursiveDOMRefactor(final String file) throws IOException, SAXException, ParserConfigurationException {
    this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
    this.documentBuilder = documentBuilderFactory.newDocumentBuilder();
    this.document = documentBuilder.parse(this.getClass().getResourceAsStream(file));
    this.fields = new HashMap<>();
    parse(document.getDocumentElement(), null, 0);

  }

  private void parse(final Element element, String parent, int level) {
    final NodeList children = element.getChildNodes();

    for (int i = 0; i < children.getLength(); i++) {
      final Node node = children.item(i);
      parent = (parent == null) ? node.getParentNode().getNodeName() : parent;

      if (node.getNodeType() == Node.ELEMENT_NODE) {
        if (!node.hasChildNodes()) {
          fields.put(createName(parent, node.getNodeName()), level);
        } else {
          parse((Element) node, createName(parent, node.getNodeName()), ++level);
        }
      }
    }
  }

  private String createName(String parent, String child) {
    return parent + "." + child;
  }

  public Map<String, Integer> getFields() {
    return new HashMap<String, Integer>(fields);
  }
}
