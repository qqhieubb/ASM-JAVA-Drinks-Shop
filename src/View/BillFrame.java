/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author thhqb
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BillFrame extends JFrame {

    private JTable productTable;
    private DefaultTableModel productModel;
    private JTable cartTable;
    private DefaultTableModel cartModel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;

    public BillFrame() {
        // Set up the JFrame
        setTitle("Product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Set up the product table
        productModel = new DefaultTableModel();
        productModel.addColumn("Product Name");
        productModel.addColumn("Category");
        productModel.addColumn("Price");
        productTable = new JTable(productModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);

        // Read data from file and add to product table
        try {
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                productModel.addRow(data);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up the cart table
        cartModel = new DefaultTableModel();
        cartModel.addColumn("Product");
        cartModel.addColumn("Quantity");
        cartModel.addColumn("Price");
        cartTable = new JTable(cartModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);

        // Set up the add button
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row from the product table
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the product data from the selected row
                    String productName = (String) productModel.getValueAt(selectedRow, 0);
                    String priceString = (String) productModel.getValueAt(selectedRow, 2);
                    double price = Double.parseDouble(priceString);

                    // Check if the product is already in the cart
                    boolean found = false;
                    for (int i = 0; i < cartModel.getRowCount(); i++) {
                        String name = (String) cartModel.getValueAt(i, 0);
                        if (name.equals(productName)) {
                            // If the product is already in the cart, update the quantity and price
                            int quantity = Integer.parseInt((String) cartModel.getValueAt(i, 1)) + 1;
                            double total = price * quantity;
                            cartModel.setValueAt(String.valueOf(quantity), i, 1);
                            cartModel.setValueAt(String.format("%.2f", total), i, 2);
                            found = true;
                            break;
                        }
                    }

                    // If the product is not in the cart, add it to the cart
                    if (!found) {
                        cartModel.addRow(new String[]{productName, "1", String.format("%.2f", price)});
                    }
                }
            }
        });

        // Set up the delete button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row from the cart table
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow != -1) {
                    cartModel.removeRow(selectedRow);
                }
            }
        });

        // Set up the save button
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate the total price
                double totalPrice = 0;
                for (int i = 0; i < cartModel.getRowCount(); i++) {
                    String priceString = (String) cartModel.getValueAt(i, 2);
                    double price = Double.parseDouble(priceString);
                    totalPrice += price;
                }

                // Write the bill to file
                try {
                    FileWriter writer = new FileWriter("Bill.txt");
                    for (int i = 0; i < cartModel.getRowCount(); i++) {
                        String productName = (String) cartModel.getValueAt(i, 0);
                        String quantityString = (String) cartModel.getValueAt(i, 1);
                        String priceString = (String) cartModel.getValueAt(i, 2);
                        double price = Double.parseDouble(priceString);
                        writer.write(productName + " x " + quantityString + " = " + priceString + "\n");
                    }
                    writer.write("Total: " + String.format("%.2f", totalPrice));
                    writer.close();

                    JOptionPane.showMessageDialog(null, "Bill saved.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Set up the layout
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        panel.add(productScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(cartScrollPane, BorderLayout.EAST);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new BillFrame();
    }
}
