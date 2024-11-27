package src.librarysystem;

/**
 * Lớp này đại diện cho một cặp khóa-giá trị, nơi mỗi cặp có một khóa và một giá trị có thể là bất
 * kỳ kiểu dữ liệu nào. Cặp khóa-giá trị này hữu ích khi cần lưu trữ dữ liệu liên kết với nhau.
 *
 * @param <K> Kiểu của khóa.
 * @param <V> Kiểu của giá trị.
 */
class Pair<K, V> {

  private K key;
  private V value;

  /**
   * Khởi tạo một cặp khóa-giá trị với khóa và giá trị đã được cung cấp.
   *
   * @param key   Khóa của cặp.
   * @param value Giá trị của cặp.
   */
  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Lấy khóa của cặp.
   *
   * @return Khóa của cặp.
   */
  public K getKey() {
    return key;
  }

  /**
   * Lấy giá trị của cặp.
   *
   * @return Giá trị của cặp.
   */
  public V getValue() {
    return value;
  }

  /**
   * Trả về chuỗi mô tả của cặp khóa-giá trị, với khóa và giá trị được nối với nhau.
   *
   * @return Chuỗi mô tả của cặp khóa-giá trị.
   */
  @Override
  public String toString() {
    return key + " " + value;
  }
}