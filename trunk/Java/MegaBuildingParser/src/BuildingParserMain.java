import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BuildingParserMain {
	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		String xmlPath = "C:\\Users\\trnnn-win7\\Desktop\\buildings_0.xml";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File(xmlPath));
		NodeList rootList = doc.getChildNodes();
		for (int i = 0; i < rootList.getLength(); i++) {
			Node node = rootList.item(i);
			if (node.getNodeName().equals("country")) {
				NodeList fieldNodeList = node.getChildNodes();
				for (int j = 0; j < fieldNodeList.getLength(); j++) {
					Node fieldNode = fieldNodeList.item(j);
					if (fieldNode.getNodeName().equals("field")) {
						NodeList shopNodeList = fieldNode.getChildNodes();
						for (int k = 0; k < shopNodeList.getLength(); k++) {
							Node shopNode = shopNodeList.item(k);
							if (shopNode.getNodeName().equals("dyno_shop")) {
								int id = Integer.parseInt(shopNode
										.getAttributes().getNamedItem("id")
										.getNodeValue());
								System.out.println(id);
							}
						}
					}
				}
			}
		}
	}
}
