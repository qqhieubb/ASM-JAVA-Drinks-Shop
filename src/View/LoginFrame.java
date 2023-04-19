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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class LoginFrame extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        super("Đăng nhập");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameLabel.setBounds(100, 50, 120, 30);
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(230, 50, 150, 30);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setBounds(100, 100, 120, 30);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(230, 100, 150, 30);
        panel.add(passwordField);

        loginButton = new JButton("Đăng nhập");
        loginButton.addActionListener(this);
        loginButton.setBounds(180, 160, 120, 30);
        panel.add(loginButton);
        
        JButton registerButton = new JButton("Đăng ký");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện khi người dùng nhấn nút "Đăng ký"
                JOptionPane.showMessageDialog(LoginFrame.this, "Chức năng đăng ký sẽ được cập nhật sau.");
            }
        });
        registerButton.setBounds(320, 160, 80, 30);
        panel.add(registerButton);
        
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (username.trim().isEmpty()) {
                throw new Exception("Vui lòng nhập tên đăng nhập.");
            } else if (password.trim().isEmpty()) {
                throw new Exception("Vui lòng nhập mật khẩu.");
            } else if (checkLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    }

    private boolean checkLogin(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 5 && fields[1].equals(username) && fields[2].equals(password) && fields[4].equals("Active")) {
                    reader.close();
                    ProductFrame productframe = new ProductFrame();
                    productframe.setVisible(true);
                    return true;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}