package org.example.src;


import java.util.ArrayList;
import java.awt.EventQueue;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
				String to = "hientuongtran@gmail.com";
				String from = "23020152@gmail.com"; // replace with your email
				String host = "smtp.gmail.com"; // or another SMTP server

				// Setup mail server properties
				Properties properties = System.getProperties();
				properties.put("mail.smtp.host", host);
				properties.put("mail.smtp.port", "465");
				properties.put("mail.smtp.ssl.enable", "true");
				properties.put("mail.smtp.auth", "true");

				// Get the Session object and authenticate with your email
				Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("your-email@gmail.com", "your-email-password");
					}
				});

				try {
					// Create a default MimeMessage object
					MimeMessage message = new MimeMessage(session);

					// Set From: header field
					message.setFrom(new InternetAddress(from));

					// Set To: header field
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

					// Set Subject: header field
					message.setSubject("Password Recovery");

					// Set the message content
					message.setText("Hello " +  ",\n\nYour password is: 12345"  + "\n\nBest regards,\nLibrary Management System");

					// Send message
					Transport.send(message);
					System.out.println("Sent message successfully....");
				} catch (MessagingException mex) {
					mex.printStackTrace();
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
				User currentUser = new User(username, password, "normal" , ".@gmail.com");
				String userType ="";
				/**
				 * đoạn này là khi ấn vào nút login thì kiểm tra xem nó là loại tài khoản nào.
				 */
				boolean loginAcess = false;
				ArrayList<User> users = new ArrayList<>();
				users.add(new User("admin", "admin123" , "admin","1@gmail.com"));
				users.add(new User("student1", "pass123", "student","2@gmail.com"));
				users.add(new User("student2", "password", "student","3@gmail.com"));
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
		 * nút quên mật khẩu.
		 */
		JButton forgotPasswordButton = new JButton("Forgot Password?");
		forgotPasswordButton.setFont(new Font("Verdana", Font.PLAIN, 13));
		forgotPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = JOptionPane.showInputDialog("Enter your email address:");
				if (email != null && !email.isEmpty()) {
					// Here we will handle sending the recovery email
					ArrayList<User> users = new ArrayList<>();
					users.add(new User("admin", "admin123", "admin", "hientuongtran@gmail.com"));
					users.add(new User("student1", "pass123", "student", "2@gmail.com"));
					users.add(new User("student2", "password", "student", "3@gmail.com"));
					boolean emailExists = false;
					for (User user : users) {
						if (Objects.equals(user.getEmail(), email)) {
							emailExists = true;
							sendRecoveryEmail(user); // Function to send the email
							JOptionPane.showMessageDialog(getParent(), "Recovery email sent! Check your inbox.", "Success", JOptionPane.INFORMATION_MESSAGE);
							break;
						}
					}
					if (!emailExists) {
						JOptionPane.showMessageDialog(getParent(), "Email not found", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			public void sendRecoveryEmail(User user) {
				String to = user.getEmail();
				String from = "23020152@vnu.edu.vn"; // replace with your email
				String host = "smtp.gmail.com"; // or another SMTP server

				// Setup mail server properties
				Properties properties = System.getProperties();
				properties.put("mail.smtp.host", host);
				properties.put("mail.smtp.port", "465");
				properties.put("mail.smtp.ssl.enable", "true");
				properties.put("mail.smtp.auth", "true");

				// Get the Session object and authenticate with your email
				Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("your-email@gmail.com", "your-email-password");
					}
				});

				try {
					// Create a default MimeMessage object
					MimeMessage message = new MimeMessage(session);

					// Set From: header field
					message.setFrom(new InternetAddress(from));

					// Set To: header field
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

					// Set Subject: header field
					message.setSubject("Password Recovery");

					// Set the message content
					message.setText("Hello " + user.getUsername() + ",\n\nYour password is: " + user.getPassword() + "\n\nBest regards,\nLibrary Management System");

					// Send message
					Transport.send(message);
					System.out.println("Sent message successfully....");
				} catch (MessagingException mex) {
					mex.printStackTrace();
				}
			}

		});







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