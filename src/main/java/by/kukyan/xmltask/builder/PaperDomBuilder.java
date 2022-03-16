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
import java.time.LocalDate;

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
    public void buildPapers(String path){
        try{
            ClassLoader loader = getClass().getClassLoader();
            URL resource = loader.getResource(path);

            Document document = documentBuilder.parse(resource.getFile());
            Element element = document.getDocumentElement();
            createPapers(element, PaperXmlTag.PAPER);
            createPapers(element, PaperXmlTag.MAGAZINE);
            createPapers(element, PaperXmlTag.BOOKLET);
        } catch (IOException | SAXException e) {
            logger.error("Error while reading", e);
        }
    }

    public void createPapers(Element root, PaperXmlTag paperXmlTag){
        NodeList paperList = root.getElementsByTagName(paperXmlTag.getTagName());
        for (int i = 0; i < paperList.getLength(); i++) {
            Element el = (Element) paperList.item(i);
            AbstractPaper paper = buildPaper(el, paperXmlTag);
            papers.add(paper);
        }
    }

    private AbstractPaper buildPaper(Element element, PaperXmlTag paperXmlTag){

        AbstractPaper paper = new Paper();

        String id = element.getAttribute(PaperXmlTag.ID.getTagName());
        PrintingCountry country = PrintingCountry.getCountryFromString(element.getAttribute(paperXmlTag.PRINTING_COUNTRY.getTagName()));
        String title = getContentFromTag(element, PaperXmlTag.TITLE.getTagName());
        int size = Integer.parseInt(getContentFromTag(element, PaperXmlTag.SIZE.getTagName()));
        boolean monthly = Boolean.parseBoolean(getContentFromTag(element, PaperXmlTag.MONTHLY.getTagName()));
        boolean coloured = Boolean.parseBoolean(getContentFromTag(element, PaperXmlTag.COLOURED.getTagName()));
        LocalDate newDate = LocalDate.parse(getContentFromTag(element, PaperXmlTag.PRINTING_DATE.getTagName()));
        switch (paperXmlTag){
            case MAGAZINE -> {
                Magazine temp = Magazine.setNewMagazine(paper, false, null, false);
                boolean glossy = Boolean.parseBoolean(getContentFromTag(element, PaperXmlTag.GLOSSY.getTagName()));
                Periodicity periodicity = Periodicity.getPeriodicityFromString(getContentFromTag(element, paperXmlTag.PERIODICITY.getTagName()));
                boolean sub = Boolean.parseBoolean(getContentFromTag(element, PaperXmlTag.SUBSCRIPTION_INDEX.getTagName()));
                temp.setGlossy(glossy);
                temp.setPeriodicity(periodicity);
                temp.setSubsriable(sub);
                paper = temp;
            }
            case PAPER -> {
                Paper temp = new Paper();
                Periodicity periodicity = Periodicity.getPeriodicityFromString(getContentFromTag(element, paperXmlTag.PERIODICITY.getTagName()));
                boolean sub = Boolean.parseBoolean(getContentFromTag(element, PaperXmlTag.SUBSCRIPTION_INDEX.getTagName()));
                temp.setPeriodicity(periodicity);
                temp.setSubsriable(sub);
                paper = temp;
            }
            case BOOKLET -> {
                Booklet temp = Booklet.setNewBooklet(paper, false);
                boolean glossy = Boolean.parseBoolean(getContentFromTag(element, PaperXmlTag.GLOSSY.getTagName()));
                temp.setGlossy(glossy);
                paper = temp;
            }
        }
        paper.setId(id);
        paper.setCountry(country);
        paper.setTitle(title);
        paper.setSize(size);
        paper.setMonthly(monthly);
        paper.setPrintingDate(newDate);
        paper.setColoured(coloured);
        return paper;
    }

    private String getContentFromTag(Element element, String elementName){
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
