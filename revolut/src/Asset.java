public class Asset {
    protected String name;
    protected String abbr;
    protected Double value;

    public Asset(){

    }
    public Asset(String name, String abbr, Double value){
        this.name = name;
        this.abbr = abbr;
        this.value = value;
    }
}
