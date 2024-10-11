
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
INSERT INTO book ( title, authors, publisher, publishedDate, thumbnail, ISBN, description, numPage, category, price, language, buyLink)
VALUES
('Effective Java', 'Joshua Bloch', 'Addison-Wesley', '2018-01-06', 'https://x...content-available-to-author-only...x.com/effective_java.jpg', '9780134685991', 'A comprehensive guide to best practices in Java programming.', 416, 'Programming', 45.50, 'English', 'https://x...content-available-to-author-only...x.com/buy_effective_java'),
('Clean Code', 'Robert C. Martin', 'Prentice Hall', '2008-08-11', 'https://x...content-available-to-author-only...x.com/clean_code.jpg', '9780132350884', 'A handbook of agile software craftsmanship.', 464, 'Programming', 42.75, 'English', 'https://x...content-available-to-author-only...x.com/buy_clean_code'),
('The Pragmatic Programmer', 'Andy Hunt, Dave Thomas', 'Addison-Wesley', '1999-10-30', 'https://x...content-available-to-author-only...x.com/pragmatic_programmer.jpg', '9780201616224', 'Tips and practices to become a pragmatic programmer.', 352, 'Programming', 39.99, 'English', 'https://x...content-available-to-author-only...x.com/buy_pragmatic_programmer'),
('Head First Java', 'Kathy Sierra, Bert Bates', 'O’Reilly Media', '2005-02-09', 'https://x...content-available-to-author-only...x.com/head_first_java.jpg', '9780596009205', 'A brain-friendly guide to learning Java.', 720, 'Programming', 38.85, 'English', 'https://x...content-available-to-author-only...x.com/buy_head_first_java'),
( 'Java: The Complete Reference', 'Herbert Schildt', 'McGraw-Hill', '2018-04-09', 'https://x...content-available-to-author-only...x.com/java_complete_reference.jpg', '9781260440232', 'Comprehensive reference guide to Java programming.', 1248, 'Programming', 59.99, 'English', 'https://x...content-available-to-author-only...x.com/buy_java_complete_reference'),
( 'Introduction to Algorithms', 'Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein', 'MIT Press', '2009-07-31', 'https://x...content-available-to-author-only...x.com/introduction_algorithms.jpg', '9780262033848', 'The bible of algorithms used in computer science.', 1292, 'Computer Science', 80.99, 'English', 'https://x...content-available-to-author-only...x.com/buy_introduction_algorithms'),
( 'Design Patterns', 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', 'Addison-Wesley', '1994-10-31', 'https://x...content-available-to-author-only...x.com/design_patterns.jpg', '9780201633610', 'Guide to design patterns in software engineering.', 395, 'Software Engineering', 54.95, 'English', 'https://x...content-available-to-author-only...x.com/buy_design_patterns'),
( 'Refactoring: Improving the Design of Existing Code', 'Martin Fowler', 'Addison-Wesley', '1999-07-08', 'https://x...content-available-to-author-only...x.com/refactoring.jpg', '9780201485677', 'Techniques to improve the design of existing code.', 455, 'Software Engineering', 49.99, 'English', 'https://x...content-available-to-author-only...x.com/buy_refactoring'),
( 'Artificial Intelligence: A Modern Approach', 'Stuart Russell, Peter Norvig', 'Pearson', '2020-04-01', 'https://x...content-available-to-author-only...x.com/ai_modern_approach.jpg', '9780134610993', 'Comprehensive guide to artificial intelligence.', 1152, 'Artificial Intelligence', 69.50, 'English', 'https://x...content-available-to-author-only...x.com/buy_ai_modern_approach'),
( 'Cracking the Coding Interview', 'Gayle Laakmann McDowell', 'CareerCup', '2015-07-01', 'https://x...content-available-to-author-only...x.com/cracking_coding_interview.jpg', '9780984782857', '189 programming interview questions and solutions.', 706, 'Career Development', 35.95, 'English', 'https://x...content-available-to-author-only...x.com/buy_cracking_coding_interview');


DROP TABLE IF EXISTS `borrow_slip`;
CREATE TABLE  `borrow_slip`(
    user_id INT NOT NULL,
    book_name varchar(100) NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE NOT NULL
);





DROP TABLE IF EXISTS `registration`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `registration` (
  `id` INT AUTO_INCREMENT NOT NULL,
  `name` varchar(100) default NULL,
  `username` varchar(20) default NULL,
  `password` varchar(100) default NULL,
  `usertype` varchar(10) default NULL,
  PRIMARY KEY  (`id`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;




--
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO registration (id, name, username, password, usertype) VALUES
('1', 'Nguyen Van A', 'nguyenvana@gmail.com', 'password123', 'user'),
('2', 'Tran Thi B', 'tranthib@gmail.com', 'password456', 'user'),
('3', 'Le Van C', 'levanc@gmail.com',  'password789', 'admin'),
('4', 'Pham Thi D', 'phamthid@gmail.com', 'password101', 'user'),
('5', 'Vu Van E', 'vuvane@gmail.com', 'password112', 'user');
INSERT INTO registration (id, name, username, password, usertype) VALUES
('6', 'Nguyen Thi F', 'nguyenthif', 'adminPassword123', 'admin');
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
INSERT INTO `author` (name)
VALUES
('ALL'),
('Joshua Bloch'),
('Robert C. Martin'),
('Andy Hunt, Dave Thomas'),
('Kathy Sierra, Bert Bates'),
('Herbert Schildt'),
('Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein'),
('Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides'),
('Martin Fowler'),
('Stuart Russell, Peter Norvig'),
('Gayle Laakmann McDowell');


DROP TABLE IF EXISTS ` publisher`;
CREATE TABLE publisher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
INSERT INTO `publisher` (name)
VALUES
('ALL'),
('Addison-Wesley'),
('Prentice Hall'),
('Addison-Wesley'),
('O’Reilly Media'),
('McGraw-Hill'),
('MIT Press'),
('Addison-Wesley'),
('Addison-Wesley'),
('Pearson'),
('CareerCup');

DROP TABLE IF EXISTS ` category`;

CREATE TABLE `category` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
INSERT INTO `category` (name)
VALUES
('ALL'),
('Programming'),
('Programming'),
('Programming'),
('Programming'),
('Programming'),
('Computer Science'),
('Software Engineering'),
('Software Engineering'),
('Artificial Intelligence'),
('Career Development');


