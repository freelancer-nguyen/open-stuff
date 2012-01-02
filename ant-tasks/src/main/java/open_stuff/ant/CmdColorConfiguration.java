/*******************************************************************************
 * Copyright (c) 2012 freelancer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors:
 *     freelancer - initial API and implementation
 ******************************************************************************/
package open_stuff.ant;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class CmdColorConfiguration {

	private List colorConditions = null;

	CmdColorConfiguration() {
		init();
	}

	private void init() {
		if (colorConditions == null) {
			try {
				InputStream configurationAsStream = null;
				String configurationFile = System.getProperty("CmdColorLogger.confFile");
				
				if (configurationFile != null) {
					configurationAsStream = new FileInputStream(configurationFile);
				} else {
					configurationAsStream = CmdColorConfiguration.class.getClassLoader().getResourceAsStream("open_stuff/ant/DefaultConfiguration.xml");
				}
				
				// XSD
				//File schema = new File (CmdColorConfiguration.class.getClassLoader().getResource("open_stuff/ant/CmdColorConfiguration.xsd").toURI());
				
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				//builderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
				//builderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", schema);
				//builderFactory.setValidating(true);
				
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document doc = builder.parse(configurationAsStream);
				
				Element root = doc.getDocumentElement();
				
				NodeList cmdColorConditions = root.getChildNodes();
				
				colorConditions = new Vector(cmdColorConditions.getLength());
				
				for (int i=0; i< cmdColorConditions.getLength(); i++) {
					Node node = cmdColorConditions.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element colorCondition = (Element)node;
						CmdColorCondition cmdColorCondition = new CmdColorCondition();
						colorConditions.add(cmdColorCondition);
						
						NodeList children = colorCondition.getChildNodes();
						for (int j=0; j< children.getLength(); j++) {
							Node child = children.item(j);
							if (child.getNodeType() == Node.ELEMENT_NODE) {
								Element childElem = (Element)child;
								if ("colorAttribute".equals(childElem.getTagName())) {
									short colorAttribute = Short.parseShort(childElem.getAttribute("value"));
									cmdColorCondition.colorAttribute = colorAttribute;									
								} else {
									cmdColorCondition.condition = createMessageCondition(childElem);
								}
									
							}
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	private static MessageCondition createMessageCondition(Element elem) {
		String elemName = elem.getTagName();
		
		if ("priority".equals(elemName)) {
			int type = MessagePriorityCondition.EQUAL;
			String value = null;
			if (!(value = elem.getAttribute("greaterThan")).isEmpty())
				type = MessagePriorityCondition.GREATER_THAN;
			else if (!(value = elem.getAttribute("lesserThan")).isEmpty())
				type = MessagePriorityCondition.LESSER_THAN;
			else if (!(value = elem.getAttribute("equal")).isEmpty())
				type = MessagePriorityCondition.EQUAL;
			return new MessagePriorityCondition(type, Integer.parseInt(value));
		}
		
		if ("content".equals(elemName)) {
			return new MessageContentCondition(elem.getAttribute("matches"));
		}
		
		CompositeCondition compositeCondition = null;
		if ("or".equals(elemName))
			compositeCondition = new OrCondition();
		if ("and".equals(elemName))
			compositeCondition = new AndCondition();
		
		NodeList children = elem.getChildNodes();
		for (int i=0; i< children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE)
				compositeCondition.addMessageCondition(createMessageCondition((Element)child));
		}
		
		return compositeCondition;
	}

	CmdColorConditionIterator iterator() {
		if (colorConditions != null)
			return new CmdColorConditionIterator(colorConditions.iterator());
		return null;
	}

	class CmdColorConditionIterator {

		private Iterator decorated;

		private CmdColorConditionIterator(Iterator iterator) {
			decorated = iterator;
		}

		public boolean hasNext() {
			return decorated.hasNext();
		}

		public CmdColorCondition next() {
			return (CmdColorCondition) decorated.next();
		}
	}

}
