package by.kukyan.xmltask.entity;

public enum PrintingCountry {
    BELARUS("Belarus"),
    USA("USA"),
    ENGLAND("England"),
    POLAND("Poland"),
    RUSSIA("Russia");

    private final String value;

    PrintingCountry(String val){
        value = val;
    }

    public String getValue(){
        return value;
    }

    public static PrintingCountry getCountryFromString(String temp){
        for(var count : PrintingCountry.values()){
            if(count.value.equalsIgnoreCase(temp)){
                return count;
            }
        }
        return null;
    }
}
