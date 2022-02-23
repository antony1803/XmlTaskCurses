package by.kukyan.xmltask.builder;

import by.kukyan.xmltask.exception.PaperException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URL;

public class PaperSaxBuilder extends AbstractPaperBuilder{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void buildPapers(String path) throws PaperException {
        try{
            ClassLoader loader = getClass().getClassLoader();
            URL resource = loader.getResource(path);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            PaperHandler handler = new PaperHandler();

            reader.setContentHandler(handler);
            reader.parse(resource.getFile());

            papers.addAll(handler.getPapers());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("sax error", e);
            throw new PaperException("sax error", e);
        }
    }
}
