package com.playtech.cm.utils.testlink;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.geom.Path2D;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Denis Veselovskiy
 * Date: 6/19/12
 * Time: 12:09 PM
 */
public class TestLinkMarker {

    private static NodeList tests;
    private static NodeList clazzes;
    private static NodeList methods;
    private static Document doc;

    public static void main(String[] args) {
       fixResultsXML("C:\\jenkins\\workspace\\CMAutomationStable\\target\\surefire-reports\\testng-results.xml");
    }
    public static void fixResultsXML(String pathToXML) {
        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(pathToXML);
            Map<String, String> mp = new HashMap<String, String>();
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            tests = (NodeList) (xpath.compile("//test").evaluate(doc, XPathConstants.NODESET));
            for (int t = 1 ; t <= tests.getLength(); t++) {
                clazzes = (NodeList) (xpath.compile("//test[" + t + "]/class").evaluate(doc, XPathConstants.NODESET));
                for (int c = 1;  c <= clazzes.getLength(); c++) {
                    methods = (NodeList) (xpath.compile("//test[" + t + "]/class[" + c + "]/test-method").evaluate(doc, XPathConstants.NODESET));
                    for (int m = 1;  m <= methods.getLength()  ; m++) {
                        String key = clazzes.item(c-1).getAttributes().getNamedItem("name").getNodeValue() + "."
                                + methods.item(m-1).getAttributes().getNamedItem("name").getNodeValue();
                        String value =  methods.item(m-1).getAttributes().getNamedItem("status").getNodeValue();
                        if (mp.containsKey(key)) {
                            if (value.equals("FAIL")) {
                                mp.put(key, value);
                            }
                        }
                        else {
                            mp.put(key, value);
                        }
                    }
                }
            }

            tests = (NodeList) (xpath.compile("//test").evaluate(doc, XPathConstants.NODESET));
            for (int t = 1 ; t <= tests.getLength(); t++) {
                clazzes = (NodeList) (xpath.compile("//test[" + t + "]/class").evaluate(doc, XPathConstants.NODESET));
                for (int c = 1;  c <= clazzes.getLength(); c++) {
                    methods = (NodeList) (xpath.compile("//test[" + t + "]/class[" + c + "]/test-method").evaluate(doc, XPathConstants.NODESET));
                    for (int m = 1;  m <= methods.getLength()  ; m++) {
                        String key = clazzes.item(c-1).getAttributes().getNamedItem("name").getNodeValue() + "."
                                + methods.item(m-1).getAttributes().getNamedItem("name").getNodeValue();
                        Node value =  methods.item(m-1).getAttributes().getNamedItem("status");

                        if (mp.get(key).equals("FAIL")) {
                            value.setTextContent("FAIL");
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        saveXML(pathToXML, "UTF-8");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void saveXML(String pathToFile, String charSet) {
        try {
            Writer target = new OutputStreamWriter(new FileOutputStream(pathToFile), charSet);
            Source source = new DOMSource(doc);
            StreamResult dest = new StreamResult(target);
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.ENCODING, charSet);
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(source, dest);
            target.flush();
            target.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

