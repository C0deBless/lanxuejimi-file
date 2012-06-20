package com.trnnn.http.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class ServerConfigFactory {

	ServerConfigFactory(String path) {
		this.path = path;
	}

	private static final ServerConfigFactory factory;

	private final String path;
	private final List<ServerConfig> serverConfigs = new ArrayList<>();

	static {
		String path = ServerConfigFactory.class.getClassLoader()
				.getResource("").getFile()
				+ "server.xml";
		factory = new ServerConfigFactory(path);
	}

	public static void init() {
		factory.parseServerXML();
	}

	public static ServerConfigFactory factory() {
		return factory;
	}

	public List<ServerConfig> config() {
		return serverConfigs;
	}

	private void parseServerXML() {
		File configFile = new File(path);
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = domfac.newDocumentBuilder();
			Document doc = db.parse(configFile);
			NodeList list = doc.getElementsByTagName("service");
			for (int i = 0; i < list.getLength(); i++) {
				ServerConfig serverConfig = new ServerConfig();
				Node serviceNode = list.item(i);
				NamedNodeMap attrs = serviceNode.getAttributes();
				Node nameNode = attrs.getNamedItem("name");
				String name = nameNode.getNodeValue();
				serverConfig.name(name);
				NodeList connectorNodes = serviceNode.getChildNodes();
				for (int j = 0; j < connectorNodes.getLength(); j++) {
					Node connectorNode = connectorNodes.item(j);
					if (!connectorNode.getNodeName().equals("connector")) {
						continue;
					}
					NamedNodeMap connetorAttrs = connectorNode.getAttributes();
					int port = this.attrValueAsInt(connetorAttrs, "port");
					String protocol = this.attrValueAsString(connetorAttrs,
							"protocol");
					int connectionTimeout = this.attrValueAsInt(connetorAttrs,
							"connectionTimeout");
					serverConfig.connectionTimeout(connectionTimeout);
					serverConfig.port(port);
					serverConfig.protocol(protocol);
				}
				this.serverConfigs.add(serverConfig);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private int attrValueAsInt(NamedNodeMap attrs, String name) {
		String value = attrs.getNamedItem(name).getNodeValue();
		if (value == null || value.equals(""))
			return 0;
		return Integer.parseInt(value);
	}

	private String attrValueAsString(NamedNodeMap attrs, String name) {
		String value = attrs.getNamedItem(name).getNodeValue();
		if (value == null || value.equals("")) {
			return null;
		}
		return value;
	}
}
