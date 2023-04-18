/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thhqb
 */
public class ProductController {

    public void catchInputName(String name){
        try {
                    if (name.isEmpty()) {
                        throw new Exception("Please input name");
                    } else if (name.matches(".*\\d.*")) {
                        throw new Exception("Tên không được chứa kí tự số");
                    }
                } catch (Exception ex) {
                    System.out.println("Lỗi: " + ex.getMessage());
                }
    }
    
    
}
