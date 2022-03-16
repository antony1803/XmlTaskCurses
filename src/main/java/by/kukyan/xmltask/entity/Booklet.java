package by.kukyan.xmltask.entity;

import java.time.LocalDate;

public class Booklet extends AbstractPaper{
    private boolean isGlossy;

    public Booklet(){
        super();
    }

    public Booklet(String newId, PrintingCountry newCountry, String newTitle, int newSize, boolean coloured, boolean monthly, LocalDate date, boolean glossy) {
        super(newId, newCountry, newTitle, newSize, coloured, monthly, date);
        isGlossy = glossy;
    }

    public static Booklet setNewBooklet(AbstractPaper paper, boolean isGlossy){
        return new Booklet(paper.getId(), paper.getCountry(), paper.getTitle(), paper.getSize(), paper.isColoured(), paper.isMonthly(), paper.getPrintingDate(), isGlossy);
    }

    public boolean isGlossy() {
        return isGlossy;
    }

    public void setGlossy(boolean glossy) {
        isGlossy = glossy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null){return false;}
        if (!(o instanceof Booklet)) {return false;}
        Booklet second = (Booklet) o;
        if (getCountry() != second.getCountry()){return false;}
        if (!getId().equals(second.getId())){return false;}
        if (!getTitle().equals(second.getTitle())){return false;}
        if (second.getSize() != getSize()){return false;}
        if (isMonthly() != second.isMonthly()){return false;}
        if (isColoured() != second.isColoured()){return false;}
        if(!getPrintingDate().equals(second.getPrintingDate())){return false;}
        if (isGlossy != second.isGlossy){return false;}
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode() + (isGlossy ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Booklet{");
        sb.append("id='").append(getId());
        sb.append(", country=").append(getCountry());
        sb.append(", title=").append(getTitle());
        sb.append(", size=").append(getSize());
        sb.append(", isMonthly='").append(isMonthly());
        sb.append(", isColoured=").append(isColoured());
        sb.append(", isGlossy=").append(isGlossy);
        sb.append('}');
        return sb.toString();
    }
}
