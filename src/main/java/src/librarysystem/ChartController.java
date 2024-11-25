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

public class ChartController {

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
      if(sum>0)        pieChartData.add(new PieChart.Data("Other",sum));

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return pieChartData;
  }

}
