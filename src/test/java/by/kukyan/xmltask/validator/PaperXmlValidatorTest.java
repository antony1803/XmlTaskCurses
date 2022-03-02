package by.kukyan.xmltask.validator;

import by.kukyan.xmltask.exception.PaperException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PaperXmlValidatorTest {
    private static final String XML_FILE = "data/papers.xml";
    private static final String XML_FILE_WRONG = "data/wrong.xml";


    @Test
    public void testIsValidXML() {
        boolean actual = false;
        try {
            actual = PaperXmlValidator.validateXml(XML_FILE);
        } catch (PaperException e) {
            e.printStackTrace();
        }
        assertTrue(actual);
    }

    @Test
    public void testIsNotValid(){
        boolean actual = false;
        try {
            actual = PaperXmlValidator.validateXml(XML_FILE_WRONG);
        } catch (PaperException e) {
            fail(e.getMessage(),e);
        }
        assertFalse(actual);
    }
}
