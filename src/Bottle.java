
public class Bottle {
    private int year;
    private String color;
    private String name;
    private String appelation;
    private double price;
    private String currency;
    private int quantity;
    private String comment;

    public Bottle(int year, String color, String name, String appelation, double price, String currency, int quantity, String comment){
        this.year = year;
        this.color = color;
        this.name = name;
        this.appelation = appelation;
        this.price = price;
        this.currency = currency;
        this.quantity = quantity;
        this.comment = comment;
    }

    public Bottle(Object[] elems){
        if(elems.length == 7) { // OLD format
            this.year = new Integer(elems[0].toString());
            this.color = elems[1].toString();
            this.name = elems[2].toString();
            this.appelation = elems[3].toString();
            this.price = new Double(elems[4].toString());
            this.currency = "CHF"; // DEFAULT VALLUE
            this.quantity = new Integer(elems[5].toString());
            this.comment = elems[6].toString();
        }else if (elems.length == 8){ // NEW format
            this.year = new Integer(elems[0].toString());
            this.color = elems[1].toString();
            this.name = elems[2].toString();
            this.appelation = elems[3].toString();
            this.price = new Double(elems[4].toString());
            this.currency = elems[5].toString();
            this.quantity = new Integer(elems[6].toString());
            this.comment = elems[7].toString();
        }else {throw new IllegalArgumentException("Badd Object[] format");}
    }

    public Bottle(String from_db){ // Fromat of this string is "year | name | ... "
        String[] elems = from_db.replace("\n", "").split(" \\| ");

        if (elems.length == 7) {
            this.year = new Integer(elems[0]);
            this.color = elems[1];
            this.name = elems[2];
            this.appelation = elems[3];
            this.price = new Double(elems[4]);
            this.currency = "CHF";
            this.quantity = new Integer(elems[5]);
            this.comment = elems[6];
        } else if (elems.length == 8){
            this.year = new Integer(elems[0]);
            this.color = elems[1];
            this.name = elems[2];
            this.appelation = elems[3];
            this.price = new Double(elems[4]);
            this.currency = elems[5];
            this.quantity = new Integer(elems[6]);
            this.comment = elems[7];
        }else {throw new IllegalArgumentException("Bad string bottle format");}
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

    public String getCurrency() {
        return currency;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getComment() {
        return comment;
    }

    public String toString(){ // Eventually will be renamed to string
        return Integer.toString(year) + " | " + color + " | " + name + " | " + appelation + " | " +
                Double.toString(price) + " | " + currency + " | " + Integer.toString(quantity) + " | " + comment ;
    }

    public Object[] arrayification(){
        return new Object[] {year, color, name, appelation, price, currency, quantity, comment};
    }

    public static String[] getHeaders(){
        return new String[] {"Year", "Color", "Name", "Appellation", "Price", "Currency" ,"Quantity" , "Comment"};
    }
}
