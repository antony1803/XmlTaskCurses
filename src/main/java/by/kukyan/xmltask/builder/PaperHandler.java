package by.kukyan.xmltask.builder;

import by.kukyan.xmltask.entity.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
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
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if(qName.equals(PaperXmlTag.PAPER.getTagName()) ||
            qName.equals(PaperXmlTag.BOOKLET.getTagName())||
            qName.equals(PaperXmlTag.MAGAZINE.getTagName())){

            currentPaper = qName.equals(PaperXmlTag.PAPER.getTagName()) ? new Paper() :
                    (qName.equals(PaperXmlTag.MAGAZINE.getTagName()) ? new Magazine() : new Booklet());

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
    public void endElement(String uri, String localName, String qName){
        String paperTag = PaperXmlTag.PAPER.getTagName();
        String bookletTag = PaperXmlTag.BOOKLET.getTagName();
        String magazineTag = PaperXmlTag.MAGAZINE.getTagName();

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
                    currentPaper.setMonthly(Boolean.parseBoolean(data));
                }
                case PRINTING_DATE -> {
                    currentPaper.setPrintingDate(LocalDate.parse(data));
                }
                case COLOURED -> {
                    currentPaper.setColoured(Boolean.parseBoolean(data));
                }
                case TYPE -> {
                    ;
                }
                case SUBSCRIPTION_INDEX -> {
                    if(currentPaper instanceof Paper){
                        Paper temp = (Paper) currentPaper;
                        temp.setSubsriable(Boolean.parseBoolean(data));
                    }
                    else if(currentPaper instanceof Magazine){
                        Magazine temp = (Magazine)currentPaper;
                        temp.setSubsriable(Boolean.parseBoolean(data));
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
                        temp.setGlossy(Boolean.parseBoolean(data));
                    }
                    else if(currentPaper instanceof Magazine){
                        Magazine temp = (Magazine)currentPaper;
                        temp.setGlossy(Boolean.parseBoolean(data));
                    }
                }
            }
        }
        currentXmlTag = null;
    }
}
