package by.kukyan.xmltask.builder;

import by.kukyan.xmltask.exception.PaperException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaperBuilderFactory {
    private static Logger logger = LogManager.getLogger();
    private static final PaperBuilderFactory instance = new PaperBuilderFactory();
    private PaperBuilderFactory(){}

    public static PaperBuilderFactory getInstance(){
        return instance;
    }

    public AbstractPaperBuilder createPaperBuilder(ParserType type) throws PaperException{
      switch (type) {
          case SAX -> {
              return new PaperSaxBuilder();
          }
          case DOM -> {
              return new PaperDomBuilder();
          }
          case STAX -> {
              return new PaperStaxBuilder();
          }
          default -> {
              throw new PaperException("Constant is not present in enum" + type);
          }
      }

    }
}
