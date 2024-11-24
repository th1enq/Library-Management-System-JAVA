package src.librarysystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class MyDateTime implements Comparable<MyDateTime> {

  private LocalDateTime dateTime;

  public MyDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }


  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    return dateTime.format(formatter);
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  @Override
  public int compareTo(MyDateTime other) {
    return this.dateTime.compareTo(other.dateTime);
  }

  public LocalDateTime toLocalDateTime() {
    return this.dateTime;
  }
  public static void main(String[] args) {
  //  MyDateTime myDateTime = new MyDateTime(LocalDateTime.now());
  //  System.out.println("now: " + myDateTime.toString());
  }


}