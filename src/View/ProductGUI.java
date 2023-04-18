/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Product;
import Controller.ProductController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProductGUI extends JFrame {

    public List<Product> productList = new ArrayList<>();
    public JTable productTable;
    public DefaultTableModel tableModel;

    public ProductGUI() {
        setTitle("Product Manager");
        setBounds(100, 100, 1200, 700);

        // Create components
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
        String[] columnNames = {"Name", "Category", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);

        // Add components to panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

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

                try {
                    String name = nameField.getText().trim();
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Please input name of product</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                    } else if (name.matches(".*\\d.*")) {
                        JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">The product name cannot contain numeric characters</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                    }

                    String category = categoryField.getText().trim();
                    if (category.isEmpty()) {

                        JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Please input Category name</b></html>", "Message", JOptionPane.ERROR_MESSAGE);

                    } else if (category.matches(".*\\d.*")) {

                        JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Category must not contain numeric characters</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                    }

                    String priceText = priceField.getText().trim();
                    if (priceText.isEmpty()) {

                        JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Please enter product price</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                    }
                    double price = Double.parseDouble(priceText);

                    Product product = new Product(name, category, price);
                    productList.add(product);
                    updateTable();
                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Error: Product price must be number</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                    // Hiển thị thông báo lỗi cho người dùng ở đây nếu cần
                } catch (Exception ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                    // Hiển thị thông báo lỗi cho người dùng ở đây nếu cần
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow != -1) {

                        String name = nameField.getText().trim();
                        if (name.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Please input name of product</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                        } else if (name.matches(".*\\d.*")) {
                            JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">The product name cannot contain numeric characters</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                        }

                        String category = categoryField.getText().trim();
                        if (category.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Please input Category name</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                        } else if (category.matches(".*\\d.*")) {
                            JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Category must not contain numeric characters</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                        }

                        String priceString = priceField.getText().trim();
                        if (priceString.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Please enter product price</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                        }
                        double price = Double.parseDouble(priceString);

                        Product product = productList.get(selectedRow);

                        product.setName(name);
                        product.setCategory(category);
                        product.setPrice(price);
                        updateTable();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow == -1) {
                      
                        JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">Please select the product to delete</b></html>", "Message", JOptionPane.ERROR_MESSAGE);
                    }
                    productList.remove(selectedRow);
                    updateTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Product product : productList) {
            Object[] rowData = {product.getName(), product.getCategory(), product.getPrice()};
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        new ProductGUI();

    }
}
