
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

    public Bottle(String from_db){ // Fromat of this string is "year | name | ... "
        String[] elems = from_db.replace("\n", "").split(" \\| ");

        if (elems.length != 7){throw new IllegalArgumentException("Bad string bottle format");}

        this.year = new Integer(elems[0]);
        this.color = elems[1];
        this.name = elems[2];
        this.appelation = elems[3];
        this.price = new Double(elems[4]);
        this.quantity = new Integer(elems[5]);
        this.comment = elems[6];
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

    public String toString(){ // Eventually will be renamed to string
        return Integer.toString(year) + " | " + color + " | " + name + " | " + appelation + " | " +
                Double.toString(price) + " | " + Integer.toString(quantity) + " | " + comment ;
    }

    public Object[] arrayification(){
        return new Object[] {year, color, name, appelation, price, quantity, comment};
    }

    public static String[] getHeaders(){
        return new String[] {"Year", "Color", "Name", "Appellation", "Price", "Quantity" , "Comment"};
    }
}
