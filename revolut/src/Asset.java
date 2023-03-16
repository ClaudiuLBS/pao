public class Asset {
    protected String name;
    protected String abbreviation;
    protected Double value;

    public Asset(){

    }
    public Asset(String name, String abbreviation, Double value){
        this.name = name;
        this.abbreviation = abbreviation;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
