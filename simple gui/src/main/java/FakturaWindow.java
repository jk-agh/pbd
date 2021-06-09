import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;


public class FakturaWindow extends JPanel implements ActionListener {

    public JFrame frame;

    private JButton fakturaButton;
    private JTextArea textArea;
    public FakturaWindow(){
        frame = new JFrame("Faktura");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);


        String[] columnNames = {"Order ID",
                "Imię",
                "Nazwisko",
                "Nazwa firmy"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        String url = "jdbc:sqlserver://pbd.kosciolek.dev:1433;databaseName=test9";
        String user = "sa";
        String password = "SomePassword0)0)";
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String query = "SELECT [order].id , client_employee.first_name, client_employee.second_name, client_company.name\n" +
                    "FROM [order], order_associated_employee, client_employee, client_company\n" +
                    "WHERE [order].id = order_associated_employee.order_id AND order_associated_employee.employee_id = " +
                    "client_employee.id AND client_employee.company_id = client_company.id";


            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                Object[] objs = {rs.getInt("id"), rs.getString("first_name"), rs.getString("second_name"), rs.getString("name")};
                tableModel.addRow(objs);
            }

        } catch (SQLException e){
            System.out.println("oops, error: ");
            e.printStackTrace();
        }
        JTable table = new JTable(tableModel);

        JScrollPane scroll = new JScrollPane(table);


        JPanel panel1 = new JPanel();
        panel1.setLocation(0,0);
        panel1.setSize(520, 400);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 3));
        panel2.setLocation(0, 450);
        panel2.setSize(500, 100);


        textArea = new JTextArea();
        JLabel label = new JLabel("Wprowadź id zamówienia");

        fakturaButton = new JButton("Stwórz fakturę");
        this.fakturaButton.addActionListener(this);
        panel2.add(label);
        panel2.add(textArea);
        panel2.add(new JPanel());
        panel2.add(fakturaButton);
        panel1.add(scroll);

        frame.add(panel2);
        frame.add(panel1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fakturaButton){
            try{
                String url = "jdbc:sqlserver://pbd.kosciolek.dev:1433;databaseName=test9";
                String user = "sa";
                String password = "SomePassword0)0)";
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();

                String query = "SELECT [order].id, [order].date_placed, effective_price, product_id, product.[description], product.tax_percent, client_company.[name], " +
                        "client_company.nip, \n" +
                        "client_employee.first_name, client_employee.second_name \n" +
                        "FROM [order], order_product, product, client_company, client_employee, order_associated_employee\n" +
                        "WHERE [order].id = order_product.order_id AND [order].id = " + textArea.getText() + " AND order_product.product_id = product.id AND " +
                        "order_associated_employee.order_id = [order].id AND \n" +
                        "order_associated_employee.employee_id = client_employee.id AND client_employee.company_id = client_company.id";

                ResultSet rs = statement.executeQuery(query);


                LinkedList<OrderInfo> itemsInOrder = new LinkedList<>();
                int orderId;
                String datePlaced;
                double grossPrice = 0;
                double netPrice = 0;
                double taxPercent = 0;
                String foodName;
                String companyName;
                String nip;
                String firstName;
                String secondName;
                while(rs.next()){
                    OrderInfo orderInfo = new OrderInfo(rs.getInt("id"), rs.getString("date_placed"), rs.getDouble("effective_price"),
                            rs.getString("description"),rs.getDouble("tax_percent"), rs.getString("name"), rs.getString("nip"),
                            rs.getString("first_name"), rs.getString("second_name"));
                    itemsInOrder.add(orderInfo);

                }
                LinkedList<String> productNames = new LinkedList<>();
                LinkedList<Double> productPrices = new LinkedList<>();

                orderId = itemsInOrder.get(0).getOrderId();
                String date = itemsInOrder.get(0).getDatePlaced();
                String[] parts = date.split(" ");
                datePlaced = parts[0];
                companyName = itemsInOrder.get(0).getCompanyName();
                nip = itemsInOrder.get(0).getNip();
                taxPercent = itemsInOrder.get(0).getTaxPercent();
                firstName = itemsInOrder.get(0).getFirstName();
                secondName = itemsInOrder.get(0).getSecondName();

                for(OrderInfo element : itemsInOrder){
                    grossPrice += element.getGrossPrice();
                    netPrice = netPrice + (element.getGrossPrice() / (1.00 + (taxPercent*0.01)));
                    productNames.add(element.getFoodName());
                    productPrices.add(element.getGrossPrice());

                }

                new PDFMaker(orderId, datePlaced, grossPrice, netPrice, taxPercent, productNames, productPrices, companyName, nip, firstName, secondName);

            } catch (SQLException | FileNotFoundException ex){
                System.out.println("oops, error: ");
                ex.printStackTrace();
            }
        }
    }
}
