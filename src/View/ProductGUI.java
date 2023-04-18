/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author thhqb
 */
public class ProductGUI extends JFrame {
    private List<Product> productList = new ArrayList<>();
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ProductGUI() {
        setTitle("Product Manager");

        // Create components
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(10);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(10);
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Create table
        String[] columnNames = {"ID", "Name", "Category", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);

        // Add components to panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add event listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String category = categoryField.getText();
                double price = Double.parseDouble(priceField.getText());
                Product product = new Product(id, name, category, price);
                productList.add(product);
                updateTable();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String category = categoryField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    Product product = productList.get(selectedRow);
                    product.setId(id);
                    product.setName(name);
                    product.setCategory(category);
                    product.setPrice(price);
                    updateTable();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    productList.remove(selectedRow);
                    updateTable();
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
    
    private void updateTable() {
        tableModel.setRowCount(0);
        for (Product product : productList) {
            Object[] rowData = {product.getId(), product.getName(), product.getCategory(), product.getPrice()};
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        new ProductGUI();
    }
}
