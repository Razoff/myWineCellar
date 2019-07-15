public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");
        Cellar cellar = new Cellar();

        System.out.println(cellar.toString());

        //cellar.addBottle(new Bottle(2012, "Brown", "Whisky", "WHisky", 125, 1, "Ce n'est pas du vin"));

        //System.out.println(cellar);

        cellar.backup();

        //cellar.addBottle(new Bottle(1993, "red", "Chateau Arnaud", "Rioja", 12.50, 11, "A boire avant 2025"));
        //ListDisplayer disp = new ListDisplayer(cellar);

        //disp.display();

    }
}
