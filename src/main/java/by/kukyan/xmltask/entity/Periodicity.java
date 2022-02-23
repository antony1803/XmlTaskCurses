package by.kukyan.xmltask.entity;

public enum Periodicity {
    ONCEPERDAY("P1D"),
    ONCEPERWEEK("P7D"),
    ONCEPERMONTH("P1M"),
    ONCEPERYEAR("P1Y");

    private final String value;

    Periodicity(String val){
        value = val;
    }

    public String getPeriodicity(){
        return value;
    }

    public static Periodicity getPeriodicityFromString(String temp){
        for(var count : Periodicity.values()){
            if(count.value.equalsIgnoreCase(temp)){
                return count;
            }
        }
       return null;
    }
}
