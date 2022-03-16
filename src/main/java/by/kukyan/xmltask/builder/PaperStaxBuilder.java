package by.kukyan.xmltask.builder;

import by.kukyan.xmltask.entity.*;
import by.kukyan.xmltask.exception.PaperException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

public class PaperStaxBuilder extends AbstractPaperBuilder{
    private static final Logger logger = LogManager.getLogger();
    private static final char UNDERSCORE = '_';
    private static final char HYPHEN = '-';
    private XMLInputFactory inputFactory;

    public PaperStaxBuilder(){
        inputFactory = XMLInputFactory.newInstance();
    }
    @Override
    public void buildPapers(String path) throws PaperException {
        ClassLoader loader = getClass().getClassLoader();
        URL resource = loader.getResource(path);

        XMLStreamReader reader;
        String name;

        try(FileInputStream inputStream = new FileInputStream(resource.getFile())){
            reader = inputFactory.createXMLStreamReader(inputStream);
            while(reader.hasNext()){
                int type = reader.next();
                if(type == XMLStreamConstants.START_ELEMENT){
                    name = reader.getLocalName();
                    if(name.equals(PaperXmlTag.PAPER.getValue()) || name.equals(PaperXmlTag.MAGAZINE.getValue()) || name.equals(PaperXmlTag.BOOKLET.getValue())){
                        AbstractPaper paper = buildPaper(reader);
                        papers.add(paper);
                    }
                }
            }
        } catch (IOException | XMLStreamException e) {
            logger.error("stax error", e);
            throw new PaperException("stax error", e);
        }
    }

    private AbstractPaper buildPaper(XMLStreamReader reader) throws XMLStreamException {

        String temp = reader.getLocalName();
        AbstractPaper currentPaper = temp.equals(PaperXmlTag.PAPER.getValue()) ? new Paper()
                : temp.equals(PaperXmlTag.BOOKLET.getValue()) ? new Booklet() : new Magazine();

        currentPaper.setId(reader.getAttributeValue(null, PaperXmlTag.ID.getValue()));


        PrintingCountry country = PrintingCountry.getCountryFromString(reader.getAttributeValue(null, PaperXmlTag.PRINTING_COUNTRY.getValue()));
        if (country == null) {
            currentPaper.setCountry(PrintingCountry.valueOf("BELARUS"));
        } else {
            currentPaper.setCountry(country);
        }

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName().toUpperCase().replace(HYPHEN, UNDERSCORE);
                    buildPaperProperties(reader, name, currentPaper);
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (name.equals(PaperXmlTag.PAPER.getValue()) || name.equals(PaperXmlTag.MAGAZINE.getValue())
                    || name.equals(PaperXmlTag.BOOKLET.getValue())) {
                        return currentPaper;
                    }
                }
            }
        }

        throw new XMLStreamException("unknown tag");
    }

    private void buildPaperProperties(XMLStreamReader reader, String name, AbstractPaper paper) throws XMLStreamException{
        String data = getXMLText(reader);
        switch (PaperXmlTag.valueOf(name)){
            case TITLE -> {
                paper.setTitle(data);
            }
            case SIZE -> {
                paper.setSize(Integer.parseInt(data));
            }
            case MONTHLY -> {
                paper.setMonthly(Boolean.parseBoolean(data));
            }
            case PRINTING_DATE -> {
                paper.setPrintingDate(LocalDate.parse(data));
            }
            case COLOURED -> {
                paper.setColoured(Boolean.parseBoolean(data));
            }
            case TYPE -> {
                ;
            }
            case SUBSCRIPTION_INDEX -> {
                if(paper instanceof Paper){
                    Paper temp = (Paper) paper;
                    temp.setSubsriable(Boolean.parseBoolean(data));
                }
                else if(paper instanceof Magazine){
                    Magazine temp = (Magazine)paper;
                    temp.setSubsriable(Boolean.parseBoolean(data));
                }
            }
            case PERIODICITY -> {
                if(paper instanceof Paper){
                    Paper temp = (Paper) paper;
                    temp.setPeriodicity(Periodicity.getPeriodicityFromString(data));
                }
                else if(paper instanceof Magazine){
                    Magazine temp = (Magazine)paper;
                    temp.setPeriodicity(Periodicity.getPeriodicityFromString(data));
                }
            }
            case GLOSSY -> {
                if(paper instanceof Booklet){
                    Booklet temp = (Booklet) paper;
                    temp.setGlossy(Boolean.parseBoolean(data));
                }
                else if(paper instanceof Magazine){
                    Magazine temp = (Magazine)paper;
                    temp.setGlossy(Boolean.parseBoolean(data));
                }
            }
            default -> {
                logger.error("unknown tag");
                throw new XMLStreamException("unknown tag");
            }
        }

    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
