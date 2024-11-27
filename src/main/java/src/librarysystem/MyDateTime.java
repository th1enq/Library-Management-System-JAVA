package src.librarysystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Lớp này đại diện cho một đối tượng ngày giờ với khả năng lưu trữ và thao tác với đối tượng {@link LocalDateTime}.
 * Nó hỗ trợ các phương thức để định dạng ngày giờ dưới dạng chuỗi, so sánh với đối tượng {@link MyDateTime} khác,
 * và chuyển đổi đối tượng {@link MyDateTime} về {@link LocalDateTime}.
 */
public class MyDateTime implements Comparable<MyDateTime> {

  private LocalDateTime dateTime;

  /**
   * Tạo một đối tượng {@code MyDateTime} với giá trị ngày giờ đã cho.
   *
   * @param dateTime Ngày giờ muốn gán cho đối tượng {@code MyDateTime}.
   */
  public MyDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  /**
   * Trả về chuỗi đại diện cho ngày giờ dưới định dạng "dd-MM-yyyy HH:mm:ss".
   *
   * @return Chuỗi ngày giờ đã định dạng.
   */
  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    return dateTime.format(formatter);
  }

  /**
   * Lấy giá trị {@link LocalDateTime} của đối tượng {@code MyDateTime}.
   *
   * @return Ngày giờ dưới dạng {@link LocalDateTime}.
   */
  public LocalDateTime getDateTime() {
    return dateTime;
  }

  /**
   * Gán giá trị ngày giờ cho đối tượng {@code MyDateTime}.
   *
   * @param dateTime Ngày giờ mới muốn gán cho đối tượng {@code MyDateTime}.
   */
  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  /**
   * So sánh đối tượng {@code MyDateTime} hiện tại với một đối tượng {@code MyDateTime} khác.
   *
   * @param other Đối tượng {@code MyDateTime} cần so sánh.
   * @return Một số nguyên âm, 0 hoặc dương tùy thuộc vào việc đối tượng hiện tại có nhỏ hơn, bằng hay lớn hơn đối tượng {@code other}.
   */
  @Override
  public int compareTo(MyDateTime other) {
    return this.dateTime.compareTo(other.dateTime);
  }

  /**
   * Chuyển đổi đối tượng {@code MyDateTime} thành đối tượng {@link LocalDateTime}.
   *
   * @return Ngày giờ dưới dạng {@link LocalDateTime}.
   */
  public LocalDateTime toLocalDateTime() {
    return this.dateTime;
  }

  /**
   * Phương thức main để thử nghiệm lớp {@code MyDateTime}.
   *
   * @param args Tham số dòng lệnh.
   */
  public static void main(String[] args) {
    //  MyDateTime myDateTime = new MyDateTime(LocalDateTime.now());
    //  System.out.println("now: " + myDateTime.toString());
  }
}
