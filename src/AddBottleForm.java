import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.*;

public class AddBottleForm {
    public static Bottle display() {

        // Year field
        ArrayList<Integer> years_tmp = new ArrayList<Integer>();
        for(int year = 1900 ; year <= Calendar.getInstance().get(Calendar.YEAR); year++){
            years_tmp.add(year);
        }
        JComboBox<Integer> years = new JComboBox<Integer>(years_tmp.toArray(new Integer[years_tmp.size()]));

        // Color field
        JComboBox<String> colors = new JComboBox<String>(new String[]{"Red wine", "Rosé wine", "White wine", "Late harvest", "Champagne", "Hard liquor", "Special", "Black"});

        // Name field
        JTextField name = new JTextField();

        // Appelation field
        JTextField appelation = new JTextField();

        // Price field
        JTextField price = new JTextField();

        // Quantity field
        ArrayList<Integer> quantity_tmp = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++){
            quantity_tmp.add(i);
        }
        JComboBox<Integer> quantity = new JComboBox<Integer>(quantity_tmp.toArray(new Integer[quantity_tmp.size()]));

        // Comment field
        JTextField comment = new JTextField();

        // Add in panel
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Year"));
        panel.add(years);
        panel.add(new JLabel("Color"));
        panel.add(colors);
        panel.add(new JLabel("Name"));
        panel.add(name);
        panel.add(new JLabel("Appelation"));
        panel.add(appelation);
        panel.add(new JLabel("Price"));
        panel.add(price);
        panel.add(new JLabel("Quantity"));
        panel.add(quantity);
        panel.add(new JLabel("Comment"));
        panel.add(comment);

        // Quit action

        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("New bottle");
            try {
                return new Bottle((int) years.getSelectedItem(), ColorConverter.wineTypeToColor(colors.getSelectedItem().toString()), name.getText(),
                        appelation.getText(), new Double(price.getText()), (int) quantity.getSelectedItem(), comment.getText());
            }catch (Exception e){
                System.out.println("Error bottle format");
                return new Bottle(0, "Black", "Château erreur", "Erroris fungulis", 666, 1, e.getMessage());
            }
        } else {
            System.out.println("Cancelled");
            return null;
        }
    }
}
