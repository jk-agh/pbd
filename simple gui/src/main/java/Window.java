import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {

    public JFrame frame;

    private final JButton fakturaButton;
    private final JButton clientButton;

    public Window(){
        frame = new JFrame("Main Window");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(2,2));

        fakturaButton = new JButton("Stwórz fakturę");
        clientButton = new JButton("Dodaj klienta");

        this.fakturaButton.addActionListener(this);
        this.clientButton.addActionListener(this);

        frame.add(fakturaButton);
        frame.add(clientButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fakturaButton){
            new FakturaWindow();
        }
        else if(e.getSource() == clientButton){
            new DodajKlientaWindow();
        }


    }
}
