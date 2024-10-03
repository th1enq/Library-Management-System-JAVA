package org.example.src;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.jar.JarEntry;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javax.swing.SwingConstants;

public class AddNewBook extends JFrame {

  private JTextField textField;
  private JTextField textField_1;
  private JTextField textField_2;
  private JTextField textField_3;
  private JTextField textField_4;
  private JTextField textField_5;
  private JPanel contentPane;
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          AddNewBook frame = new AddNewBook();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public String codeNum() {
    return "";
  }


  public static void generateQRCode(String data, String path, String charset, int h, int w)
      throws WriterException, IOException {

  }


  public void addNames(String name, String str) {

  }


  public void reset() {

  }

  public AddNewBook() {

    setResizable(false);
    setTitle("Add New Book");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 801, 511);
    textField = new JTextField();
    textField.setBounds(150,50,600,30);
    JLabel l0 = new JLabel("ID:");
    l0.setBounds(20,50,120,20);
    textField_1 = new JTextField();
    textField_1.setBounds(150,100,600,30);
    JLabel l1 = new JLabel("Tile:");
    l1.setBounds(20,100,120,20);
    textField_2 = new JTextField();
    textField_2.setBounds(150,150,600,30);
    JLabel l2 = new JLabel("Author:");
    l2.setBounds(20,150,120,20);
    textField_3 = new JTextField();
    textField_3.setBounds(150,200,600,30);
    JLabel l3 = new JLabel("Subject:");
    l3.setBounds(20,200,120,20);
    textField_4 = new JTextField();
    textField_4.setBounds(150,250,600,30);
    JLabel l4 = new JLabel("Pushlisher:");
    l4.setBounds(20,250,120,20);
    textField_5 = new JTextField();
    textField_5.setBounds(150,300,600,30);
    JLabel l5 = new JLabel("Category:");
    l5.setBounds(20,300,120,20);
    setLocationRelativeTo(this);
    JButton addButton = new JButton("Thêm sách");
    addButton.setBounds(350, 400, 100, 30);
    contentPane = new JPanel();
    setContentPane(contentPane);
    contentPane.add(textField);
    contentPane.add(l0);
    contentPane.add(l1);
    contentPane.add(l2);
    contentPane.add(l3);
    contentPane.add(l4);
    contentPane.add(l5);

    contentPane.add(textField_1);
    contentPane.add(textField_2);
    contentPane.add(textField_3);
    contentPane.add(textField_4);
    contentPane.add(textField_5);
    contentPane.add(addButton);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        DBInfo.addBook(textField.getText(), textField_1.getText(), textField_2.getText(),
            textField_3.getText(), textField_4.getText(), textField_5.getText());
        reset();
      }
    });
  }
}
