package by.kukyan.xmltask.builder;

import by.kukyan.xmltask.entity.*;
import by.kukyan.xmltask.exception.PaperException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.*;

import java.io.IOException;
import java.net.URL;

public class PaperDomBuilder extends AbstractPaperBuilder{
    private static final Logger logger = LogManager.getLogger();
    private final DocumentBuilder documentBuilder;

    public PaperDomBuilder() throws PaperException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Dom configuration encounter error", e);
            throw new PaperException("Dom configuration encounter error", e);
        }
    }

    @Override
    public void buildPapers(String path) throws PaperException {
        try{
            ClassLoader loader = getClass().getClassLoader();
            URL resource = loader.getResource(path);
            Document document = documentBuilder.parse(resource.getFile());
            Element element = document.getDocumentElement();
        } catch (IOException | SAXException e) {
            logger.error("Error while reading", e);
        }
    }

    public void createPapers(Element root, PaperXmlTag paperXmlTag) throws PaperException {
        NodeList paperList = root.getElementsByTagName(paperXmlTag.getValue());
        for (int i = 0; i < paperList.getLength(); i++) {
            Element el = (Element) paperList.item(i);
            AbstractPaper paper = buildPaper(el, paperXmlTag);
            papers.add(paper);
        }
    }

    private AbstractPaper buildPaper(Element element, PaperXmlTag paperXmlTag) throws PaperException {

        AbstractPaper paper = new Paper();

        String id = element.getAttribute(PaperXmlTag.ID.toString());
        PrintingCountry country = PrintingCountry.valueOf(getElementTextContent(element, paperXmlTag.COUNTRY.getValue().toUpperCase()));
        String title = element.getAttribute(PaperXmlTag.TITLE.toString());
        int size = Integer.parseInt(element.getAttribute(PaperXmlTag.SIZE.getValue()));
        boolean monthly = Boolean.parseBoolean(element.getAttribute(PaperXmlTag.MONTHLY.getValue()));
        boolean coloured = Boolean.parseBoolean(element.getAttribute(PaperXmlTag.COLOURED.getValue()));
        switch (paperXmlTag){
            case MAGAZINE -> {
                Magazine temp = Magazine.setNewMagazine(paper, false, Periodicity.valueOf(Periodicity.ONCEPERYEAR.getPeriodicity()), false);
                boolean glossy = Boolean.parseBoolean(element.getAttribute(PaperXmlTag.GLOSSY.getValue()));
                Periodicity periodicity = Periodicity.valueOf(getElementTextContent(element, paperXmlTag.PERIODICITY.getValue()));
                boolean sub = Boolean.parseBoolean(element.getAttribute(PaperXmlTag.SUBSCRIABLE.getValue()));
                temp.setGlossy(glossy);
                temp.setPeriodicity(periodicity);
                temp.setSubsriable(sub);
                paper = temp;
            }
            case PAPER -> {
                Paper temp = new Paper();
                Periodicity periodicity = Periodicity.valueOf(getElementTextContent(element, paperXmlTag.PERIODICITY.getValue()));
                boolean sub = Boolean.parseBoolean(element.getAttribute(PaperXmlTag.SUBSCRIABLE.getValue()));
                temp.setPeriodicity(periodicity);
                temp.setSubsriable(sub);
                paper = temp;
            }
            case BOOKLET -> {
                Booklet temp = Booklet.setNewBooklet(paper, false);
                boolean glossy = Boolean.parseBoolean(element.getAttribute(PaperXmlTag.GLOSSY.getValue()));
                temp.setGlossy(glossy);
                paper = temp;
            }
        }
        paper.setId(id);
        paper.setCountry(country);
        paper.setTitle(title);
        paper.setSize(size);
        paper.setMonthly(monthly);
        paper.setColoured(coloured);
        return paper;
    }

    private String getElementTextContent(Element element, String elementName) {
        NodeList nodeList = element.getElementsByTagName(elementName);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }
}
