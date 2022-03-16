package by.kukyan.xmltask.builder;

public enum PaperXmlTag {
    PAPERS("papers"),
    PAPER("paper"),
    BOOKLET("booklet"),
    MAGAZINE("magazine"),
    ID("id"),
    PRINTING_COUNTRY("printing-country"),

    TITLE("title"),
    SIZE("size"),
    MONTHLY("monthly"),
    PRINTING_DATE("printing-date"),
    COLOURED("coloured"),
    TYPE("type"),
    SUBSCRIPTION_INDEX("subscription-index"),
    PERIODICITY("periodicity"),
    GLOSSY("glossy");

    private String value;

    PaperXmlTag(String newValue){
        value = newValue;
    }

    public String getTagName(){
        return value;
    }
}
