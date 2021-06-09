public class OrderInfo {
    private int orderId;
    private String datePlaced;
    private double grossPrice;
    private double taxPercent;
    private String foodName;
    private String companyName;
    private String nip;
    private String firstName;
    private String secondName;

    public OrderInfo(int orderId, String datePlaced, double grossPrice, String foodName,double taxPercent, String companyName,
                     String nip, String firstName, String secondName){
        this.orderId = orderId;
        this.datePlaced = datePlaced;
        this.grossPrice = grossPrice;
        this.taxPercent = taxPercent;
        this.foodName = foodName;
        this.companyName = companyName;
        this.nip = nip;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(String datePlaced) {
        this.datePlaced = datePlaced;
    }

    public double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
