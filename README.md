# Library Management System

## Giới thiệu
### Mục tiêu bài tập lớn: 
- Bài tập lớn này nhằm tạo ra một hệ thống quản lý thư viện cho người quản lý và hệ thống mượn, trả, tìm kiếm sách cho sinh viên áp dụng các nguyên lý về lập trình hướng đối tượng đã được học ở môn OOP
### Các tính năng chính:
#### Tính năng chung
- Đăng nhập, đăng ký tài khoản.
- Cho phép reset mật khẩu qua mã xác thực email
- Tìm kiếm sách từ thư viện / API. Cho phép tìm kiếm nâng cao bằng ISBN, Title, Author, ...
- Mượn, trả sách
- Bình luận, đánh giá sách
- Cho phép quét mã QR để tới thông tin chi tiết của sách
- Hệ thống đề xuất sách
- Hệ thống quản lý tình trạng mượn trả / sách
- Hệ thống thông báo
- Chỉnh sửa thông tin cá nhân, đổi mật khẩu
- Xuất file excel tình trạng sách, danh sách sinh viên
#### Tính năng dành riêng cho admin
- Thêm sách từ API hoặc thủ công
- Xóa, chỉnh sửa sách
- Chấp nhận / từ chối yêu cầu mượn sách từ sinh viên
- Hệ thống quản lý user cho phép tạo, xóa, ban hay unban tài khoản
## Công nghệ sử dụng
- **Ngôn ngữ:** Java
- **Thư viện/Framework**: JavaFX (Giao diện người dùng)
- **API**: Google Book API
- **Database**: MySql
- **Công cụ phát triển:** IntelliJ, Vscode
## Cách cài đặt
1. **Yêu cầu hệ thống**
   - Java JDK 17+
   - IDE IntelliJ hoặc Vscode
2. **Cách cài đặt**
   - Clone repo: `git clone https://github.com/th1enq/Library-Management-System-JAVA.git`
   - Add project vào IDE và sử dụng
## Hướng dẫn sử dụng / Chi tiết các tính năng
### Đăng nhập / Đăng ký
- Đăng nhập, đăng ký sử dụng tài khoản email của bạn. Hãy chắc chắn rằng email chính xác vì nó sử dụng lúc bạn quên mật khẩu
- Mật khẩu được mã hóa theo chuẩn thuật toán Bycrypt để tăng tính bảo mật
  <details close>
    <summary><samp>UI Đăng nhập, Đăng ký, Quên mật khẩu</samp></summary>
    <br>
    Đăng nhập:
    <br>
    <img src="https://github.com/user-attachments/assets/f7eb0e5a-588f-4ea4-8ab0-caf4789e19d9" alt="UI Đăng nhập" width="1000">
    <br>
    Đăng ký:
    <br>
    <img src="https://github.com/user-attachments/assets/cc400a51-1945-40c6-a972-4dad443deb3e" alt="UI Đăng ký" width="1000">
    <br>
    Gửi mã xác nhận về email:
    <br>
    <img src="https://github.com/user-attachments/assets/66b2cc21-9b0a-4459-87e4-e2b5e2143a12" alt="UI Reset Password" width="1000">
    <br>
  </details>
### Giao diện người dùng
  <details close>
    <summary><samp>Dashboard hiển thể thể loại sách, số lượt truy cập ...</samp></summary>
    <br>
    <img src="https://github.com/user-attachments/assets/c3990c62-fe74-4c61-bfa7-d7113b57a4ee" alt="UI Reset Password" width="1000">
  </details>

  <details close>
    <summary><samp>Tìm kiếm sách từ thư viện hoặc API</samp></summary>
    <br>
    <img src="https://github.com/user-attachments/assets/79b13b00-1f9a-41e1-aa0a-4cf5d77161d1" alt="UI Reset Password" width="1000">
  </details>
  <details close>
    <summary><samp>Xem chi tiết sách mượn, xóa, sửa, thêm vào thư viện</samp></summary>
    <br>
    <img src="https://github.com/user-attachments/assets/291c9b80-7411-4eb0-a444-25acf5652c04" alt="UI Reset Password" width="1000">
  </details>
  <details close>
    <summary><samp>Cho phép bình luận, rating về sách</samp></summary>
    <br>
    <img src="https://github.com/user-attachments/assets/593a6913-4e47-4c0c-865b-1e2acfd064df" alt="UI Reset Password" width="1000">
  </details>
  <details close>
    <summary><samp>Hệ thống gợi ý sách, Hệ thống quản lý tình trạng sách</samp></summary>
    <br>
    <img src="https://github.com/user-attachments/assets/fe35dc97-a762-4ec4-a43b-cb34e9bede7b" alt="UI Reset Password" width="1000">
  </details>
  <details close>
    <summary><samp>Hệ thống quản lý sinh viên</samp></summary>
    <br>
    <img src="https://github.com/user-attachments/assets/f118172b-8b38-4781-9d5c-51d72a6b155e" alt="UI Reset Password" width="1000">
  </details>
  <details close>
    <summary><samp>Hệ thống quản lý thông báo</samp></summary>
    <br>
    <img src="https://github.com/user-attachments/assets/f89fbb3d-7d60-44b3-b710-05d38e440488" alt="UI Reset Password" width="1000">
  </details>
  <details close>
    <summary><samp>Hệ thống chỉnh sửa thông tin cá nhân, đổi mật khẩu, UI Setting</samp></summary>
    <br>
    <img src="https://github.com/user-attachments/assets/52682f64-2a62-4a36-b046-5500dd7da9f2" alt="UI Reset Password" width="1000">
  </details>

## Sử dụng các nguyên tắc lập trình OOP,  Design Pattern
- Các quan hệ giữa các lớp, đối tượng được thể hiện ở classDiagram.png
  ![image](https://github.com/user-attachments/assets/e4d50ad5-52bb-49f2-8c91-f40d8dfc325f)
- Sử dụng các design pattern như Singleton, Abstract Factory, ...
- Tích hợp đa luồng để cải thiện hiệu suất của chương trình
- ...

## Thành viên
| **Họ và tên**        | **Mã sinh viên** |
|-----------------------|------------------|
| Quách Đức Thiện       | 23020161         |
| Đỗ Đức Thắng      | 23020158         |
| Cao Trần Hà Thái   | 23020152 |

## Kế hoạch phát triển
- **Phiên bản 1.0:** Hoàn thiện các tính năng cơ bản và giao diện người dùng (Done)
- **Phiên bản 2.0:** Tăng cường hiệu suất, bảo mật và thêm các tính năng nâng cao hơn, ... (Comming soon)







   
