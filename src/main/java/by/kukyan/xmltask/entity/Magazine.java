package by.kukyan.xmltask.entity;

public class Magazine extends AbstractPaper {
    private boolean isSubsriable;
    private Periodicity periodicity;
    private boolean isGlossy;

    public Magazine(){
        super();
    }

    public Magazine(String newId, PrintingCountry newCountry, String newTitle, int newSize, boolean coloured, boolean monthly, boolean glossy, Periodicity period, boolean subsriable){
        super(newId, newCountry, newTitle, newSize, coloured, monthly);
        isSubsriable = subsriable;
        periodicity =period;
        isGlossy = glossy;
    }

    public boolean isSubsriable() {
        return isSubsriable;
    }

    public void setSubsriable(boolean subsriable) {
        isSubsriable = subsriable;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public boolean isGlossy() {
        return isGlossy;
    }

    public void setGlossy(boolean glossy) {
        isGlossy = glossy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Magazine)) return false;
        Magazine second = (Magazine) o;
        if (getCountry() != second.getCountry() || getId() != second.getId() || !getTitle().equals(second.getTitle()) || second.getSize() != getSize() || isMonthly() != second.isMonthly() || isColoured() != second.isColoured() || !periodicity.equals(second.periodicity) || isSubsriable != second.isSubsriable || isGlossy != second.isGlossy){
            //периписать набором ifов
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode() + periodicity.hashCode() + (isGlossy ? 1 : 0) + (isSubsriable ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Magazine{");
        sb.append("id='").append(getId());
        sb.append(", country=").append(getCountry());
        sb.append(", title=").append(getTitle());
        sb.append(", size=").append(getSize());
        sb.append(", isMonthly='").append(isMonthly());
        sb.append(", isColoured=").append(isColoured());
        sb.append(", isSubscriable=").append(isSubsriable);
        sb.append(", periodicity").append(periodicity);
        sb.append(", isGlossy").append(isGlossy);
        sb.append('}');
        return sb.toString();
    }
}
