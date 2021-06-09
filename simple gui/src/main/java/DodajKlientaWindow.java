import com.itextpdf.layout.border.Border;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Objects;

public class DodajKlientaWindow implements ActionListener {

    public JFrame frame;
    private final JComboBox clientType;
    private JLabel firstNameOrName;
    private JLabel secondNameOrNip;
    private JLabel phoneNumber;

    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;

    private JButton dodajKlientaButton;

    public DodajKlientaWindow(){
        frame = new JFrame("Dodaj Klienta");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(4, 0));


        String[] clientTypes = {"Client Company", "Client Person"};
        clientType = new JComboBox(clientTypes);

        firstNameOrName = new JLabel("Imię:");
        secondNameOrNip = new JLabel("Nazwisko:");
        phoneNumber = new JLabel("Numer telefonu:");

        textArea1 = new JTextArea();
        textArea2 = new JTextArea();
        textArea3 = new JTextArea();
        textArea1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        textArea2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        textArea3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        clientType.setSelectedIndex(1);
        this.clientType.addActionListener(this);

        dodajKlientaButton = new JButton("Dodaj klienta");
        this.dodajKlientaButton.addActionListener(this);


        frame.add(clientType);
        frame.add(dodajKlientaButton);
        frame.add(firstNameOrName);
        frame.add(textArea1);
        frame.add(secondNameOrNip);
        frame.add(textArea2);
        frame.add(phoneNumber);
        frame.add(textArea3);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == clientType){
            JComboBox cb = (JComboBox)e.getSource();
            String msg = (String)cb.getSelectedItem();

            switch (msg){
                case "Client Company":
                    firstNameOrName.setText("Nazwa:");
                    secondNameOrNip.setText("NIP:");
                    textArea1.setText("");
                    textArea2.setText("");
                    textArea3.setText("");
                    break;
                case "Client Person":
                    firstNameOrName.setText("Imię:");
                    secondNameOrNip.setText("Nazwisko:");
                    textArea1.setText("");
                    textArea2.setText("");
                    textArea3.setText("");
                    break;
                default:
                    phoneNumber.setText("xdnic");
                    break;
            }
        }
        else if(e.getSource() == dodajKlientaButton){

            String msg = (String)clientType.getSelectedItem();

            switch (msg){
                case "Client Company":
                    try{
                        String url = "jdbc:sqlserver://pbd.kosciolek.dev:1433;databaseName=test9";
                        String user = "sa";
                        String password = "SomePassword0)0)";
                        Connection connection = DriverManager.getConnection(url, user, password);
                        Statement statement = connection.createStatement();

                        String query = "EXEC dbo.insert_client_company @name =  "+ "'" + textArea1.getText() + "'" + ", @phone_number =" + "'" +
                                textArea3.getText() + "'" + ", @nip =" + "'" + textArea2.getText() + "'" + ";";

                        ResultSet rs = statement.executeQuery(query);


                    } catch (SQLException ex){
                        System.out.println("oops, error: ");
                        ex.printStackTrace();
                    }
                    break;
                case "Client Person":
                    try{
                        String url = "jdbc:sqlserver://pbd.kosciolek.dev:1433;databaseName=test9";
                        String user = "sa";
                        String password = "SomePassword0)0)";
                        Connection connection = DriverManager.getConnection(url, user, password);
                        Statement statement = connection.createStatement();

                        String query = "EXEC dbo.insert_client_person @first_name =  "+ "'" + textArea1.getText() + "'" + ", @second_name =" + "'" +
                                textArea2.getText() + "'" + ", @phone_number =" + "'" + textArea3.getText() + "'" + ";";

                        ResultSet rs = statement.executeQuery(query);


                    } catch (SQLException ex){
                        System.out.println("oops, error: ");
                        ex.printStackTrace();
                    }
                    break;
                default:
                    phoneNumber.setText("");
                    break;
            }

        }
    }
}
