package src.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

/**
 * Lớp này quản lý các biểu đồ thống kê liên quan đến số lượt đăng nhập và phân loại sách trong hệ thống.
 * Nó cung cấp các phương thức để cập nhật và lấy dữ liệu cần thiết cho các biểu đồ.
 */
public class ChartController {

  /**
   * Cập nhật số lượng đăng nhập trong một ngày cụ thể trong cơ sở dữ liệu.
   * Phương thức này tăng số lượng đăng nhập cho ngày đã cho.
   *
   * @param dayOfWeek Ngày trong tuần (ví dụ: "Monday", "Tuesday", ...).
   */
  public static void updateLoginCount(String dayOfWeek) {
    String sql = "UPDATE daily_logins SET login_count = login_count + 1 WHERE day_of_week = ?";

    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, dayOfWeek);

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Lấy dữ liệu về số lượt đăng nhập trong tuần, mỗi ngày được biểu diễn bởi một đối tượng {@link XYChart.Data}.
   * Dữ liệu này được sử dụng để vẽ biểu đồ đường hoặc cột cho số lượt đăng nhập theo từng ngày.
   *
   * @return ObservableList chứa dữ liệu đăng nhập cho biểu đồ.
   */
  public static ObservableList<XYChart.Data<String, Number>> getLoginData() {
    Connection conn = DBInfo.conn();
    ObservableList<Data<String, Number>> loginData = FXCollections.observableArrayList();
    String sql = "SELECT day_of_week, login_count FROM daily_logins ORDER BY date";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        String day = rs.getString("day_of_week");
        int count = rs.getInt("login_count");
        loginData.add(new XYChart.Data<>(day, count));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return loginData;
  }

  /**
   * Lấy dữ liệu để vẽ biểu đồ tròn (pie chart) với số lượng sách phân loại.
   * Phương thức này lấy dữ liệu về số lượng sách theo từng loại và tạo ra các đối tượng {@link PieChart.Data} để sử dụng trong biểu đồ tròn.
   * Ngoài ra, còn tính toán và thêm một phần "Other" nếu còn số sách không thuộc 4 loại phổ biến nhất.
   *
   * @return ObservableList chứa dữ liệu cho biểu đồ tròn.
   */
  public static ObservableList<PieChart.Data> getPieChartData() {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    String sql = "SELECT name, cnt FROM category WHERE name != 'ALL' ORDER BY cnt DESC LIMIT 4";
    int tot = DBInfo.getBookCount();
    int sum = 0;
    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        String name = rs.getString("name");
        int count = rs.getInt("cnt");
        sum += count;
        pieChartData.add(new PieChart.Data(name, count));
      }
      sum = tot - sum;
      if (sum > 0) {
        pieChartData.add(new PieChart.Data("Other", sum));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return pieChartData;
  }

}
