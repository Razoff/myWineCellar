import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class ListDisplayer {
    private String[] headers;
    private Object[][] cellar_bottles;
    private int lastSelectedRow;

    public ListDisplayer(Cellar cellar) {
        this.headers = Bottle.getHeaders();
        this.cellar_bottles = cellar.arrayification();
        this.lastSelectedRow = -1;
    }

    public void display() {

        final JFrame frame = new JFrame("My cellar GUI");
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //JTable table = new JTable(cellar_bottles, headers);
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(cellar_bottles, headers);
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));

        JButton addButton = new JButton("Add"); // Add bottle to Jtable
        JButton rmvButton = new JButton("Remove"); // Remove bottle to jtable
        JButton savButton = new JButton("Save"); // Backup cellar into mycellar file
        JButton relButton = new JButton("Reload"); // Load current content of cellar into Jtable
        JButton quiButton = new JButton("Quit"); // Quit the GUI

        btns.add(addButton);
        btns.add(rmvButton);
        btns.add(savButton);
        btns.add(relButton);
        btns.add(quiButton);

        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(btns, BorderLayout.SOUTH);

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
                    savButton.setText("Save");
                }else if (evt.getKeyCode() == KeyEvent.VK_DELETE){
                    delete_row(table);
                    System.out.println("Delete key");
                }else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_D){
                    add_row(table, new AddBottleForm().display());
                    System.out.println("CTRL + D");
                    savButton.setText("Save*");
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                lastSelectedRow = table.getSelectedRow();
                System.out.println(lastSelectedRow);
            }
        });

        table.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent focusEvent) {
                TableCellEditor tce = table.getCellEditor();
                if (tce != null){
                    System.out.println("Edited");
                    savButton.setText("Save*");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Add");
                add_row(table, new AddBottleForm().display());
                savButton.setText("Save*");
            }
        });

        rmvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Delete");
                delete_row(table);
                savButton.setText("Save*");
            }
        });

        savButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Save");
                update_bottles(table);
                savButton.setText("Save");
                backup();
            }
        });

        relButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Reload");
                DefaultTableModel model = new DefaultTableModel(cellar_bottles, headers);
                table.setModel(model);
                savButton.setText("Save");
            }
        });

        quiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Quit");
                update_bottles(table);
                if (backup()) {
                    System.exit(0);
                }else{
                    System.exit(1);
                }
            }
        });

    }

    private void delete_row(JTable table){
        if (lastSelectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(lastSelectedRow);
        }else{
            System.out.println("No rows selected");
        }

    }

    private void add_row(JTable table, Bottle bottle){
        if(bottle != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(bottle.arrayification());
            table.setModel(model);
        }
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
