package by.kukyan.xmltask.entity;

public enum PrintingCountry {
    BELARUS("Belarus"),
    USA("USA"),
    ENGLAND("England"),
    POLAND("Poland"),
    RUSSIA("Russia");

    private final String namesOfCountries;

    PrintingCountry(String val){
        namesOfCountries = val;
    }

    public String getCountries(){
        return namesOfCountries;
    }

    public static PrintingCountry getCountryFromString(String temp){
        for(var count : PrintingCountry.values()){
            if(count.namesOfCountries.equalsIgnoreCase(temp)){
                return count;
            }
        }
        return null;
    }
}
