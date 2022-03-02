package by.kukyan.xmltask.validator;

import by.kukyan.xmltask.exception.PaperException;

import org.xml.sax.SAXException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class PaperXmlValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String VALIDATION_SCHEME = "data/papers.xsd";

    public static boolean validateXml(String pathToXml) throws PaperException{
        ClassLoader loader = PaperXmlValidator.class.getClassLoader();
        URL xsdPath = loader.getResource(VALIDATION_SCHEME);
        File scheme = new File(xsdPath.getFile());
        URL xmlPath = loader.getResource(pathToXml);
        String path = new File(xmlPath.getFile()).getPath();
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try{
            Schema schema = factory.newSchema(scheme);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(path);
            validator.validate(source);
        } catch (SAXException e) {
            logger.warn("XML is invalid" + path, e);
            return false;
        } catch (IOException e) {
            logger.error("Can`t read file" + path, e);
            throw new PaperException("Can`t read file" + path, e);
        }
        return true;
    }
}
