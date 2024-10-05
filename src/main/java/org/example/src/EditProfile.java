package org.example.src;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

  /**
   * đang lỗi.
   */
  public EditProfile() {
    setResizable(false);
    setTitle("Add New Book");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 801, 511);
    username = new JTextField();
    username.setBounds(150, 50, 600, 30);
    JLabel l0 = new JLabel("Username:");
    l0.setBounds(20, 50, 120, 20);
    password = new JTextField();
    password.setBounds(150, 100, 600, 30);
    JLabel l1 = new JLabel("Password:");
    l1.setBounds(20, 100, 120, 20);
    new_username = new JTextField();
    new_username.setBounds(150, 150, 600, 30);
    JLabel l2 = new JLabel("New Username:");
    l2.setBounds(20, 150, 120, 20);
    new_password = new JTextField();
    new_password.setBounds(150, 200, 600, 30);
    JLabel l3 = new JLabel("New Password:");
    l3.setBounds(20, 200, 120, 20);
    new_email = new JTextField();
    new_email.setBounds(150, 250, 600, 30);
    JLabel l4 = new JLabel("New Email:");
    l4.setBounds(20, 250, 120, 20);
    setLocationRelativeTo(this);
    JButton updButton = new JButton("Update");
    updButton.setBounds(350, 400, 100, 30);
    contentPane = new JPanel();
    JLabel error = new JLabel("");
    error.setBounds(350, 460, 100, 30);
    setContentPane(contentPane);

    contentPane.add(l0);
    contentPane.add(l1);
    contentPane.add(l2);
    contentPane.add(l3);
    contentPane.add(l4);

    contentPane.add(username);
    contentPane.add(password);
    contentPane.add(new_username);
    contentPane.add(new_password);
    contentPane.add(new_email);
    contentPane.add(updButton);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    updButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (DBInfo.checkPass(username.getText(), password.getText()) == false
            && username.getText() != null && password.getText() != null) {
          error.setText("SAI");
        } else {

          String nPass = password.getText();
          String nUser = username.getText();
          if (new_password.getText() != null) {
            nPass = new_password.getText();
          }
          if (new_username.getText() != null) {
            nUser = new_username.getText();
          }
          int id = DBInfo.findUserId(username.getText(), password.getText());
          if (new_email.getText() != null) {
            DBInfo.updateEmail(id, new_email.getText());
          }
          DBInfo.updateUser(id, nUser, nPass);
          // reset();
        }
      }
    });
  }
}
