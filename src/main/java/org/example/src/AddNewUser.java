package org.example.src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class AddNewUser extends JFrame {

  private JPanel contentPane;
  private JTextField textField;
  private JTextField textField_1;
  private JTextField textField_2;
  private JTextField textField_3;
  private JPasswordField passwordField;
  private JPasswordField passwordField_1;
  private JComboBox comboBox;

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          AddNewUser frame = new AddNewUser();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public void reset() {
    textField.setText(null);
    textField_1.setText(null);
    textField_2.setText(null);
    textField_3.setText(null);
    passwordField.setText(null);
    passwordField_1.setText(null);
    comboBox.setSelectedIndex(0);
  }

  public String studentId() {
    return "";
  }

  public AddNewUser() {
		setResizable(false);
		setTitle("Add User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 469, 593);
		setLocationRelativeTo(this);
		textField = new JTextField(10);
		textField_1 = new JTextField(10);
		textField_2 = new JTextField(10);
		textField_3 = new JTextField(10);
		passwordField = new JPasswordField();
		passwordField_1 = new JPasswordField();
	}
}
