package by.kukyan.xmltask.builder;

import by.kukyan.xmltask.entity.AbstractPaper;
import by.kukyan.xmltask.exception.PaperException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPaperBuilder {
    protected Set<AbstractPaper> papers;

    public AbstractPaperBuilder(){
        papers = new HashSet<>();
    }

    public AbstractPaperBuilder(Set<AbstractPaper> newPapers){
        papers = newPapers;
    }

    public Set<AbstractPaper> getPapers(){
        return papers;
    }

    public abstract void buildPapers(String path) throws PaperException;
}
