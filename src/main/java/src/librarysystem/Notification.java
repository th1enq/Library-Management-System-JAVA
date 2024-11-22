package src.librarysystem;



class Notification {

  private int id;
  private int senderId;
  private int receiverId;
  private String message;
  private MyDateTime createdAt;

  public Notification(int senderId, int receiverId, String message) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.message = message;
    this.createdAt = new MyDateTime(java.time.LocalDateTime.now());
  }

  public Notification(int id, int senderId, int receiverId, String message, MyDateTime createdAt) {
    this.id = id;
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.message = message;
    this.createdAt = createdAt;
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

  public String getReceiver(){
    if(senderId < 999)  {
      return "user";
    }
    if(senderId == 999){
      return "admin";
    }
    return "Hệ thống";
  }
  @Override
  public String toString() {
    return "Notification{" +
        " từ "  + getReceiver() +
        ", senderId=" + senderId +
        ", receiverId=" + receiverId +
        ", message='" + message + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}