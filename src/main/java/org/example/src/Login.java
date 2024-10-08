package org.example.src;
import java.util.ArrayList;
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

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class Login extends JFrame {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;

	public Login() {
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 431, 344);
		setLocationRelativeTo(this);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Verdana", Font.PLAIN, 13));

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Verdana", Font.PLAIN, 13));

		usernameField = new JTextField();
		usernameField.setFont(new Font("Verdana", Font.PLAIN, 13));
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Verdana", Font.PLAIN, 13));

		JButton loginButton = new JButton("Login");

		//Image img1 = new ImageIcon(this.getClass().getResource("/img/login.png")).getImage().getScaledInstance(13, 17, Image.SCALE_DEFAULT);
		//loginButton.setIcon(new ImageIcon(img1));
		loginButton.setFont(new Font("Verdana", Font.PLAIN, 13));
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password =String.copyValueOf(passwordField.getPassword());
				User currentUser = new User(username, password, "normal");
				String userType ="";
				/**
				 * đoạn này là khi ấn vào nút login thì kiểm tra xem nó là loại tài khoản nào.
				 */
				boolean loginAcess = false;
				ArrayList<User> users = new ArrayList<>();
				users.add(new User("admin", "admin123" , "admin"));
				users.add(new User("student1", "pass123", "student"));
				users.add(new User("student2", "password", "student"));
				for (User user : users) {
					if (user.equals(currentUser)) {
						loginAcess = true;
						break;
					}
				}

				if (!loginAcess) {
					JOptionPane.showMessageDialog(getParent(), "Incorrect username or password","Sign In Failed",JOptionPane.ERROR_MESSAGE);
				}

				if (loginAcess) {
					if(currentUser.getType().equals("student")) {
						StudentPage sp=new StudentPage();
						sp.setVisible(true);
						dispose();
					}

					if(currentUser.getType().equals("admin")) {
						AdminPage ap=new AdminPage();
						ap.setVisible(true);
						dispose();
					}
				}
			}
		});

		/**
		 * nut dang ky.
		 */
		JButton SignUpButton = new JButton("Sign Up");
		SignUpButton.setFont(new Font("Verdana", Font.PLAIN, 13));
		SignUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Registration regis=new Registration();
				regis.setVisible(true);
				setVisible(false);
			}
		});

		/**
		 * Tiêu đề
		 */
		JLabel lblNewLabel = new JLabel("Library Management User Login");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 16));

		/**
		 * Tạo giao diện.
		 */
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
														.addGroup(gl_contentPane.createSequentialGroup()
																.addGap(47)
																.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																		.addComponent(usernameLabel)
																		.addComponent(passwordLabel))
																.addGap(31))
														.addGroup(gl_contentPane.createSequentialGroup()
																.addGap(65)
																.addComponent(loginButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addPreferredGap(ComponentPlacement.RELATED)))
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addGroup(Alignment.TRAILING, gl_contentPane.createParallelGroup(Alignment.LEADING, false)
																.addComponent(passwordField, Alignment.TRAILING)
																.addComponent(usernameField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
														.addComponent(SignUpButton, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addGap(64)
												.addComponent(lblNewLabel))
										.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(45))
		));

		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addGap(18)
								.addComponent(lblNewLabel)
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(usernameLabel)
										.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(37)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(loginButton)
										.addComponent(SignUpButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(31, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}