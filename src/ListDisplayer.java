import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class ListDisplayer {
    private String[] headers;
    private Object[][] cellar_bottles;

    public ListDisplayer(Cellar cellar) {
        this.headers = Bottle.getHeaders();
        this.cellar_bottles = cellar.arrayification();
    }

    public void display() {

        final JFrame frame = new JFrame("My cellar GUI");
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JTable table = new JTable(cellar_bottles, headers);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());

        frame.getContentPane().add(scrollPane);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                update_bottles(table);
                if (backup()) {
                    System.exit(0);
                }else{
                    System.exit(1);
                }
            }
        });

        table.addKeyListener(new java.awt.event.KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_S) {
                    System.out.println("CTRL + S");
                    update_bottles(table);
                    backup();
                }
            }
        });

    }

    private void update_bottles(JTable table){
        TableModel dtm = table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                tableData[i][j] = dtm.getValueAt(i, j);
            }
        }
        this.cellar_bottles = tableData;
    }

    private Boolean backup(){
        return new Cellar(cellar_bottles).backup();
    }

}
