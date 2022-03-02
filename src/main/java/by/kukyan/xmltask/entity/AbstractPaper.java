package by.kukyan.xmltask.entity;

import java.time.LocalDate;

public abstract class AbstractPaper {
    private String id;
    private PrintingCountry country;
    private String title;
    private int size;
    private boolean isColoured;
    private boolean isMonthly;
    private LocalDate printingDate;

    public AbstractPaper(){}

    public AbstractPaper(String newId, PrintingCountry newCountry, String newTitle, int newSize, boolean coloured, boolean monthly, LocalDate date){
        id = newId;
        country = newCountry;
        title = newTitle;
        size = newSize;
        isColoured = coloured;
        isMonthly = monthly;
        printingDate = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PrintingCountry getCountry() {
        return country;
    }

    public void setCountry(PrintingCountry country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isColoured() {
        return isColoured;
    }

    public void setColoured(boolean coloured) {
        isColoured = coloured;
    }

    public boolean isMonthly() {
        return isMonthly;
    }

    public void setMonthly(boolean monthly) {
        isMonthly = monthly;
    }

    public LocalDate getPrintingDate() {
        return printingDate;
    }

    public void setPrintingDate(LocalDate printingDate) {
        this.printingDate = printingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if(o == null){return false;}
        if (!(o instanceof AbstractPaper)) {return false;}
        AbstractPaper second = (AbstractPaper) o;
        if (country != second.country){return false;}
        if (id != second.id){return false;}
        if(!title.equals(second.title)){return false;}
        if(second.size != size){return false;}
        if(isMonthly != second.isMonthly){return false;}
        if(isColoured != second.isColoured){return false;}
        if(!printingDate.equals(second.printingDate)){return false;}
       return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result += title.hashCode();
        result += size;
        result += printingDate.hashCode();
        result += country.hashCode();
        result += isMonthly ? 1 : 0;
        result += isColoured ? 1 :0;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Paper{");
        sb.append("id='").append(id);
        sb.append(", country=").append(country);
        sb.append(", title=").append(title);
        sb.append(", size=").append(size);
        sb.append(", isMonthly='").append(isMonthly);
        sb.append(", isColoured=").append(isColoured);
        sb.append(", printed ").append(printingDate);
        sb.append('}');
        return sb.toString();
    }
}
