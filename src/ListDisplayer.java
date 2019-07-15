import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class ListDisplayer {
    private Cellar cellar;

    public ListDisplayer(Cellar cellar){
        this.cellar = cellar;
        //this.cave = cellar.bottles();
    }

    public void display(){

        final JFrame frame = new JFrame("My cellar GUI");
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTable table = new JTable(cellar.arrayification(), Bottle.getHeaders());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.getContentPane().add(scrollPane);

    }

}
