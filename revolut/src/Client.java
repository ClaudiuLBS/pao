import java.util.Map;
import java.util.Vector;

public class Client {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private Boolean premiumAccount = false;
    private Map<Asset, Float> assetsOwned;

    public Client() {

    }

    public Client(
            String firstName,
            String lastName,
            String phoneNumber,
            String address,
            Boolean premiumAccount,

            Map<Asset, Float> assetsOwned) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.premiumAccount = premiumAccount;
        this.assetsOwned = assetsOwned;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Map<Asset, Float> getAssetsOwned() {
        return assetsOwned;
    }

    public void setAssetsOwned(Map<Asset, Float> assetsOwned) {
        this.assetsOwned = assetsOwned;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getPremiumAccount() {
        return premiumAccount;
    }

    public void setPremiumAccount(Boolean premiumAccount) {
        this.premiumAccount = premiumAccount;
    }

}
