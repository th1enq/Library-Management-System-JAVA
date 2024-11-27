package src.librarysystem;


class Notification {

  private int id;
  private int senderId;
  private int receiverId;
  private String message;
  private MyDateTime createdAt;
  private int type;

  public Notification(int senderId, int receiverId, String message, int type) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.message = message;
    this.createdAt = new MyDateTime(java.time.LocalDateTime.now());
    this.type = type;
  }

  public Notification(int id, int senderId, int receiverId, String message, MyDateTime createdAt,
                      int type) {
    this.id = id;
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.message = message;
    this.createdAt = createdAt;
    this.type = type;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSenderId() {
    return senderId;
  }

  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  public int getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(int receiverId) {
    this.receiverId = receiverId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public MyDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(MyDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getReceiver() {
    if (senderId < 999) {
      return "user";
    }
    if (senderId == 999) {
      return "admin";
    }
    return "System";
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Notification{" +
            " từ " + getReceiver() +
            ", senderId=" + senderId +
            ", receiverId=" + receiverId +
            ", message='" + message + '\'' +
            ", createdAt=" + createdAt +
            '}';
  }
}