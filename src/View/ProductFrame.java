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
import java.io.*;

public class ProductFrame extends JFrame implements ActionListener {

    private JTable table;
    private DefaultTableModel model;
    private JTextField productNameField;
    private JTextField categoryField;
    private JTextField priceField;

    public ProductFrame() {
        super("Product");

        // Set the size of the frame
        setSize(1000, 500);

        // Create a table with three columns: Product Name, Category, and Price
        String[] columns = {"Product Name", "Category", "Price"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.CENTER);

        // Add the product name, category, and price fields to the frame
        JPanel inputPanel = new JPanel(new GridLayout(1, 6));
        inputPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);
        add(inputPanel, BorderLayout.NORTH);

        // Add the Add, Delete, and Update buttons to the frame
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        buttonPanel.add(updateButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load the product data from the file
        loadProductData();

        // Show the frame
        setVisible(true);
    }

    private void loadProductData() {
        try {
            // Open the file for reading
            FileReader fileReader = new FileReader("products.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read each line of the file and add the data to the table
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }

            // Close the file
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProductData() {
        try {
            // Openthe file for writing
            FileWriter fileWriter = new FileWriter("products.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write each row of the table to the file
            for (int i = 0; i < model.getRowCount(); i++) {
                String line = "";
                for (int j = 0; j < model.getColumnCount(); j++) {
                    line += model.getValueAt(i, j).toString();
                    if (j < model.getColumnCount() - 1) {
                        line += ",";
                    }
                }
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }

            // Close the file
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add")) {
            try {
                // Check if any input fields are empty
                if (productNameField.getText().isEmpty() || categoryField.getText().isEmpty() || priceField.getText().isEmpty()) {
                    throw new Exception("Vui lòng nhập dữ liệu vào tất cả các ô");
                }
                // Check if product name or category contains digits
                if (productNameField.getText().matches(".*\\d.*") || categoryField.getText().matches(".*\\d.*")) {
                    throw new Exception("Tên và loại sản phẩm không chứa chữ số");
                }
                // Check if price field contains non-numeric characters
                if (!priceField.getText().matches("\\d+(\\.\\d+)?")) {
                    throw new Exception("Giá tiền không chứa chữ cái");
                }

                // Add a new row to the table with the data from the input fields
                String[] data = {productNameField.getText(), categoryField.getText(), priceField.getText()};
                model.addRow(data);

                // Clear the input fields
                productNameField.setText("");
                categoryField.setText("");
                priceField.setText("");

                // Save the updated data to the file
                saveProductData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals("Delete")) {
            try {
                // Check if a row is selected
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    throw new Exception("Vui lòng chọn sản phẩm để xóa.");
                }

                // Remove the selected row from the table
                model.removeRow(selectedRow);

                // Save the updated data to the file
                saveProductData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals("Update")) {
            try {
                // Check if a row is selected
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    throw new Exception("Vui lòng chọn sản phẩm để cập nhật.");
                }
                // Check if product name or category contains digits
                if (productNameField.getText().matches(".*\\d.*") || categoryField.getText().matches(".*\\d.*")) {
                    throw new Exception("Tên và loại sản phẩm không được chứa kĩ tự chữ cái.");
                }
                // Check if price field contains non-numeric characters
                if (!priceField.getText().matches("\\d+(\\.\\d+)?")) {
                    throw new Exception("Giá tiền không chữa chữ cái");
                }

                // Update the selected row with the data from the input fields
                model.setValueAt(productNameField.getText(), selectedRow, 0);
                model.setValueAt(categoryField.getText(), selectedRow, 1);
                model.setValueAt(priceField.getText(), selectedRow, 2);

                // Save the updated data to the file
                saveProductData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new ProductFrame();
    }
}
