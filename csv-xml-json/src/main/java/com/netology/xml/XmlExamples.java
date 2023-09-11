package com.netology.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class XmlExamples {

    public static final String INPUT_PATH = "csv-xml-json/src/main/resources/xml/input.xml";
    public static final String OUTPUT_PATH = "csv-xml-json/src/main/resources/xml/output.xml";

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(INPUT_PATH);

            NodeList studentNodeList = document.getElementsByTagName("student");

            double realAverage = 0;
            int mark;

            NodeList subjectsList = ((Element) studentNodeList.item(0)).getElementsByTagName("subject");

            for (int j = 0; j < subjectsList.getLength(); j++) {
                mark = Integer.parseInt(((Element) subjectsList.item(j)).getAttribute("mark"));
                realAverage += mark;
            }
            realAverage = realAverage / subjectsList.getLength();

            Node averageNode = ((Element) studentNodeList.item(0)).getElementsByTagName("average").item(0);

            double average = Double.parseDouble((averageNode).getTextContent());

            if (average != realAverage) {
                averageNode.setTextContent(String.valueOf(BigDecimal.valueOf(realAverage)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue()));
            }

            Document outputDoc = builder.newDocument();
            createNewDocument(OUTPUT_PATH, studentNodeList, outputDoc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void createNewDocument(String output, NodeList students, Document outputDoc) {

        for (int i = 0; i < students.getLength(); i++) {
            Node student = outputDoc.importNode(students.item(i), true);
            outputDoc.appendChild(student);
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(outputDoc);

            FileWriter writer = new FileWriter(output);
            StreamResult result = new StreamResult(writer);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(source, result);
        } catch (TransformerException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}