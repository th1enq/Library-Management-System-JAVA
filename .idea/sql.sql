
DROP DATABASE IF EXISTS TESTT;
CREATE DATABASE TESTT;
USE TESTT;



DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
    title VARCHAR(255) PRIMARY KEY,
    authors VARCHAR(255),
    publisher VARCHAR(255),
    publishedDate DATE,
    thumbnail VARCHAR(255),
    ISBN VARCHAR(13),
    description TEXT,
    numPage VARCHAR(30),
    category VARCHAR(100),
    price VARCHAR(30),
    language VARCHAR(50),
    buyLink VARCHAR(255),
    avail VARCHAR(50) NOT NULL DEFAULT "YES",
    rating INT NOT NULL DEFAULT 0
);
INSERT INTO book (title, authors, publisher, publishedDate, thumbnail, ISBN, description, numPage, category, price, language, buyLink, avail, rating) VALUES
-- Science (2 books)
('A Brief History of Time', 'Stephen Hawking', 'Bantam', '1988-04-01', 'thumb1.jpg', '9780553380163', 'A landmark volume in science writing by one of the great minds of our time', '212', 'Science', '18.99', 'English', 'link1.com', 'YES', 5),
('The Selfish Gene', 'Richard Dawkins', 'Oxford University Press', '1976-08-30', 'thumb2.jpg', '9780192860927', 'A classic exposition of evolutionary biology', '360', 'Science', '15.99', 'English', 'link2.com', 'YES', 5),

-- Manga (4 books)
('Naruto', 'Masashi Kishimoto', 'Shueisha', '1999-09-21', 'thumb3.jpg', '9781569319000', 'A young ninja who seeks recognition from his peers', '220', 'Manga', '9.99', 'Japanese', 'link3.com', 'YES', 5),
('One Piece', 'Eiichiro Oda', 'Shueisha', '1997-07-22', 'thumb4.jpg', '9784088725093', 'The story of a young pirate and his adventures', '230', 'Manga', '9.99', 'Japanese', 'link4.com', 'YES', 5),
('Attack on Titan', 'Hajime Isayama', 'Kodansha', '2009-09-09', 'thumb5.jpg', '9781612620244', 'Humanity fights against giant humanoid Titans', '192', 'Manga', '10.99', 'Japanese', 'link5.com', 'YES', 4),
('My Hero Academia', 'Kohei Horikoshi', 'Shueisha', '2014-07-07', 'thumb6.jpg', '9781421582696', 'A boy born without superpowers in a world where they are common', '240', 'Manga', '10.99', 'Japanese', 'link6.com', 'YES', 5),

-- Magazine (10 books)
('National Geographic', 'Various', 'National Geographic Partners', '2023-01-01', 'thumb7.jpg', '9781426217777', 'Exploring the world and all its beauty', '100', 'Magazine', '5.99', 'English', 'link7.com', 'YES', 5),
('Time', 'Various', 'Time USA', '2023-02-01', 'thumb8.jpg', '9781618932648', 'Covers world news, politics, and culture', '100', 'Magazine', '6.99', 'English', 'link8.com', 'YES', 5),
('Forbes', 'Various', 'Forbes Media', '2023-03-01', 'thumb9.jpg', '9781618932649', 'Leading source for reliable business news', '90', 'Magazine', '6.99', 'English', 'link9.com', 'YES', 5),
('The Economist', 'Various', 'The Economist Group', '2023-04-01', 'thumb10.jpg', '9781618932650', 'Delivers current affairs and international business news', '120', 'Magazine', '7.99', 'English', 'link10.com', 'YES', 5),
('Scientific American', 'Various', 'Springer Nature', '2023-05-01', 'thumb11.jpg', '9781618932651', 'Popular science magazine', '110', 'Magazine', '7.99', 'English', 'link11.com', 'YES', 4),
('People', 'Various', 'Meredith Corporation', '2023-06-01', 'thumb12.jpg', '9781618932652', 'Celebrity and human-interest stories', '95', 'Magazine', '5.99', 'English', 'link12.com', 'YES', 3),
('Vogue', 'Various', 'Condé Nast', '2023-07-01', 'thumb13.jpg', '9781618932653', 'Fashion, beauty, and lifestyle magazine', '100', 'Magazine', '6.99', 'English', 'link13.com', 'YES', 5),
('New Scientist', 'Various', 'New Scientist Ltd.', '2023-08-01', 'thumb14.jpg', '9781618932654', 'Science and technology news', '110', 'Magazine', '8.99', 'English', 'link14.com', 'YES', 4),
-- Horror (4 books)
('Dracula', 'Bram Stoker', 'Archibald Constable and Company', '1897-05-26', 'thumb17.jpg', '9781435159570', 'The story of the famous vampire', '418', 'Horror', '12.99', 'English', 'link17.com', 'YES', 5),
('Frankenstein', 'Mary Shelley', 'Lackington, Hughes, Harding, Mavor & Jones', '1818-01-01', 'thumb18.jpg', '9780486282114', 'The story of Victor Frankenstein and his monstrous creation', '280', 'Horror', '9.99', 'English', 'link18.com', 'YES', 5),
('The Shining', 'Stephen King', 'Doubleday', '1977-01-28', 'thumb19.jpg', '9780385121675', 'A family isolated in a haunted hotel', '447', 'Horror', '10.99', 'English', 'link19.com', 'YES', 5),
('It', 'Stephen King', 'Viking', '1986-09-15', 'thumb20.jpg', '9780450411434', 'A group of kids facing a monster', '1138', 'Horror', '14.99', 'English', 'link20.com', 'YES', 5),
-- Self-Help (2 books)
('The Power of Now', 'Eckhart Tolle', 'New World Library', '1997-10-01', 'thumb21.jpg', '9781577314806', 'A guide to spiritual enlightenment and living in the present moment.', '229', 'Self-Help', '14.99', 'English', 'link21.com', 'YES', 5),
('Atomic Habits', 'James Clear', 'Penguin', '2018-10-16', 'thumb22.jpg', '9780735211292', 'A proven way to build good habits and break bad ones.', '320', 'Self-Help', '18.99', 'English', 'link22.com', 'YES', 5);

DROP TABLE IF EXISTS `borrow_slip`;

CREATE TABLE `borrow_slip` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_name VARCHAR(100) NOT NULL,
    borrow_date DATETIME NOT NULL,
    return_date DATETIME NOT NULL
);




DROP TABLE IF EXISTS `registration`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `registration` (
  `id` INT AUTO_INCREMENT NOT NULL,
  `name` VARCHAR(100) DEFAULT NULL,
  `username` VARCHAR(20) NOT NULL UNIQUE,
  `password` VARCHAR(100) NOT NULL,
  `usertype` ENUM('admin', 'user') DEFAULT 'user',
  `is_banned` TINYINT(1) DEFAULT 0,
  `avatar_link` VARCHAR(200),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


  DROP TABLE IF EXISTS `comment`;
  SET @saved_cs_client     = @@character_set_client;
  SET character_set_client = utf8;
  CREATE TABLE `comment` (
     `stt` INT AUTO_INCREMENT NOT NULL,
     `book_title`  VARCHAR(200),
     `username` VARCHAR(200),
     `time`  DATE,
     `content`  TEXT,
     PRIMARY KEY (`stt`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `comment` (book_title, username, time, content)
VALUES
('Attack on Titan', 'nguyenvana', '2024-11-15', 'Cuốn sách này rất thú vị, cốt truyện hấp dẫn!'),
('Attack on Titan', 'tranthib', '2024-11-14', 'Nhân vật được xây dựng rất tốt, rất thích câu chuyện này.');

INSERT INTO `comment` (book_title, username, time, content)
VALUES
('Atomic Habits', 'nguyenvana', '2024-11-13', 'Cuốn sách hữu ích cho việc thay đổi thói quen.'),
('Atomic Habits', 'tranthib', '2024-11-12', 'Những nguyên tắc trong sách dễ áp dụng vào cuộc sống.');








--
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO registration (id, name, username, password, usertype) VALUES
('1', 'Nguyen Van A', 'nguyenvana', 'password123', 'user'),
('2', 'Tran Thi B', 'tranthib', 'password456', 'user'),
('3', 'Pham Thi D', 'phamthid', 'password101', 'user'),
('4', 'Vu Van E', 'vuvane', 'password112', 'user'),
('99998', 'Le Van C', 'levanc',  'password789', 'admin');
INSERT INTO registration (id, name, username, password, usertype) VALUES
('99999', 'Nguyen Thi F', 'nguyenthif', 'adminPassword123', 'admin');
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
    name VARCHAR(255)
);
INSERT INTO category (name) VALUES
("ALL"),
('Science'),
('Manga'),
('Magazine'),
('Horror'),
('Self-Help');


DROP TABLE IF EXISTS `notifications`;

CREATE TABLE `notifications` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

