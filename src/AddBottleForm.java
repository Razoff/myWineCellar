import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.*;

public class AddBottleForm {
    public static void display() {

        // Year field
        ArrayList<Integer> years_tmp = new ArrayList<Integer>();
        for(int year = 1900 ; year <= Calendar.getInstance().get(Calendar.YEAR); year++){
            years_tmp.add(year);
        }
        JComboBox<Integer> years = new JComboBox<Integer>(years_tmp.toArray(new Integer[years_tmp.size()]));

        // Color field
        JComboBox<String> colors = new JComboBox<String>(new String[]{"Red", "Pink", "White", "Yellow", "Blue", "Purple"});

        // Name field

        // Appelation field

        // Price field

        // Quantity field

        // Comment field

        // Add in panel

        // Quit action


        /*String[] items = {"One", "Two", "Three", "Four", "Five"};
        JComboBox<String> combo = new JComboBox<>(items);
        JTextField field1 = new JTextField("1234.56");
        JTextField field2 = new JTextField("9876.54"); */
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Year"));
        panel.add(years);
        panel.add(new JLabel("Color"));
        panel.add(colors);


        /*
        panel.add(combo);
        panel.add(new JLabel("Field 1:"));
        panel.add(field1);
        panel.add(new JLabel("Field 2:"));
        panel.add(field2);*/
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println(years.getSelectedItem());
            System.out.println(colors.getSelectedItem());
            /*System.out.println(combo.getSelectedItem()
                    + " " + field1.getText()
                    + " " + field2.getText());*/
        } else {
            System.out.println("Cancelled");
        }
    }
}
