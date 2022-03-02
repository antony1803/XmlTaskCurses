package by.kukyan.xmltask.entity;

import java.time.LocalDate;

public class Paper extends AbstractPaper{
    private boolean isSubsriable;
    private Periodicity periodicity;

    public Paper(){
        super();
    }

    public Paper(String newId, PrintingCountry newCountry, String newTitle, int newSize, boolean coloured, boolean monthly, LocalDate date, Periodicity period, boolean subsriable){
        super(newId, newCountry, newTitle, newSize, coloured, monthly, date);
        isSubsriable = subsriable;
        periodicity =period;
    }

    public static Paper setNewPaper(AbstractPaper paper, Periodicity period, boolean subsriable){
        return new Paper(paper.getId(), paper.getCountry(), paper.getTitle(), paper.getSize(), paper.isColoured(), paper.isMonthly(), paper.getPrintingDate(), period, subsriable);
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

    @Override
    public boolean equals(Object o) {
        if (this == o){return true;}
        if(o == null){return false;}
        if (!(o instanceof Paper)){return false;}
        Paper second = (Paper) o;
        if (getCountry() != second.getCountry()){return false;}
        if (getId() != second.getId()){return false;}
        if (!getTitle().equals(second.getTitle())){return false;}
        if (second.getSize() != getSize()){return false;}
        if (isMonthly() != second.isMonthly()){return false;}
        if(!getPrintingDate().equals(second.getPrintingDate())){return false;}
        if (isColoured() != second.isColoured()){return false;}
        if (!periodicity.equals(second.periodicity)){return false;}
        if (isSubsriable != second.isSubsriable){return false;}
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode() + periodicity.hashCode() + (isSubsriable ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Paper{");
        sb.append("id='").append(getId());
        sb.append(", country=").append(getCountry());
        sb.append(", title=").append(getTitle());
        sb.append(", size=").append(getSize());
        sb.append(", isMonthly='").append(isMonthly());
        sb.append(", printed ").append(getPrintingDate());
        sb.append(", isColoured=").append(isColoured());
        sb.append(", isSubscriable=").append(isSubsriable);
        sb.append(", periodicity").append(periodicity);
        sb.append('}');
        return sb.toString();
    }
}
