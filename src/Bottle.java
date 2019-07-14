
public class Bottle {
    private int year;
    private String color;
    private String name;
    private String appelation;
    private double price;
    private int quantity;
    private String comment;

    public Bottle(int year, String color, String name, String appelation, double price, int quantity, String comment){
        this.year = year;
        this.color = color;
        this.name = name;
        this.appelation = appelation;
        this.price = price;
        this.quantity = quantity;
        this.comment = comment;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getAppelation() {
        return appelation;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getComment() {
        return comment;
    }

    public String dataification(){
        return Integer.toString(year) + " | " + color + " | " + name + " | " + appelation + " | " +
                Double.toString(price) + " | " + Integer.toString(quantity) + " | " + comment ;
    }
}
