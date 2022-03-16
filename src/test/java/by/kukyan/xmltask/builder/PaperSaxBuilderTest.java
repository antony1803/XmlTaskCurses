package by.kukyan.xmltask.builder;

import by.kukyan.xmltask.entity.*;
import by.kukyan.xmltask.exception.PaperException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class PaperSaxBuilderTest {
    private static final String XML_FILE = "data/papers1.xml";
    private Set<AbstractPaper> expected;

    @BeforeClass
    public void setUp(){
        expected = new HashSet<>();

        expected.add(new Booklet("by-10001",
                PrintingCountry.BELARUS,
                "Орифлейм предлагает скидки",
                1,
                true,
                false,
                LocalDate.parse("2021-12-01"),
                true));

        expected.add(new Magazine("en-10002",
                PrintingCountry.ENGLAND,
                "Times",
                50,
                true,
                true,
                LocalDate.parse("2022-01-01"),
                true,
                Periodicity.ONCEPERMONTH,
                true));

    }

    @Test
    public void testBuildSetPapers(){
        AbstractPaperBuilder builder;
        Set<AbstractPaper> actual = new HashSet<>();
        try{
            builder = PaperBuilderFactory.getInstance().createPaperBuilder(ParserType.SAX);
            builder.buildPapers(XML_FILE);
            actual.addAll(builder.getPapers());
        } catch (PaperException e) {
            fail(e.getMessage(), e);
        }
        assertEquals(actual, expected);
    }
}
