package by.kukyan.xmltask.builder;

import by.kukyan.xmltask.entity.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class PaperHandler extends DefaultHandler {
    private static Logger logger = LogManager.getLogger();

    private final Set<AbstractPaper> papers;
    private EnumSet<PaperXmlTag> xmlTags;

    private AbstractPaper currentPaper;
    private PaperXmlTag currentXmlTag;

    public void add(AbstractPaper paper){
        papers.add(paper);
    }

    public PaperHandler() {
        papers = new HashSet<>();
        xmlTags = EnumSet.range(PaperXmlTag.TITLE, PaperXmlTag.GLOSSY);
    }

    public Set<AbstractPaper> getPapers(){
        return papers;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
        if(qName.equals(PaperXmlTag.PAPER.getValue()) ||
            qName.equals(PaperXmlTag.BOOKLET.getValue())||
            qName.equals(PaperXmlTag.MAGAZINE.getValue())){

            currentPaper = qName.equals(PaperXmlTag.PAPER.getValue()) ? new Paper() :
                    (qName.equals(PaperXmlTag.MAGAZINE.getValue()) ? new Magazine() : new Booklet());

            currentPaper.setId(attributes.getValue(0));
            currentPaper.setCountry(PrintingCountry.getCountryFromString(attributes.getValue(1)));
        }
        else{
            PaperXmlTag temp = PaperXmlTag.valueOf(qName.toUpperCase().replace("-", "_"));
            if(xmlTags.contains(temp)){
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        String paperTag = PaperXmlTag.PAPER.getValue();
        String bookletTag = PaperXmlTag.BOOKLET.getValue();
        String magazineTag = PaperXmlTag.MAGAZINE.getValue();

        if(qName.equals(paperTag) || qName.equals(bookletTag) || qName.equals(magazineTag)){
            papers.add(currentPaper);
            currentPaper = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length);

        if(currentXmlTag != null){
            switch (currentXmlTag){
                case TITLE -> {
                    currentPaper.setTitle(data);
                }
                case SIZE -> {
                    currentPaper.setSize(Integer.parseInt(data));
                }
                case MONTHLY -> {
                    currentPaper.setMonthly(Boolean.getBoolean(data));
                }
                case COLOURED -> {
                    currentPaper.setColoured(Boolean.getBoolean(data));
                }
                case TYPE -> {
                    ;
                }
                case SUBSCRIABLE -> {
                    if(currentPaper instanceof Paper){
                        Paper temp = (Paper) currentPaper;
                        temp.setSubsriable(Boolean.getBoolean(data));
                    }
                    else if(currentPaper instanceof Magazine){
                        Magazine temp = (Magazine)currentPaper;
                        temp.setSubsriable(Boolean.getBoolean(data));
                    }
                }
                case PERIODICITY -> {
                    if(currentPaper instanceof Paper){
                        Paper temp = (Paper) currentPaper;
                        temp.setPeriodicity(Periodicity.getPeriodicityFromString(data));
                    }
                    else if(currentPaper instanceof Magazine){
                        Magazine temp = (Magazine)currentPaper;
                        temp.setPeriodicity(Periodicity.getPeriodicityFromString(data));
                    }
                }
                case GLOSSY -> {
                    if(currentPaper instanceof Booklet){
                        Booklet temp = (Booklet) currentPaper;
                        temp.setGlossy(Boolean.getBoolean(data));
                    }
                    else if(currentPaper instanceof Magazine){
                        Magazine temp = (Magazine)currentPaper;
                        temp.setGlossy(Boolean.getBoolean(data));
                    }
                }
            }
        }
        currentXmlTag = null;
    }
}