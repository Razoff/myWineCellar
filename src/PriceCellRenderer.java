import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;

public class PriceCellRenderer extends DefaultTableCellRenderer {
    private static final DecimalFormat formatter = new DecimalFormat( "0.00" );

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // Format the cell value as required
        value = formatter.format((Number)value);
        super.setHorizontalAlignment(JLabel.LEFT);

        // Pass to parent class
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column );
    }
}
