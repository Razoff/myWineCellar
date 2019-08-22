import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsDisplayer {

    // Paths
    private final String path_cellar_dir = "./cellar" ;
    private final String path_color_map = "./cellar/color_map";
    private final String path_appelation_map = "./cellar/appelation_map";

    // Indexes
    private final int year_id = 0;
    private final int color_id = 1;
    private final int appelation_id = 3;
    private final int price_id = 4;
    private final int quantity_id = 6; // Position of quantity field

    // Cellar
    private Object[][] cellar_bottles;

    // Stats
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
    }

    public void Display(){
        System.out.println("Stats");

        create_stats();
        create_chart_color();
        create_appelation_chart();
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

    private void create_chart_color(){
        // Create chart
        PieChart color_chart = new PieChartBuilder().width(800).height(600).title("Color chart").build();

        // Set color
        Color[] sliceColors = new Color[]{
                new Color(153,0,0),
                new Color(255,102,102) ,
                new Color(255,255,204) ,
                new Color(255,255,0) ,
                new Color(51, 153, 255) ,
                new Color(102,51,0) ,
                new Color(102,0,153) ,
                new Color(0,0,0)
        };

        // Customize chart
        color_chart.getStyler().setSeriesColors(sliceColors);
        //color_chart.getStyler().setLegendVisible(false);
        color_chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
        color_chart.getStyler().setDrawAllAnnotations(true);
        color_chart.getStyler().setAnnotationDistance(1.15);
        color_chart.getStyler().setPlotContentSize(.7);
        color_chart.getStyler().setStartAngleInDegrees(90);


        // Series
        color_chart.addSeries("Red wine", color_map.get("Red"));
        color_chart.addSeries("Rosé wine", color_map.get("Pink"));
        color_chart.addSeries("White wine", color_map.get("White"));
        color_chart.addSeries("Late Harvest", color_map.get("Yellow"));
        color_chart.addSeries("Champagne", color_map.get("Blue"));
        color_chart.addSeries("Hard liquor", color_map.get("Brown"));
        color_chart.addSeries("Special", color_map.get("Purple"));
        color_chart.addSeries("Error", color_map.get("Black"));

        // Display
        JFrame frame = new JFrame("Color pie");
        frame.setLayout(new BorderLayout());
        frame.setLocation(0,600);

        // chart
        JPanel chartPanel = new XChartPanel<PieChart>(color_chart);
        frame.add(chartPanel, BorderLayout.CENTER);

        // label
        final String wine_quantity =
                "Red wine : " + color_map.get("Red") + "    " +
                "White wine : " + color_map.get("White") + "    " +
                "Rosé wine : " + color_map.get("Pink") + "    " +
                "Late harvest : " + color_map.get("Yellow") + "    " +
                "Champagne : " + color_map.get("Blue") + "    " +
                "Hard liquor : " + color_map.get("Brown") + "    " +
                "Special : " + color_map.get("Purple") + "    " +
                "Error : " + color_map.get("Black") ;

        JLabel label = new JLabel(wine_quantity, SwingConstants.CENTER);
        frame.add(label, BorderLayout.SOUTH);

        // Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    private void create_appelation_chart(){
        PieChart appelation_chart = new PieChartBuilder().width(800).height(600).title("Appelation chart").build();

        appelation_map.forEach((k,v) -> appelation_chart.addSeries(k,v));

        final String appelation_string = appelation_map.entrySet().stream().map(Object::toString).collect(Collectors.joining("  ,   "));

        // Display
        JFrame frame = new JFrame("Color pie");
        frame.setLayout(new BorderLayout());
        frame.setLocation(0,0);

        // chart
        JPanel chartPanel = new XChartPanel<PieChart>(appelation_chart);
        frame.add(chartPanel, BorderLayout.CENTER);

        JLabel label = new JLabel(appelation_string, SwingConstants.CENTER);
        frame.add(label, BorderLayout.SOUTH);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
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
