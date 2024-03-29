import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;


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
        // Create basic frame
        final JFrame frame = new JFrame("My cellar GUI");
        frame.setSize(1200, 1200);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // See listener later for closing actions

        // Create table
        JTable table = new JTable();

        // Set up model AKA put data into table
        DefaultTableModel model = new DefaultTableModel(cellar_bottles, headers){
            // Get type of each column for sorting via header
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };
        table.setModel(model);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model); // Create sorter from table model
        table.setRowSorter(sorter);

        // Special renderer to see colors in column 1
        table.getColumnModel().getColumn(1).setCellRenderer(new ColorCellRenderer());

        // Setup default spacing
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(30);
        table.getColumnModel().getColumn(5).setPreferredWidth(20);
        table.getColumnModel().getColumn(6).setPreferredWidth(20);
        table.getColumnModel().getColumn(7).setPreferredWidth(400);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        // Add scrolling
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Special renderer for header
        table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());

        // Set bigger font and box size
        table.setFont(new Font("Verdana",Font.PLAIN,20));
        table.setRowHeight(25);

        // Set price top the left + 2 decimals
        table.getColumnModel().getColumn(4).setCellRenderer(new PriceCellRenderer());

        // Set quantity to left and move it
        DefaultTableCellRenderer custom_quantity = new DefaultTableCellRenderer();
        custom_quantity.setHorizontalAlignment(JLabel.LEFT);
        table.getColumnModel().getColumn(6).setCellRenderer(custom_quantity);
        table.moveColumn(6,4);

        // Panel for the buttons
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));

        // Buttons creation
        JButton addButton = new JButton("Add"); // Add bottle to Jtable
        JButton rmvButton = new JButton("Remove"); // Remove bottle to jtable
        JButton savButton = new JButton("Save"); // Backup cellar into mycellar file
        JButton relButton = new JButton("Reload"); // Load current content of cellar into Jtable
        JButton stsButton = new JButton("Stats"); // Display stats pannel
        JButton quiButton = new JButton("Quit"); // Quit the GUI

        // Add buttons to panel
        btns.add(addButton);
        btns.add(rmvButton);
        btns.add(savButton);
        btns.add(relButton);
        btns.add(stsButton);
        btns.add(quiButton);

        // Add elements to panel
        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(btns, BorderLayout.SOUTH);

        // What we do when we exit
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

        // Handle CTRL S, D and DEL key
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

        // Keep last selected row
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                lastSelectedRow = table.getSelectedRow();
                System.out.println(lastSelectedRow);
            }
        });

        // When someone edit a celle show user that it need to be savec
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

        // Sort behaviour
        table.getRowSorter().addRowSorterListener(new RowSorterListener() {
            @Override
            public void sorterChanged(RowSorterEvent rowSorterEvent) {
                System.out.println("Sorting");
                //update_bottles(table);
            }
        });

        // Add button behaviour
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Add");
                add_row(table, new AddBottleForm().display());
                savButton.setText("Save*");
            }
        });

        // Remove button behaviour
        rmvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Delete");
                delete_row(table);
                savButton.setText("Save*");
            }
        });

        // Save button behaviour
        savButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Save");
                update_bottles(table);
                savButton.setText("Save");
                backup();
            }
        });

        // Reload button behaviour
        relButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Reload");
                soft_quit(frame);
                savButton.setText("Save");
            }
        });

        // Stats button behaviour
        stsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Stats");
                StatsDisplayer stats = new StatsDisplayer(cellar_bottles);
                stats.Display();
            }
        });

        // Quit button behaviour
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

        // Set everything visible
        frame.setVisible(true);

    }

    // Method that deletes the last selected row
    private void delete_row(JTable table){
        if (lastSelectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(table.convertRowIndexToModel(lastSelectedRow));
        }else{
            System.out.println("No rows selected");
        }

    }

    // Add bottle to table
    private void add_row(JTable table, Bottle bottle){
        if(bottle != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(bottle.arrayification());
            table.setModel(model);
        }
    }

    private void soft_quit(JFrame frame){
        frame.setVisible(false);
        frame.dispose();
        display();
    }

    // Update this.cellar_bottles with the current content of the jtable (last step before writing to cellar file)
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

    // Write back to file
    private Boolean backup(){
        return new Cellar(cellar_bottles).backup();
    }

}
