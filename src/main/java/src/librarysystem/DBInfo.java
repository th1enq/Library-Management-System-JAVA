package src.librarysystem;

import java.awt.desktop.SystemEventListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.event.UndoableEditEvent;
import java.sql.Date;

/**
 * kieu du lieu gom 4 String.
 */
class CustomData {

    private String first;

    private String second;

    private String thirst;

    private String fourth;

    public CustomData(String first, String second, String thirst, String fourth) {
        this.first = first;
        this.second = second;
        this.fourth = fourth;
        this.thirst = thirst;
    }

    public String getfirst() {
        return first;
    }

    public void setfirst(String first) {
        this.first = first;
    }

    public String getsecond() {
        return second;
    }

    public void setsecond(String second) {
        this.second = second;
    }

    public String getfourth() {
        return fourth;
    }

    public void setfourth(String fourth) {
        this.fourth = fourth;
    }

    public String getthirst() {
        return thirst;
    }

    public void setthirst(String thirst) {
        this.thirst = thirst;
    }

    public void print() {
        System.out.println(first + " " + second + " " + thirst + " " + fourth);
    }

}

public class DBInfo {

    public static String curUsername = "";
    public static String curPass = "";
    public static int curId = 0;
    private static int numUser = 6;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Connection conn() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TESTT", "root", "");
            System.out.println("Connection Established...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static Vector<String> getValue(String name) {
        Vector<String> v = new Vector<>();

        return v;
    }

    public static Vector<Vector> outerVector;
    public static Vector colsName;

    public static void allBooks() throws SQLException {

    }

    public static Vector<Vector> outerVector1;
    public static Vector colsName1;

    public static void searchBooks_by(String itemName, String values) throws SQLException {

    }

    public static Vector<Vector> outerVector2;
    public static Vector colsName2;

    public static void viewLibrarians(String usertype) throws SQLException {

    }

    public static String value;

    public static String getNotice() {
        Connection con = DBInfo.conn();
        String query = "SELECT * FROM notice";
        value = "";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                value = res.getString(2);
                System.out.println("previous Notice is " + value);
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return value;
    }

    /**
     * check xem trong Database co quyen sach can tim ko?.
     *
     * @param itemName ten quyen sach can tim
     * @return
     */
    public static boolean inDb(String itemName) {
        try {
            String query = "SELECT * FROM `book` WHERE title = ? and avail = ?";
            Connection con = DBInfo.conn();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, "YES");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                preparedStatement.close();
                con.close();
                return true;
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addSlip(String itemName) {
        try {
            Connection con = DBInfo.conn();
            String sql = "INSERT INTO borrow_slip(user_id, book_name, borrow_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // Lấy ngày hiện tại và ngày sau 10 ngày
            LocalDate currentDate = LocalDate.now();
            LocalDate dateAfter10Days = currentDate.plusDays(10);

            // Thiết lập giá trị cho các tham số
            preparedStatement.setInt(1, curId); // user_id
            preparedStatement.setString(2, itemName); // book_name
            preparedStatement.setDate(3, Date.valueOf(currentDate)); // borrow_date
            preparedStatement.setDate(4, Date.valueOf(dateAfter10Days)); // return_date

            // Thực hiện chèn dữ liệu
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Slip added successfully! Rows affected: " + rowsAffected);

            // Đóng PreparedStatement và Connection
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error adding slip");
            e.printStackTrace();
        }
    }

    /**
     * muon sach.
     *
     * @param itemName ten sach
     */
    public static void borrowBook(String itemName) {
        try {
            Connection con = DBInfo.conn();
            String sql = "UPDATE book SET avail = ? WHERE title = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "NO");
            preparedStatement.setString(2, itemName);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Thay doi trang thai dong" + rowAffected);
                addSlip(itemName);
            }
            preparedStatement.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * tra sach
     */
    public static void returnBook(String itemName) {

        Connection con = DBInfo.conn();
        try {

            String sql = "DELETE FROM borrow_slip WHERE book_name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, itemName);
            int rowsAffected = 0;
            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("xoa khoi slip thành công!");
                preparedStatement.close();
                con.close();
                Connection con2 = DBInfo.conn();
                String sql2 = "UPDATE book SET avail = ? WHERE title = ?";
                PreparedStatement preparedStatement2 = con2.prepareStatement(sql2);
                preparedStatement2.setString(1, "YES");
                preparedStatement2.setString(2, itemName);
                int rowAffected2 = preparedStatement2.executeUpdate();
                if (rowAffected2 > 0) {
                    System.out.println("Thay doi trang thai dong" + rowAffected2);
                } else {
                    System.out.println("Thay doi trang thai ko thanh cong");
                }
                preparedStatement.close();
                con.close();

            } else {
                System.out.println("Không tìm thấy người trong slip cuon: " + itemName);
            }

            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            EE.printStackTrace();
            System.out.println("Loi xoa user ");
        }
    }


    public static void addPublisher(String name) {
        try {
            Connection con = DBInfo.conn();
            String sql = "INSERT INTO publisher(name) VALUE (?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("publisher added successfully! Rows affected: " + rowsAffected);
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            System.out.println("Error adding publisher");
            EE.printStackTrace();
        }
    }

    public static void deletePublisher(String a) {
        try {
            Connection con = DBInfo.conn();
            String sql = "DELETE FROM publisher WHERE name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, a);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("DEL publisher successfully! Rows affected: " + rowsAffected);
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            System.out.println("Error deleting PUblisher");
            EE.printStackTrace();
        }
    }

    public static void deleteAuthor(String a) {
        try {
            Connection con = DBInfo.conn();
            String sql = "DELETE FROM author WHERE name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, a);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("DEL author successfully! Rows affected: " + rowsAffected);
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            System.out.println("Error deleting author");
            EE.printStackTrace();
        }
    }

    public static void addAuthor(String a) {
        try {
            Connection con = DBInfo.conn();
            String sql = "INSERT INTO author(name) VALUE (?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, a);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("author added successfully! Rows affected: " + rowsAffected);
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            System.out.println("Error adding author");
            EE.printStackTrace();
        }
    }

    public static void addCategory(String a) {
        try {
            Connection con = DBInfo.conn();
            String sql = "INSERT INTO category(name) VALUE (?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, a);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Category added successfully! Rows affected: " + rowsAffected);
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            System.out.println("Error adding Category");
            EE.printStackTrace();
        }
    }

    public static void deleteCategory(String a) {
        try {
            Connection con = DBInfo.conn();
            String sql = "DELETE FROM category WHERE name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, a);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Category deleted successfully! Rows affected: " + rowsAffected);
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            System.out.println("Error deleting Category");
            EE.printStackTrace();
        }
    }

    public static void addBook(Book A) {
        try {
            Connection con = DBInfo.conn();
            if (inDb(A.getTitle())) {
                System.out.println("da co quyen sach nay roi");
                con.close();
                return;
            }
            String sql =
                    "INSERT INTO book (title, authors, publisher, publishedDate, thumbnail, ISBN, description, numPage, category, price, language, buyLink) "
                            +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, A.getTitle());
            preparedStatement.setString(2, A.getAuthors());
            preparedStatement.setString(3, A.getPublisher());
            preparedStatement.setString(4, A.getPublishedDate());
            preparedStatement.setString(5, A.getThumbnail());
            preparedStatement.setString(6, A.getISBN());
            preparedStatement.setString(7, A.getDescription());
            preparedStatement.setString(8, A.getNumPage());
            preparedStatement.setString(9, A.getCategory());
            preparedStatement.setString(10, A.getPrice());
            preparedStatement.setString(11, A.getLanguage());
            preparedStatement.setString(12, A.getBuyLink());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Book added successfully! Rows affected: " + rowsAffected);
            addCategory(A.getCategory());
            addPublisher(A.getPublisher());
            addAuthor(A.getAuthors());
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            System.out.println("Error adding book");
            EE.printStackTrace();
        }
    }

    /**
     * xóa sách.
     * @param A tên sách
     */
    public static void deleteBook(Book A) {
        try {
            Connection con = DBInfo.conn();
            if (!inDb(A.getTitle())) {
                System.out.println("ko co quyen sach nay");
                con.close();
                return;
            }
            String sql = "DELETE FROM book WHERE title = ? LIMIT 1";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, A.getTitle());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Book deleted successfully! Rows affected: " + rowsAffected);
            deleteCategory(A.getCategory());
            deletePublisher(A.getPublisher());
            deleteAuthor(A.getAuthors());
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            System.out.println("Error deleting book");
            EE.printStackTrace();
        }
    }

    /**
     * dang ki nguoi moi.
     *
     * @param id       id
     * @param name     name
     * @param username us
     * @param password p
     * @param usertype admin hay ngdung binh thuong
     */
    public static void Register(int id, String name, String username, String password,
                                String usertype) {
        try {
            Connection con = DBInfo.conn();
            String sql = "INSERT INTO registration(id,name, username, password, usertype) VALUE (?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            numUser += 1;
            preparedStatement.setInt(1, numUser);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, usertype);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Register successfully! Rows affected: " + rowsAffected);
            preparedStatement.close();
            con.close();
        } catch (SQLException ee) {
            ee.printStackTrace();
        }
    }

    /**
     * kiem tra mat khau
     *
     * @param username us
     * @param password pas
     * @return true/false
     */
    public static boolean checkPass(String username, String password) {
        String sql = "SELECT usertype FROM registration WHERE username = ? AND password = ?";

        try {
            Connection con = DBInfo.conn();
            if (con == null) {
                System.out.println("Ngu");
                return false;
            }
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
//                String userType = resultSet.getString("usertype");
//                System.out.println("Đúng! Loại người dùng: " + userType);
                return true;
            } else {
//                System.out.println("Tên đăng nhập hoặc mật khẩu không đúng!");

            }
            preparedStatement.close();
            con.close();
            return false;
        } catch (SQLException A) {
            A.printStackTrace();
            return false;
        }
    }

    /**
     * hamf dang nhap.
     *
     * @param Username U
     * @param Password P
     */
    public static void login(String Username, String Password) {
        if (checkPass(Username, Password)) {
            curPass = Password;
            curUsername = Username;
            curId = findUserId(Username, Password);
        }
    }

    public static int findUserId(String username, String password) {
        try {
            Connection con = DBInfo.conn();
            String sql = "SELECT id FROM registration WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                System.out.println("ID của người dùng là: " + id);
                return id;
            } else {
                System.out.println("Không tìm thấy người dùng với username và password đã cho.");

            }
            preparedStatement.close();
            con.close();
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Loi ham tim id biet user va pass");
            return -1;
        }
    }

    /**
     * chinh sua thong tin nguoi dung.
     *
     * @param id          id
     * @param newUsername ten
     * @param newPassword mk
     */
    public static void updateUser(int id, String newUsername, String newPassword) {
        Connection con = DBInfo.conn();
        try {

            String sql = "UPDATE registration SET username = ?, password = ? WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, newPassword);
            preparedStatement.setInt(3, id);
            int rowsAffected = 0;
            System.out.println(rowsAffected);
            rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy người dùng với id: " + id);
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            EE.printStackTrace();
            System.out.println("Loi sua user va pass");
        }
    }

    /**
     * chinh sua email ng dung.
     *
     * @param id       id
     * @param newEmail E
     */
    public static void updateEmail(int id, String newEmail) {
        Connection con = DBInfo.conn();
        try {

            String sql = "UPDATE registration SET username = ? WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, id);
            int rowsAffected = 0;
            System.out.println(rowsAffected);
            rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy người dùng với id: " + id);
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            EE.printStackTrace();
            System.out.println("Loi sua email");
        }
    }

    public static void DeleteUser(String name) {
        Connection con = DBInfo.conn();
        try {

            String sql = "DELETE FROM registration WHERE username = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, name);

            int rowsAffected = 0;
            rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy người dùng với username: " + name);
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException EE) {
            EE.printStackTrace();
            System.out.println("Loi xoa user ");
        }
    }

    public static void rateBook(String itemName, int score) {
        try {
            Connection con = DBInfo.conn();
            String sql = "SELECT rating FROM book WHERE title = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, itemName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                int rating = resultSet.getInt("rating");
                System.out.println("rating hien tai cua cuon sach la " + rating);
                Connection con2 = DBInfo.conn();

                String sql2 = "UPDATE book SET rating = ? WHERE title = ?";
                PreparedStatement preparedStatement2 = con2.prepareStatement(sql2);
                int newrating = (rating + score) / 2;
                preparedStatement2.setInt(1, newrating);
                preparedStatement2.setString(2, itemName);

                //System.out.println(newrating);
                int rowsAffected2 = 0;
                rowsAffected2 = preparedStatement2.executeUpdate();
                if (rowsAffected2 > 0) {
                    System.out.println("Cập nhật thành công rating!");
                } else {
                    System.out.println("Cập nhật ko thành công rating!");
                }
                preparedStatement2.close();
                con2.close();
                preparedStatement.close();
                con.close();
            } else {
                System.out.println("ko tim thay sach de rating");

            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Loi ham rateBook");
        }
    }

    /**
     * lấy ra danh sách các cuốn sách trong database.
     *
     * @return arraylist gồm 4 thông số id, tên, tác giả, avail.
     */
    public static ArrayList<CustomData> getUserList() {
        Connection con = DBInfo.conn();
        ArrayList<CustomData> ret = new ArrayList<>();
        String query = "SELECT * FROM registration";
        value = "";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                CustomData tmp = new CustomData(res.getString(1), res.getString(2), res.getString(3),
                        res.getString(5));
                tmp.print();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return ret;
    }

    /**
     * laay ra những giá trị phân biệt của 1 cột của 1 bảng trong database.
     *
     * @param columnName teen cột
     * @param tableName  tên bảng
     * @return arraylist chứa các phần tử thỏa mãn
     */
    public static ArrayList<String> getDistinctValues(String columnName, String tableName) {
        ArrayList<String> distinctValues = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = DBInfo.conn();
            String sql = "SELECT DISTINCT " + columnName + " FROM " + tableName;
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                distinctValues.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return distinctValues;
    }

    /**
     * Hamf sort cac cuon sach theo yeu cau cho trc.
     *
     * @param author    tacgia
     * @param category  muc
     * @param publisher NXB
     * @return array list chua cac cuon sach thoa man
     */
    public static ArrayList<Book> getBookList(String author, String category, String publisher) {
        ArrayList<Book> bookList = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = DBInfo.conn();
            int prev = 0;
            String sql = "SELECT title, ISBN, authors, publisher, publishedDate, description,thumbnail, numPage, category, price, language, buyLink,avail,rating FROM book";
            if (author != null && !author.equals("ALL")) {
                sql += (" WHERE author = " + "'" + author + "'");
                prev++;
            }
            if (category != null && !category.equals("ALL")) {
                if (prev == 0) {
                    sql += " WHERE ";
                } else {
                    sql += " and ";
                }
                sql += ("category = " + "'" + category + "'");
                prev++;
            }
            if (publisher != null && !publisher.equals("ALL")) {
                if (prev == 0) {
                    sql += " WHERE ";
                } else {
                    sql += " and ";
                }
                sql += ("publisher = " + "'" + publisher + "'");
                prev++;
            }

            //  System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String ISBN = resultSet.getString("ISBN");
                String Author = resultSet.getString("authors");
                String Publisher = resultSet.getString("publisher");
                String publishedDate = resultSet.getString("publishedDate");
                String description = resultSet.getString("description");
                String thumbnail = resultSet.getString("thumbnail");
                String numPage = resultSet.getString("numPage");
                String Category = resultSet.getString("category");
                String price = resultSet.getString("price");
                String language = resultSet.getString("language");
                String buyLink = resultSet.getString("buyLink");
                String avail = resultSet.getString("avail");
                int rating = resultSet.getInt("rating");
                Book book = new Book(title, ISBN, Author, Publisher, publishedDate,
                        description, thumbnail, numPage, Category, price,
                        language, buyLink, avail, rating);
                book.print();
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookList;
    }

    public static void main(String[] args) {
        //  ArrayList<CustomData> test = DBInfo.getBookList();
        // DBInfo.rateBook("1984",6);
        //ArrayList<Book> tmp = DBInfo.getBookList(null, "ALL", "ALL");
        Book myBook = new Book("Tit Example", "123456789", "Author Example", "Publisher Example",
                "2024-10-10", "Description Example", "Thumbnail Example",
                "300", "Fiction", "19.99", "English", "http://buylink.com");
        //DBInfo.deleteBook(myBook);
        // DBInfo.addBook(myBook);
    }

}