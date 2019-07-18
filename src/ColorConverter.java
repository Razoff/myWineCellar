import java.awt.*;

// TODO This should be an (two) enums

public class ColorConverter {
    // {"Red", "Pink", "White", "Yellow", "Blue", "Purple", "Brown", "Black"});
    public static String wineTypeToColor(String winetype){
        switch (winetype){
            case "Red wine":
                return "Red";
            case "Rosé wine":
                return "Pink";
            case "White wine":
                return "White";
            case "Late harvest":
                return "Yellow";
            case "Champagne":
                return "Blue";
            case "Hard liquor":
                return "Brown";
            case "Special":
                return "Purple";
            default:
                return "Black";
        }
    }

    public static Color colorToColor(String color){
        switch (color){
            case "Red":
                return new Color(153,0,0);
            case "Pink":
                return new Color(255,102,102);
            case "White":
                return new Color(255,255,204);
            case "Yellow":
                return new Color(255,255,0);
            case "Blue":
                return new Color(51, 153, 255);
            case "Brown":
                return new Color(102,51,0);
            case "Purple":
                return new Color(102,0,153); // FOLLOW THE DOC
            default:
                return new Color(0,0,0);
        }
    }
}
