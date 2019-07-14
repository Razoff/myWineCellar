public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");
        Cellar cellar = new Cellar();

        cellar.addBottle(new Bottle(1993, "red", "Chateau Arnaud", "Rioja", 12.50, 11, "A boire avant 2025"));
    }
}
