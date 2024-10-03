package org.example.src;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewBooks extends JFrame {
	private JPanel contentPane;
	private JTextField searchField;
	private JLabel titleLabel;
	private JLabel authorsLabel;
	private JLabel descriptionLabel;
	private JLabel publishedDateLabel;
	private JLabel imageLabel;
	private JLabel ISBNLabel;
	private JLabel isbnImage;
	private ArrayList<Book> allBooks; // Store all fetched books
	private int currentBookIndex = 0; // Track current book index

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ViewBooks frame = new ViewBooks();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ViewBooks() {
		// Create the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 10, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// *** Top Bar Panel ***
		JPanel topBar = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon img = new ImageIcon("src/main/resources/img/topBarBookSearch.jpg");
				g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
				g.setColor(new Color(0, 0, 0, 150)); // Black color with 150 alpha for transparency
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};

		topBar.setPreferredSize(new Dimension(getWidth(), 120));  // Adjust height as needed
		topBar.setLayout(new GridBagLayout());  // Use GridBagLayout for centering

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 0, 10, 0);  // Add some padding

		// Bar components (Title Label)
		JLabel headerLabel = new JLabel("Find your next book to read.");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Georgia", Font.BOLD, 20));
		gbc.anchor = GridBagConstraints.CENTER; // Center the label horizontally
		topBar.add(headerLabel, gbc);

		// Input Field under the Label
		gbc.gridy = 1;  // Move to the next row
		gbc.insets = new Insets(10, 0, 0, 0);  // Add padding between label and text field
		searchField = new JTextField();
		searchField.setHorizontalAlignment(JTextField.CENTER);  // Center the placeholder text
		searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));  // Add a border around the input field
		searchField.setPreferredSize(new Dimension(300, 40));  // Adjust the size of the input field

		topBar.add(searchField, gbc);

		// Add ActionListener to perform search when Enter is pressed
		searchField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String query = searchField.getText();
				if (!query.isEmpty()) {
					allBooks = searchBooks(query); // Store all fetched books
					currentBookIndex = 0; // Reset to the first book
					displayBook(currentBookIndex); // Display the first book
				}
			}
		});

		contentPane.add(topBar, BorderLayout.NORTH);

		// *** Book Display Panel ***
		// Create the book display panel
		JPanel bookPanel = new JPanel();
		bookPanel.setLayout(new BorderLayout());
		bookPanel.setBackground(Color.WHITE); // Set a white background

// Labels to display book information
		imageLabel = new JLabel();
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		imageLabel.setBorder(new EmptyBorder(10, 30, 10, 10)); // Padding around the image
		bookPanel.add(imageLabel, BorderLayout.WEST);  // Place the image on the left

// Create a details panel with GridBagLayout for more control
		JPanel detailsPanel = new JPanel();
		detailsPanel.setLayout(new GridBagLayout());
		detailsPanel.setBackground(Color.WHITE); // Set background to match bookPanel
		gbc = new GridBagConstraints();

// Title label
		titleLabel = new JLabel();
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Larger, bold font for title
		titleLabel.setForeground(Color.BLACK); // Set font color
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 0); // Padding around the title
		detailsPanel.add(titleLabel, gbc);

		isbnImage = new JLabel();
		gbc.gridy = 3;
		detailsPanel.add(isbnImage, gbc);
// Authors label
		authorsLabel = new JLabel();
		authorsLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Regular font for author
		authorsLabel.setForeground(Color.GRAY); // Slightly lighter color
		gbc.gridy = 1;
		detailsPanel.add(authorsLabel, gbc);

		ISBNLabel = new JLabel();
		ISBNLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Regular font for author
		ISBNLabel.setForeground(Color.GRAY); // Slightly lighter color
		gbc.gridy = 2;
		detailsPanel.add(ISBNLabel, gbc);

// Published date label
		publishedDateLabel = new JLabel();
		publishedDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridy = 4;
		detailsPanel.add(publishedDateLabel, gbc);

// Description label
		descriptionLabel = new JLabel();
		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font for description
		descriptionLabel.setForeground(Color.DARK_GRAY); // Darker color for readability
		gbc.gridy = 5;
		gbc.insets = new Insets(10, 0, 0, 0); // More space above the description
		detailsPanel.add(descriptionLabel, gbc);

// Add the details panel to the book panel
		bookPanel.add(detailsPanel, BorderLayout.CENTER);  // Place the details on the right

// Add the book panel to the content pane
		contentPane.add(bookPanel, BorderLayout.CENTER);


		// *** Navigation Buttons ***
		JPanel buttonPanel = new JPanel();
		JButton previousButton = new JButton("Trở lại");
		JButton nextButton = new JButton("Tiến tới");

		previousButton.addActionListener(e -> {
			if (currentBookIndex > 0) {
				currentBookIndex--;
				displayBook(currentBookIndex); // Display the previous book
			}
		});

		nextButton.addActionListener(e -> {
			if (currentBookIndex < allBooks.size() - 1) {
				currentBookIndex++;
				displayBook(currentBookIndex); // Display the next book
			}
		});

		buttonPanel.add(previousButton);
		buttonPanel.add(nextButton);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void generateBarcode(String isbn) {
		PDF417Writer pdf417Writer = new PDF417Writer();
		BitMatrix bitMatrix;

		try {
			// Generate the barcode
			bitMatrix = pdf417Writer.encode(isbn, BarcodeFormat.PDF_417, 300, 150);
			BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

			// Save the barcode image to file
			File outputFile = new File("src/main/resources/img/isbn_barcode.png");
			ImageIO.write(barcodeImage, "png", outputFile);
		} catch (WriterException | IOException e) {
			System.err.println("Error generating barcode: " + e.getMessage());
		}
	}

	// Method to call the Google Books API and fetch book data
	private ArrayList<Book> searchBooks(String query) {
		ArrayList<Book> books = new ArrayList<>();
		try {
			String formattedQuery = query.replace(" ", "+");
			String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + formattedQuery;

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
						generateBarcode(ISBN);
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

	// Method to display the book information
	private void displayBook(int index) {
		if (allBooks != null && !allBooks.isEmpty() && index >= 0 && index < allBooks.size()) {
			Book book = allBooks.get(index);
			titleLabel.setText("Tiêu đề: " + book.getTitle());
			ISBNLabel.setText("ISBN: " + book.getISBN());

			String isbn = book.getISBN();
			generateBarcode(isbn);  // Call to generate the barcode image

			// Load the barcode image and display it under the ISBN label
			String barcodePath = "src/main/resources/img/isbn_barcode.png"; // Adjust path as needed
			try {
				ImageIcon barcodeIcon = new ImageIcon(barcodePath);
				isbnImage.setIcon(barcodeIcon); // Set the generated barcode image to the JLabel
			} catch (Exception e) {
				isbnImage.setIcon(null);
				isbnImage.setText("Không có mã vạch");
			}

			authorsLabel.setText("Tác giả: " + book.getAuthors());
			descriptionLabel.setText("<html>Mô tả: " + formatDescription(book.getDesription()) + "</html>"); // Allow HTML formatting
			publishedDateLabel.setText("Ngày xuất bản: " + book.getPublishedDate());

			// Load book cover image
			try {
				URL url = new URL(book.getThumbnail());
				ImageIcon imageIcon = new ImageIcon(ImageIO.read(url));
				imageLabel.setIcon(imageIcon);
			} catch (Exception e) {
				imageLabel.setIcon(null);
				imageLabel.setText("Không có ảnh");
			}
		}
	}

	// Helper method to format description to fit within 50 characters per line
	private String formatDescription(String description) {
		StringBuilder formattedDescription = new StringBuilder();
		String[] words = description.split(" ");
		StringBuilder line = new StringBuilder();

		for (String word : words) {
			if (line.length() + word.length() > 100) { // 50 characters limit
				formattedDescription.append(line.toString().trim()).append("<br>"); // Use HTML line break
				line = new StringBuilder();
			}
			line.append(word).append(" ");
		}
		formattedDescription.append(line.toString().trim()); // Add any remaining words
		return formattedDescription.toString();
	}
}

// Simple Book class to hold book data
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
