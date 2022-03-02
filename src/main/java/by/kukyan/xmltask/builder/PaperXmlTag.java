package by.kukyan.xmltask.builder;

public enum PaperXmlTag {
    PAPERS("papers"),
    PAPER("paper"),
    BOOKLET("booklet"),
    MAGAZINE("magazine"),
    ID("id"),
    COUNTRY("printing-country"),

    TITLE("title"),
    SIZE("size"),
    MONTHLY("monthly"),
    PRINTED("printing-date"),
    COLOURED("coloured"),
    TYPE("type"),
    SUBSCRIABLE("subscription-index"),
    PERIODICITY("periodicity"),
    GLOSSY("glossy");

    private String value;

    PaperXmlTag(String newValue){
        value = newValue;
    }

    public String getValue(){
        return value;
    }
}
