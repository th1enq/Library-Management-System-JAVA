package org.example.src;

import com.sun.tools.javac.Main;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class EditProfile extends JFrame {

  JTextField username;
  JTextField password;
  JTextField new_username;
  JTextField new_password;
  JTextField new_email;
  JLabel lblNewLabel_3_2_1;
  JPanel contentPane;

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          EditProfile frame = new EditProfile();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private void reset() {
    username.setText(null);
    password.setText(null);
    new_username.setText(null);
    new_password.setText(null);
    new_email.setText(null);
  }

  public EditProfile() {
    setResizable(false);
    setTitle("Add New Book");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    setBounds(0, 0, 1000, 600);
    JLabel MainGUI = new JLabel();
    MainGUI.setBounds(0,0,1000,600);
    MainGUI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mainGUI.png")));
    username = new JTextField();
    username.setBounds(420, 100, 240, 30);
    username.setBorder(null);
    JLabel l0 = new JLabel("Username:");
    l0.setBounds(250, 100, 120, 20);
    JLabel l00 = new JLabel();
    l00.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bar.png")));
    l00.setBounds(400,90,320,46);

    password = new JPasswordField();
    password.setBounds(420, 170,240 , 30);
    password.setBorder(null);
    JLabel l11 = new JLabel();
    l11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bar.png")));
    l11.setBounds(400,160,320,46);
    JLabel l1 = new JLabel("Password:");
    l1.setBounds(250, 170, 120, 20);

    new_username = new JTextField();
    new_username.setBounds(420, 240, 240, 30);
    new_username.setBorder(null);
    JLabel l22 = new JLabel();
    l22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bar.png")));
    l22.setBounds(400,230,320,46);
    JLabel l2 = new JLabel("New Username:");
    l2.setBounds(250, 240, 120, 20);

    new_password = new JPasswordField();
    new_password.setBounds(420, 310, 240, 30);
    new_password.setBorder(null);
    JLabel l3 = new JLabel("New Password:");
    l3.setBounds(250, 310, 120, 20);
    JLabel l33 = new JLabel();
    l33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bar.png")));
    l33.setBounds(400,300,320,46);

    new_email = new JTextField();
    new_email.setBounds(420, 370, 240, 30);
    new_email.setBorder(null);
    JLabel l4 = new JLabel("New Email:");
    l4.setBounds(250, 370, 120, 20);
    JLabel l44 = new JLabel();
    l44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bar.png")));
    l44.setBounds(400,360,320,46);
    setLocationRelativeTo(this);

    JButton updButton = new JButton("Update");
    updButton.setBounds(500, 500, 100, 30);
    contentPane = new JPanel();
    JLabel error = new JLabel("");
    error.setBounds(350, 460, 100, 30);
    contentPane.setBounds(0,0,1200,600);

    contentPane.add(username);
    contentPane.add(password);
    contentPane.add(new_username);
    contentPane.add(new_password);
    contentPane.add(new_email);

    contentPane.add(l00);
    contentPane.add(l11);
    contentPane.add(l22);
    contentPane.add(l33);
    contentPane.add(l44);

    contentPane.add(l0);
    contentPane.add(l1);
    contentPane.add(l2);
    contentPane.add(l3);
    contentPane.add(l4);

    contentPane.add(updButton);
    contentPane.add(MainGUI);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    getContentPane().add(contentPane);
    updButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!DBInfo.checkPass(username.getText(), password.getText())
            && username.getText() != null && password.getText() != null) {
          error.setText("SAI");
        } else {
          String curPass = password.getText();
          String curUser= username.getText();
          String nPass = curPass;
          String nUser = curPass;
          if (!new_password.getText().trim().isEmpty()) {
            nPass = new_password.getText();
          }
          if (!new_username.getText().trim().isEmpty()) {
            nUser = new_username.getText();
          }
          int id = DBInfo.findUserId(username.getText(), password.getText());
          if (!new_email.getText().trim().isEmpty()) {
            DBInfo.updateEmail(id, new_email.getText());
          }
          DBInfo.updateUser(id, nUser, nPass);
          reset();
        }
      }
    });
  }
}
