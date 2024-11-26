
DROP DATABASE IF EXISTS TESTT;
CREATE DATABASE TESTT;
USE TESTT;

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
    title VARCHAR(255) UNIQUE,
    authors VARCHAR(255),
    publisher VARCHAR(255),
    publishedDate VARCHAR(200),
    thumbnail VARCHAR(255),
    ISBN VARCHAR(130) PRIMARY KEY,
    description TEXT,
    numPage VARCHAR(30),
    category VARCHAR(100),
    price VARCHAR(30),
    language VARCHAR(50),
    buyLink VARCHAR(255),
    avail INT DEFAULT 1,
    rating INT NOT NULL DEFAULT 0,
    numView INT DEFAULT 0
);
INSERT INTO book (title, authors, publisher, publishedDate, thumbnail, ISBN, description, numPage, category, price, language, buyLink, rating) VALUES
-- Science (2 books)
('A Brief History of Time', 'Stephen Hawking', 'Bantam', '1988-04-01', 'http://books.google.com/books/content?id=oZhagX6UWOMC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9780553380163', 'A landmark volume in science writing by one of the great minds of our time', '212', 'Science', '18.99', 'English', 'https://play.google.com/store/books/details?id=oZhagX6UWOMC&rdid=book-oZhagX6UWOMC&rdot=1&source=gbs_atb&pcampaignid=books_booksearch_atb&pli=1',  5),
('The Selfish Gene', 'Richard Dawkins', 'Oxford University Press', '1976-08-30', 'http://books.google.com/books/content?id=o59HDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9780192860927', 'A classic exposition of evolutionary biology', '360', 'Science', '15.99', 'English', 'https://play.google.com/store/books/details?id=o59HDAAAQBAJ&source=gbs_api',  5),

-- Manga (4 books)
('Naruto', 'Masashi Kishimoto', 'Shueisha', '1999-09-21', 'http://books.google.com/books/content?id=bQbwAQAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api', '9781569319000', 'A young ninja who seeks recognition from his peers', '220', 'Manga', '9.99', 'Japanese', 'http://b...content-available-to-author-only...m.vn/books?id=bQbwAQAAQBAJ&dq=naruto&hl=&source=gbs_api',  5),
('One Piece', 'Eiichiro Oda', 'Shueisha', '1997-07-22', 'http://books.google.com/books/content?id=B83LDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9784088725093', 'The story of a young pirate and his adventures', '230', 'Manga', '9.99', 'Japanese', 'https://play.google.com/store/books/details?id=B83LDwAAQBAJ&source=gbs_api',  5),
('Attack on Titan', 'Hajime Isayama', 'Kodansha', '2009-09-09', 'http://books.google.com/books/content?id=sX0qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781612620244', 'Humanity fights against giant humanoid Titans', '192', 'Manga', '10.99', 'Japanese', 'https://play.google.com/store/books/details?id=sX0qDwAAQBAJ&source=gbs_api',  5),
('My Hero Academia', 'Kohei Horikoshi', 'Shueisha', '2014-07-07', 'http://books.google.com/books/content?id=nQA-CgAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api', '9781421582696', 'A boy born without superpowers in a world where they are common', '240', 'Manga', '10.99', 'Japanese', 'http://b...content-available-to-author-only...m.vn/books?id=nQA-CgAAQBAJ&dq=My+Hero+Academia&hl=&source=gbs_api', 5),

-- Magazine (10 books)
('National Geographic', 'Various', 'National Geographic Partners', '2023-01-01', 'http://books.google.com/books/content?id=VCYjAQAAMAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781426217777', 'Exploring the world and all its beauty', '100', 'Magazine', '5.99', 'English', 'https://play.google.com/store/books/details?id=VCYjAQAAMAAJ&source=gbs_api',  5),
('Time', 'Various', 'Time USA', '2023-02-01', 'http://books.google.com/books/content?id=lwgAAAAAMBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781618932648', 'Covers world news, politics, and culture', '100', 'Magazine', '6.99', 'English', 'http://b...content-available-to-author-only...m.vn/books?id=lwgAAAAAMBAJ&dq=times&hl=&source=gbs_api',  5),
('Forbes', 'Various', 'Forbes Media', '2023-03-01', 'http://books.google.com/books/content?id=E047cuo415YC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781618932649', 'Leading source for reliable business news', '90', 'Magazine', '6.99', 'English', 'https://play.google.com/store/books/details?id=E047cuo415YC&source=gbs_api',  5),
('The Economist', 'Various', 'The Economist Group', '2023-04-01', 'http://books.google.com/books/content?id=TDVRAQAAMAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781618932650', 'Delivers current affairs and international business news', '120', 'Magazine', '7.99', 'English', 'https://play.google.com/store/books/details?id=TDVRAQAAMAAJ&source=gbs_api',  5),
('Scientific American', 'Various', 'Springer Nature', '2023-05-01', 'http://books.google.com/books/content?id=rGiAErFjXJsC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781618932651', 'Popular science magazine', '110', 'Magazine', '7.99', 'English', 'play.google.com/store/books/details?id=rGiAErFjXJsC&source=gbs_api',  4),
('People', 'Various', 'Meredith Corporation', '2023-06-01', 'http://books.google.com/books/content?id=2Z6voNKabXYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781618932652', 'Celebrity and human-interest stories', '95', 'Magazine', '5.99', 'English', 'link12.com', 3),
('Vogue', 'Various', 'Condé Nast', '2023-07-01', 'http://books.google.com/books/content?id=VAjhEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781618932653', 'Fashion, beauty, and lifestyle magazine', '100', 'Magazine', '6.99', 'English', 'link13.com',  5),
('New Scientist', 'Various', 'New Scientist Ltd.', '2023-08-01', 'http://books.google.com/books/content?id=-M4-AQAAIAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api', '9781618932654', 'Science and technology news', '110', 'Magazine', '8.99', 'English', 'link14.com',  4),
-- Horror (4 books)
('Dracula', 'Bram Stoker', 'Archibald Constable and Company', '1897-05-26', 'http://books.google.com/books/content?id=39lCAQAAMAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781435159570', 'The story of the famous vampire', '418', 'Horror', '12.99', 'English', 'link17.com', 5),
('Frankenstein', 'Mary Shelley', 'Lackington, Hughes, Harding, Mavor & Jones', '1818-01-01', 'http://books.google.com/books/content?id=9xHCAgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9780486282114', 'The story of Victor Frankenstein and his monstrous creation', '280', 'Horror', '9.99', 'English', 'link18.com',  5),
('The Shining', 'Stephen King', 'Doubleday', '1977-01-28', 'http://books.google.com/books/content?id=VR5eEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9780385121675', 'A family isolated in a haunted hotel', '447', 'Horror', '10.99', 'English', 'link19.com', 5),
('It', 'Stephen King', 'Viking', '1986-09-15', 'http://books.google.com/books/content?id=fVWQDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9780450411434', 'A group of kids facing a monster', '1138', 'Horror', '14.99', 'English', 'link20.com',  5),
-- Self-Help (2 books)
('The Power of Now', 'Eckhart Tolle', 'New World Library', '1997-10-01', 'http://books.google.com/books/content?id=QFQ7DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9781577314806', 'A guide to spiritual enlightenment and living in the present moment.', '229', 'Self-Help', '14.99', 'English', 'link21.com', 5),
('Atomic Habits', 'James Clear', 'Penguin', '2018-10-16', 'http://books.google.com/books/content?id=Jrx6EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', '9780735211292', 'A proven way to build good habits and break bad ones.', '320', 'Self-Help', '18.99', 'English', 'link22.com',5);

DROP TABLE IF EXISTS `borrow_slip`;

CREATE TABLE `borrow_slip` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_name VARCHAR(500) NOT NULL,
    borrow_date DATETIME NOT NULL,
    return_date DATETIME NOT NULL
);

CREATE TABLE `borrow_history` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_name VARCHAR(255) NOT NULL,
    borrow_date DATETIME NOT NULL,
    return_date DATETIME NOT NULL,
    FOREIGN KEY (book_name) REFERENCES book(title) ON DELETE CASCADE
);

CREATE TABLE `borrow_request` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_name VARCHAR(500) NOT NULL,
    borrow_date DATETIME NOT NULL,
    return_date DATETIME NOT NULL,
    accepted TINYINT(1) DEFAULT 0,
    FOREIGN KEY (book_name) REFERENCES book(title) ON DELETE CASCADE
    -- accepted = 1 => đã xử lý (có thể chấp thuận hoặc không) --
);
--      Register(1,"nguyenvana","nguyenvana@gmail.com","password123","user","1"); --
--        Register(1,"tranthib","tranthib@gmail.com","password234","user","1"); --
--        Register(1,"abc","23020158@vnu.edu.vn","password345","user","1");  --
--        Register(1,"bcd","23020161@vnu.edu.vn","password111","user","1"); --
--        Register(1,"admin","levanc","password789","admin","1");   --
DROP TABLE IF EXISTS `registration`;
CREATE TABLE `registration` (
  `id` int(11) NOT NULL PRIMARY KEY,
  `name` varchar(100) DEFAULT NULL ,
  `username` varchar(200) NOT NULL UNIQUE,
  `password` varchar(100) NOT NULL,
  `usertype` enum('admin','user') DEFAULT 'user',
  `is_banned` tinyint(1) DEFAULT 0,
  `avatar_link` varchar(500) DEFAULT NULL,
  `MSV` varchar(100) DEFAULT NULL,
  `University` varchar(100) DEFAULT NULL,
  `Phone` varchar(100) DEFAULT NULL,
  `Cover_photo_link` varchar(500) DEFAULT NULL,
  `Reputation` int(11) DEFAULT 5
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Đang đổ dữ liệu cho bảng `registration`
--

INSERT INTO `registration` (`id`, `name`, `username`, `password`, `usertype`, `is_banned`, `avatar_link`, `MSV`, `University`, `Phone`, `Cover_photo_link`, `Reputation`) VALUES
(1, 'nguyenvana', 'nguyenvana@gmail.com', '$2a$10$SRJEZs8MjJVS0XCV0Ib4a.XuLCTEt47MzPy4hbx4zE1uRRpseyCcy', 'user', 0, NULL, '1', NULL, NULL, NULL, 5),
(2, 'tranthib', 'tranthib@gmail.com', '$2a$10$sXFpTi4pPIXSmeX5Wwa/tu20HuR/5xQXgxBqi41afURa.pvhjjGV6', 'user', 0, NULL, '1', NULL, NULL, NULL, 5),
(3, 'abc', '23020158@vnu.edu.vn', '$2a$10$37Axlx35fjBeZs9mtywWbuS/DinC62nGoJEcy8u.gLlXS4FppUVFu', 'user', 0, 'file:/C:/Users/PC/IdeaProjects/Library-Management-System-JAVA/target/classes/images/wibu.jpg', '1', 'UET', NULL, 'file:/C:/Users/PC/IdeaProjects/Library-Management-System-JAVA/target/classes/images/bg_img.jpg', 5),
(4, 'bcd', '23020161@vnu.edu.vn', '$2a$10$RwDTRjHzsrFeHbIoA2Ep4./l4RBJ3JJSYYMUf1NAQi7IMvpskoE/C', 'user', 0, NULL, '1', NULL, NULL, NULL, 5),
(999, 'admin', 'levanc', '$2a$10$PqasxhM1PCwf5fEEWnruKOO29tI6A4ZAaxSRQ/KC/9uYteNnVhlJG', 'admin', 0, NULL, '1', NULL, NULL, NULL, 5);


 DROP TABLE IF EXISTS `comment`;
 SET @saved_cs_client     = @@character_set_client;
 SET character_set_client = utf8;
 CREATE TABLE `comment` (
     `stt` INT AUTO_INCREMENT NOT NULL,
     `book_title`  VARCHAR(500),
     `username` VARCHAR(200),
     `time`  DATETIME,
     `content`  TEXT,
     `rate`  INT,
     PRIMARY KEY (`stt`),
     FOREIGN KEY (`book_title`) REFERENCES `book`(`title`) ON DELETE CASCADE,
     FOREIGN KEY (`username`) REFERENCES `registration`(`username`) ON DELETE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `comment` (book_title, username, time, content,rate)
VALUES
('Attack on Titan', 'nguyenvana@gmail.com', '2024-11-15 10:00:00', 'Cuốn sách này rất thú vị, cốt truyện hấp dẫn!',5),
('Attack on Titan', 'tranthib@gmail.com', '2024-11-14 10:00:00', 'Nhân vật được xây dựng rất tốt, rất thích câu chuyện này.',5);

INSERT INTO `comment` (book_title, username, time, content,rate)
VALUES
('Atomic Habits', 'nguyenvana@gmail.com', '2024-11-13 10:00:00', 'Cuốn sách hữu ích cho việc thay đổi thói quen.',5),
('Atomic Habits', 'tranthib@gmail.com', '2024-11-12 10:00:00', 'Những nguyên tắc trong sách dễ áp dụng vào cuộc sống.',3);

INSERT INTO `comment` (book_title, username, time, content, rate)
VALUES
('Naruto', 'nguyenvana@gmail.com', '2024-11-25 10:00:00', 'Một câu chuyện cảm động và đầy cảm hứng về tình bạn.', 5),
('Naruto', 'tranthib@gmail.com', '2024-11-26 08:30:00', 'Tôi đã học được rất nhiều từ tinh thần không bỏ cuộc của Naruto.', 4),
('Dracula', 'nguyenvana@gmail.com', '2024-11-25 15:20:00', 'Một tác phẩm kinh điển với bầu không khí ma mị và cuốn hút.', 5),
('Dracula', 'tranthib@gmail.com', '2024-11-26 09:45:00', 'Tôi rất thích cách tác giả xây dựng hình ảnh Dracula đầy bí ẩn.', 4),
('Dracula', 'nguyenvana@gmail.com', '2024-11-24 15:20:00', 'hay', 3),
('Dracula', 'tranthib@gmail.com', '2024-09-26 09:45:00', 'd hay', 1),
('Dracula', '23020158@vnu.edu.vn', '2023-11-25 15:20:00', 'HAYYYYY', 5),
('Dracula', '23020161@vnu.edu.vn', '2021-11-26 09:45:00', 'orz', 4),
('Dracula', 'nguyenvana@gmail.com', '2024-11-27 08:00:00', 'Một câu chuyện hấp dẫn và đậm chất cổ điển.', 5),
('Dracula', 'tranthib@gmail.com', '2024-11-20 14:30:00', 'Bầu không khí u ám và ly kỳ khiến tôi không thể rời mắt.', 4),
('Dracula', '23020158@vnu.edu.vn', '2024-11-15 10:45:00', 'Tác phẩm rất đáng đọc, tuy nhiên hơi dài.', 4),
('Dracula', '23020161@vnu.edu.vn', '2024-10-22 09:30:00', 'Hình tượng Dracula được xây dựng rất sinh động.', 5),
('Dracula', 'nguyenvana@gmail.com', '2024-09-18 16:00:00', 'Phần mở đầu khá chậm nhưng càng đọc càng cuốn hút.', 3);

CREATE TABLE daily_logins (
    id INT AUTO_INCREMENT NOT NULL,
    date DATE NOT NULL,
    day_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') NOT NULL,
    login_count INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO daily_logins (date, day_of_week, login_count)
VALUES
('2024-11-20', 'Wednesday', 20),
('2024-11-21', 'Thursday', 10),
('2024-11-22', 'Friday', 5),
('2024-11-23', 'Saturday', 10),
('2024-11-24', 'Sunday', 20),
('2024-11-25', 'Monday', 20),
('2024-11-26', 'Tuesday', 15);




/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
INSERT INTO author (name) VALUES
("ALL"),
('Stephen Hawking'),
('Richard Dawkins'),
('Masashi Kishimoto'),
('Eiichiro Oda'),
('Hajime Isayama'),
('Kohei Horikoshi'),
('Various'),
('Bram Stoker'),
('Mary Shelley'),
('Stephen King'),
('Eckhart Tolle'),
('James Clear');



DROP TABLE IF EXISTS ` publisher`;
CREATE TABLE publisher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
INSERT INTO publisher (name) VALUES
("ALL"),
('Bantam'),
('Oxford University Press'),
('Shueisha'),
('Kodansha'),
('Avery'),
('Doubleday'),
('Viking'),
('New World Library'),
('Springer Nature'),
('Meredith Corporation'),
('Condé Nast'),
('National Geographic Partners'),
('Time USA'),
('Forbes Media'),
('The Economist Group'),
('Pearson');


DROP TABLE IF EXISTS ` category`;

CREATE TABLE `category` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    cnt INT DEFAULT '0'
);
INSERT INTO category (name,cnt) VALUES
("ALL",20),
('Science',2),
('Manga',4),
('Magazine',10),
('Horror',4),
('Self-Help',2);


DROP TABLE IF EXISTS `notifications`;

CREATE TABLE `notifications` (
  `id` INT AUTO_INCREMENT NOT NULL,
  `sender_id` INT NOT NULL,
  `receiver_id` INT DEFAULT NULL,
  `message` TEXT NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `borrow_history` (`id`, `user_id`, `book_name`, `borrow_date`, `return_date`) VALUES
(1, 1, 'Naruto', '2024-11-24 20:26:02', '2024-11-24 20:26:42'),
(2, 2, 'Dracula', '2024-11-24 20:30:10', '2024-11-24 20:40:10'),
(3, 3, 'Time', '2024-11-24 21:00:00', '2024-11-24 21:20:00'),
(4, 1, 'Forbes', '2024-11-24 20:45:30', '2024-11-24 21:05:30'),
(5, 2, 'Naruto', '2024-11-24 22:10:00', '2024-11-24 22:30:00'),
(6, 3, 'Dracula', '2024-11-24 22:30:00', '2024-11-24 22:50:00'),
(7, 1, 'Time', '2024-11-24 23:00:00', '2024-11-24 23:20:00'),
(8, 2, 'Forbes', '2024-11-24 23:10:30', '2024-11-24 23:30:30'),
(9, 3, 'Naruto', '2024-11-24 23:40:00', '2024-11-24 23:55:00'),
(10, 1, 'Dracula', '2024-11-25 00:00:00', '2024-11-25 00:30:00');


INSERT INTO `borrow_request` (`id`, `user_id`, `book_name`, `borrow_date`, `return_date`, `accepted`) VALUES
(1, 1, 'Naruto', '2024-11-24 20:26:02', '2024-12-04 20:26:02', 1),
(2, 2, 'Forbes', '2024-11-24 20:41:03', '2024-12-04 20:41:03', 1),
(3, 3, 'Time', '2024-11-24 20:50:10', '2024-12-04 20:50:10', 1),
(4, 1, 'Forbes', '2024-11-24 21:00:00', '2024-12-04 21:00:00', 0),
(5, 2, 'Time', '2024-11-24 21:30:00', '2024-12-04 21:30:00', 0),
(6, 3, 'Dracula', '2024-11-24 22:00:00', '2024-12-04 22:00:00', 1),
(7, 1, 'Dracula', '2024-11-24 22:30:30', '2024-12-04 22:30:30', 1),
(8, 2, 'Naruto', '2024-11-24 23:00:00', '2024-12-04 23:00:00', 0),
(9, 3, 'Naruto', '2024-11-24 23:30:00', '2024-12-04 23:30:00', 1),
(10, 1, 'Forbes', '2024-11-25 00:00:00', '2024-12-05 00:00:00', 0);


INSERT INTO `borrow_slip` (`id`, `user_id`, `book_name`, `borrow_date`, `return_date`) VALUES
(1, 1, 'Naruto', '2024-11-24 20:26:02', '2024-12-04 20:26:02'),
(2, 2, 'Forbes', '2024-11-24 20:35:30', '2024-12-04 20:35:30'),
(3, 3, 'Dracula', '2024-11-24 21:00:00', '2024-12-04 21:00:00'),
(4, 1, 'Time', '2024-11-24 21:30:00', '2024-12-04 21:30:00'),
(5, 2, 'Naruto', '2024-11-24 22:00:00', '2024-12-04 22:00:00'),
(6, 3, 'Forbes', '2024-11-24 22:30:00', '2024-12-04 22:30:00'),
(7, 1, 'Dracula', '2024-11-24 23:00:00', '2024-12-04 23:00:00'),
(8, 2, 'Time', '2024-11-24 23:15:00', '2024-12-04 23:15:00'),
(9, 3, 'Forbes', '2024-11-24 23:40:00', '2024-12-04 23:40:00'),
(10, 1, 'Dracula', '2024-11-25 00:10:00', '2024-12-05 00:10:00');


CREATE TABLE password_reset_token (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);