package org.example.src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.instrument.UnmodifiableClassException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DeleteUser extends JFrame {

  JTextField Uname;
  JPanel contentPane;

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          DeleteUser frame = new DeleteUser();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
private void reset(){
    Uname.setText(null);
}
  public DeleteUser() {
    setResizable(false);
    setTitle("DeleteUser");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(this);
    setBounds(0, 0, 1000, 600);
    JLabel MainGUI = new JLabel();
    MainGUI.setBounds(0, 0, 1000, 600);
    MainGUI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mainGUI.png")));
    contentPane = new JPanel();

    Uname = new JTextField();
    Uname.setBounds(420, 100, 240, 30);
    Uname.setBorder(null);
    JLabel l0 = new JLabel("Username :");
    l0.setBounds(250, 100, 120, 20);
    JLabel l00 = new JLabel();
    l00.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bar.png")));
    l00.setBounds(400, 90, 320, 46);

    contentPane.add(l00);
    contentPane.add(Uname);
    contentPane.add(l0);

    JButton del = new JButton("XÓA TÀI KHOẢN");
    del.setBounds(450, 400, 200, 50);
    del.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (Uname.getText().trim().isEmpty() == true) {
          return;
        } else {
          DBInfo.DeleteUser(Uname.getText().trim());
          reset();
        }
      }
    });
    JLabel HDSD = new JLabel("Nhập username của tài khoán cần bị xóa");
    HDSD.setBounds(400, 250, 330, 30);
    HDSD.setBorder(null);
    Font font = new Font("Arial", Font.PLAIN, 18);  // Font, kiểu, cỡ chữ
    HDSD.setFont(font);
    HDSD.setForeground(Color.RED);

    contentPane.add(HDSD);
    contentPane.add(del);

    contentPane.add(MainGUI);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));

    getContentPane().add(contentPane);

  }
}
