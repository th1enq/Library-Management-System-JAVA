package org.example.src;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

public class ViewBooks extends JFrame {
	private int x_Mouse, y_Mouse;
	private int currentModule = 0;
	public static String subModule = "";
	private static String currentUser = "";
	public static JPanel modulePanel = new JPanel();

	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ViewBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(() -> new ViewBooks().setVisible(true));
	}

	public ViewBooks() {
		initComponents();
		setSize(1113, 763);
		setLocationRelativeTo(null);
		setBackground(new Color(0, 0, 0, 0));

		// Add mouse listeners to enable window dragging
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				x_Mouse = e.getX();
				y_Mouse = e.getY();
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - x_Mouse, y - y_Mouse);
			}
		});
	}

	private JLabel panels;
	private JLabel mainGUI;
	private JLabel exitButton;
	private JLabel searchBar;
	private JTextField searchTextField;
	private JLabel excelInput;
	private JLabel excelOutput;
	private JLabel addBook;
	boolean tooglesearch = false;

	private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {
		if (tooglesearch != true){
			tooglesearch = true;
			searchTextField.setText("");
		}
	}

	private void addBookMouseClicked(java.awt.event.MouseEvent evt) {
		System.out.println("Add new books");
	}

	private void excelInputMouseClicked(java.awt.event.MouseEvent evt) {
		System.out.println("Nhập Excel");
	}

	private void excelOnputMouseClicked(java.awt.event.MouseEvent evt) {
		System.out.println("Xuất Excel");
	}

	private ArrayList<Book> searchBooks(String query) {
		ArrayList<Book> books = new ArrayList<>();
		try {
			String API_KEY = "AIzaSyCI2U6tHVrTcuYbsFilyfbUy4hwkYftIYw";
			String formattedQuery = query.replace(" ", "+");
			String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + formattedQuery + "&key=" + API_KEY;

			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				InputStreamReader reader = new InputStreamReader(connection.getInputStream());
				JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
				JsonArray items = jsonResponse.getAsJsonArray("items");

				if (items != null) {
					for (int i = 0; i < items.size(); i++) {
						JsonObject volumeInfo = items.get(i).getAsJsonObject().getAsJsonObject("volumeInfo");
						String title = volumeInfo.get("title").getAsString();

						// Process authors (remove brackets and format nicely)
						String authors = "Unknown";
						if (volumeInfo.has("authors")) {
							JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
							StringBuilder authorsBuilder = new StringBuilder();
							for (int j = 0; j < authorsArray.size(); j++) {
								authorsBuilder.append(authorsArray.get(j).getAsString());
								if (j < authorsArray.size() - 1) {
									authorsBuilder.append(", ");
								}
							}
							authors = authorsBuilder.toString();
						}

						String publishedDate = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "Unknown";

						// Get book cover image link
						String thumbnail = "";
						if (volumeInfo.has("imageLinks")) {
							thumbnail = volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString();
						}

						String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No Description";
						String ISBN = "None";
						if (volumeInfo.has("industryIdentifiers")) {
							JsonArray identifiers = volumeInfo.getAsJsonArray("industryIdentifiers");
							for (int j = 0; j < (int) identifiers.size(); j++) {
								JsonObject identifier = identifiers.get(j).getAsJsonObject();
								String type = identifier.get("type").getAsString();
								String identifierValue = identifier.get("identifier").getAsString();

								ISBN = identifierValue;
							}
						}

						books.add(new Book(title, ISBN, authors, description, publishedDate, thumbnail));
					}
				}
			} else {
				System.out.println("Error: Could not fetch data from API. Response code: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	private void searchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
			String query = searchTextField.getText();
			ArrayList<Book> books = searchBooks(query);

			showBooksInTable(books);
		}
	}


	private void showBooksInTable(ArrayList<Book> books) {
		// Column names for the table
		String[] columnNames = {"Title", "Authors", "Published Date", "ISBN"};

		// Data for the table (2D array, one row for each book)
		Object[][] rowData = new Object[books.size()][4];

		// Fill the table data with book details
		for (int i = 0; i < books.size(); i++) {
			Book book = books.get(i);
			rowData[i][0] = book.getTitle();
			rowData[i][1] = book.getAuthors();
			rowData[i][2] = book.getPublishedDate();
			rowData[i][3] = book.getISBN();
		}

		// Create the JTable with the data and column names
		JTable bookTable = new JTable(rowData, columnNames);

		// Set table properties
		bookTable.setFillsViewportHeight(true);  // Fill the entire panel with the table
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  // Adjust column size to fit content

		// Wrap the table in a scroll pane for better handling
		JScrollPane scrollPane = new JScrollPane(bookTable);

		// Clear the current content of the modulePanel (if needed)
		modulePanel.removeAll();

		// Set the layout for modulePanel if not set
		modulePanel.setLayout(new java.awt.BorderLayout());

		// Add the scroll pane (with the table) to the modulePanel
		modulePanel.add(scrollPane, java.awt.BorderLayout.CENTER);

		// Refresh the panel to show the new content
		modulePanel.revalidate();  // Recalculate the layout
		modulePanel.repaint();  // Repaint the panel to display the table
	}



	private void initComponents() {
		// create winndow
		panels = new JLabel();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setUndecorated(true);
		getContentPane().setLayout(null);
		panels.setOpaque(false);
		panels.setLayout(null);

		// search text field add
		searchTextField = new JTextField();
		searchTextField.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
		searchTextField.setForeground(new java.awt.Color(82, 210, 202));
		searchTextField.setText("Tìm Kiếm...");
		searchTextField.setBorder(null);
		searchTextField.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				searchTextFieldMouseClicked(evt);
			}
		});
		searchTextField.setBounds(500, 63, 240, 30);
		searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				searchTextFieldKeyReleased(evt);
			}
		});
		panels.add(searchTextField);

		// search bar add
		searchBar = new JLabel();
		searchBar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/searchButton.png"))); // NOI18N
		searchBar.setBounds(450, 55, 320, 46);
		panels.add(searchBar);

		// add book add
		addBook = new JLabel();
		addBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/addBook.png"))); // NOI18N
		addBook.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				addBookMouseClicked(evt);
			}
		});
		addBook.setBounds(300, 120, 160, 78);
		panels.add(addBook);

		modulePanel = new JPanel();
		modulePanel.setBounds(200, 220, 750, 350);
		panels.add(modulePanel);

		// excel input add
		excelInput = new JLabel();
		excelInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excelInput.png"))); // NOI18N
		excelInput.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				excelInputMouseClicked(evt);
			}
		});
		excelInput.setBounds(500, 120, 160, 78);
		panels.add(excelInput);

		// excel output add
		excelOutput = new JLabel();
		excelOutput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excelOutput.png"))); // NOI18N
		excelOutput.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				excelOnputMouseClicked(evt);
			}
		});
		excelOutput.setBounds(700, 120, 160, 78);
		panels.add(excelOutput);


		// main GUI add
		mainGUI = new JLabel();
		mainGUI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mainGUI.png"))); // NOI18N
		mainGUI.setBounds(0, 0, 1000, 600);
		panels.add(mainGUI);

		panels.setBounds(0, 0, 1200, 600);
		getContentPane().add(panels);
	}
}

class Book {
	private String title;
	private String authors;
	private String publishedDate;
	private String thumbnail;
	private String desription;
	private String ISBN;

	public Book(String title, String ISBN, String authors, String desription, String publishedDate, String thumbnail) {
		this.title = title;
		this.ISBN = ISBN;
		this.authors = authors;
		this.desription = desription;
		this.publishedDate = publishedDate;
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthors() {
		return authors;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String getDesription() {
		return desription;
	}

	public String getISBN() {
		return ISBN;
	}
}
