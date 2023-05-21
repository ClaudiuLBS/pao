public class Asset implements Comparable<Asset> {
    private Integer id;
    private String name;
    private String abbreviation;
    private Double value;

    public Asset() {

    }

    public Asset(Integer id, String name, String abbreviation, Double value) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.value = value;
    }
    public Integer getId() {
        return id;
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

    @Override
    public String toString() {
        return "Name = " + name +
                "\nAbbreviation='" + abbreviation +
                "\nValue=" + value;
    }

    @Override
    public int compareTo(Asset otherAsset) {
        return this.name.compareTo(otherAsset.name);
    }
}
