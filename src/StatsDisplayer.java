import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StatsDisplayer {
    private final int year_id = 0;
    private final int color_id = 1;
    private final int appelation_id = 3;
    private final int price_id = 4;
    private final int quantity_id = 5; // Position of quantity field

    private Object[][] cellar_bottles;

    private Map<String, Integer> color_map = new HashMap<>();
    private Map<String, Integer> appelation_map = new HashMap<>();
    private int total_bottles = 0;
    private int oldest_bottle = 3000;
    private double most_expensive = 0.0;
    private double total_price = 0.0;

    public StatsDisplayer(Object[][] cellar){
        this.cellar_bottles = cellar;

        color_map.put("Red", 0);
        color_map.put("Pink", 0);
        color_map.put("White", 0);
        color_map.put("Yellow", 0);
        color_map.put("Blue", 0);
        color_map.put("Brown", 0);
        color_map.put("Purple", 0);
        color_map.put("Black", 0);

        create_stats();
    }

    public void Display(){
        System.out.println("Stats");
    }

    private void create_stats(){
        for(Object[] bottle : cellar_bottles){
            try {
                int quantity = (Integer) bottle[quantity_id];
                int year = (Integer) bottle[year_id];
                double price = (Double) bottle[price_id];

                color_map.merge(bottle[color_id].toString(), quantity, Integer::sum);
                appelation_map.merge(bottle[appelation_id].toString(), quantity, Integer::sum);
                total_bottles += quantity;
                total_price += price;

                if (year < oldest_bottle) {
                    oldest_bottle = year;
                }

                if (price > most_expensive) {
                    most_expensive = price;
                }
            }catch (Exception e){
                System.out.println("Error with bottle : " + Arrays.deepToString(bottle) + "\n" + e);
            }
        }
    }

    @Override
    public String toString() {
        String ret = "";

        ret += Arrays.toString(color_map.entrySet().toArray()) + "\n";
        ret += Arrays.toString(appelation_map.entrySet().toArray()) + "\n";
        ret += "Total number of bottles : " + total_bottles + "\n";
        ret += "Oldest bottle in cellar : " + oldest_bottle + "\n";
        ret += "Most expensive bottle in cellar : " + most_expensive + "\n";
        ret += "Total value of the cellar : " + total_price + "\n";

        return ret;

    }
}
