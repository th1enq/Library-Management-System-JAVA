-- MySQL dump 10.11
--
-- Host: localhost    Database: librarymanagement
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
--
-- Table structure for table `author`
--
DROP DATABASE IF EXISTS TESTT;
CREATE DATABASE TESTT;
USE TESTT;
DROP TABLE IF EXISTS `author`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `author` (
  `id` int(10) NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;
--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'	Jared Diamond'),(2,'Andrew Hunt'),(3,'Brian Greene'),(4,'Charles Darwin'),(5,'Donald Knuth'),(6,'Douglas Hofstadter'),(7,'Edward Gibbon'),(8,'Erich Gamma'),(9,'G. H. Hardy'),(10,'George Polya'),(11,'Harm J. de Blij'),(12,'James Watson'),(13,'Jerry P. King'),(14,'Martin Fowler'),(15,'Peter Frankopan'),(16,'Philip J. Davis'),(17,'Rand McNally'),(18,'Richard Dawkins'),(19,'Robert Cecil Martin'),(20,'Stephen Hawking'),(21,'Steve McConnell'),(22,'Thomas S. Kuhn'),(23,'Timothy Gowers '),(24,'Yuval Noah Harari');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--
DROP TABLE IF EXISTS `borrow_slip`;
CREATE TABLE  `borrow_slip`(
    slip_id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE NOT NULL,
    PRIMARY KEY(`slip_id`)
);
DROP TABLE IF EXISTS `book`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `book` (
  `bookid` varchar(100) NOT NULL,
  `title` varchar(100) default NULL,
  `author` varchar(100) default NULL,
  `subject` varchar(100) default NULL,
  `publisher` varchar(100) default NULL,
  `category` varchar(100) default NULL,
  PRIMARY KEY  (`bookid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO book (bookid, title, author, subject, publisher, category) VALUES
('00000001', 'To Kill a Mockingbird', 'Harper Lee', 'Fiction', 'J.B. Lippincott & Co.', 'Classic'),
('00000002', '1984', 'George Orwell', 'Dystopian', 'Secker & Warburg', 'Science Fiction'),
('00000003', 'Pride and Prejudice', 'Jane Austen', 'Romance', 'T. Egerton', 'Romance'),
('00000004', 'Moby-Dick', 'Herman Melville', 'Adventure', 'Harper & Brothers', 'Adventure'),
('00000005', 'The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', 'Charles Scribner\'s Sons', 'Classic'),
('00000006', 'The Call of the Wild', 'Jack London', 'Adventure', 'Macmillan', 'Adventure'),
('00000007', 'Brave New World', 'Aldous Huxley', 'Dystopian', 'Chatto & Windus', 'Dystopian'),
('00000008', 'War and Peace', 'Leo Tolstoy', 'Historical Fiction', 'The Russian Messenger', 'Historical Fiction'),
('00000009', 'The Hobbit', 'J.R.R. Tolkien', 'Fantasy', 'George Allen & Unwin', 'Fantasy'),
('00000010', 'The Odyssey', 'Homer', 'Epic', 'Ancient Greece', 'Epic'),
('00000011', 'Crime and Punishment', 'Fyodor Dostoevsky', 'Psychological', 'The Russian Messenger', 'Psychological'),
('00000012', 'Jane Eyre', 'Charlotte Brontë', 'Romance', 'Smith, Elder & Co.', 'Romance'),
('00000013', 'The Divine Comedy', 'Dante Alighieri', 'Epic', 'Italy', 'Epic'),
('00000014', 'Frankenstein', 'Mary Shelley', 'Horror', 'Lackington, Hughes, Harding, Mavor & Jones', 'Horror'),
('00000015', 'The Brothers Karamazov', 'Fyodor Dostoevsky', 'Philosophical', 'The Russian Messenger', 'Philosophical'),
('00000016', 'The Count of Monte Cristo', 'Alexandre Dumas', 'Adventure', 'Pétion', 'Adventure'),
('00000017', 'Les Misérables', 'Victor Hugo', 'Historical Fiction', 'A. Lacroix, Verboeckhoven & Cie', 'Historical Fiction'),
('00000018', 'The Picture of Dorian Gray', 'Oscar Wilde', 'Philosophical', 'Ward, Lock & Co.', 'Philosophical'),
('00000019', 'The Adventures of Huckleberry Finn', 'Mark Twain', 'Adventure', 'Charles L. Webster And Company', 'Adventure'),
('00000020', 'Dracula', 'Bram Stoker', 'Horror', 'Archibald Constable and Company', 'Horror'),
('00000021', 'Don Quixote', 'Miguel de Cervantes', 'Adventure', 'Francisco de Robles', 'Adventure'),
('00000022', 'The Old Man and the Sea', 'Ernest Hemingway', 'Adventure', 'Charles Scribner\'s Sons', 'Adventure'),
('00000023', 'One Hundred Years of Solitude', 'Gabriel García Márquez', 'Magical Realism', 'Editorial Sudamericana', 'Magical Realism'),
('00000024', 'The Metamorphosis', 'Franz Kafka', 'Psychological', 'Kurt Wolff Verlag', 'Psychological'),
('00000025', 'The Iliad', 'Homer', 'Epic', 'Ancient Greece', 'Epic'),
('00000026', 'The Trial', 'Franz Kafka', 'Philosophical', 'Verlag Die Schmiede', 'Philosophical'),
('00000027', 'Fahrenheit 451', 'Ray Bradbury', 'Dystopian', 'Ballantine Books', 'Dystopian'),
('00000028', 'The Road', 'Cormac McCarthy', 'Post-apocalyptic', 'Alfred A. Knopf', 'Post-apocalyptic'),
('00000029', 'The Stranger', 'Albert Camus', 'Philosophical', 'Librairie Gallimard', 'Philosophical'),
('00000030', 'The Grapes of Wrath', 'John Steinbeck', 'Historical Fiction', 'The Viking Press-James Lloyd', 'Historical Fiction');



/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `category` (
  `id` int(10) NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO category (name) VALUES
('Classic'),
('Dystopian'),
('Romance'),
('Adventure'),
('Classic'),
('Adventure'),
('Dystopian'),
('Historical Fiction'),
('Fantasy'),
('Epic'),
('Psychological'),
('Romance'),
('Epic'),
('Horror'),
('Philosophical'),
('Adventure'),
('Historical Fiction'),
('Philosophical'),
('Adventure'),
('Horror'),
('Adventure'),
('Adventure'),
('Magical Realism'),
('Psychological'),
('Epic'),
('Philosophical'),
('Dystopian'),
('Post-apocalyptic'),
('Philosophical'),
('Historical Fiction');

/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issuebooks`
--

DROP TABLE IF EXISTS `issuebooks`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `issuebooks` (
  `username` varchar(50) default NULL,
  `bookId` int(100) NOT NULL,
  `title` varchar(100) default NULL,
  `author` varchar(100) default NULL,
  `issueDate` varchar(20) default NULL,
  `dueDate` varchar(20) default NULL,
  `returnStatus` varchar(10) default NULL,
  PRIMARY KEY  (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;
--
-- Dumping data for table `issuebooks`
--

LOCK TABLES `issuebooks` WRITE;
/*!40000 ALTER TABLE `issuebooks` DISABLE KEYS */;
INSERT INTO issuebooks (username, bookId, title, author, issueDate, dueDate, returnStatus) VALUES
('user1', 1, 'To Kill a Mockingbird', 'Harper Lee', '2024-10-01', '2024-10-15', 'Pending'),
('user2', 2, '1984', 'George Orwell', '2024-10-02', '2024-10-16', 'Pending'),
('user3', 3, 'Pride and Prejudice', 'Jane Austen', '2024-10-03', '2024-10-17', 'Pending'),
('user4', 4, 'Moby-Dick', 'Herman Melville', '2024-10-04', '2024-10-18', 'Returned'),
('user5', 5, 'The Great Gatsby', 'F. Scott Fitzgerald', '2024-10-05', '2024-10-19', 'Pending');

/*!40000 ALTER TABLE `issuebooks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `notice` (
  `id` int(10) NOT NULL auto_increment,
  `notice` varchar(500) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,'We\'re thrilled to welcome you to our Library Management System! With our easy-to-use platform and comprehensive search capabilities, you\'ll be able to find exactly what you need in no time.');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `publisher` (
  `id` int(10) NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO publisher (name) VALUES
('J.B. Lippincott & Co.'),
('Secker & Warburg'),
('T. Egerton'),
('Harper & Brothers'),
('Charles Scribner\'s Sons'),
('Macmillan'),
('Chatto & Windus'),
('The Russian Messenger'),
('George Allen & Unwin'),
('Ancient Greece'),
('The Russian Messenger'),
('Smith, Elder & Co.'),
('Italy'),
('Lackington, Hughes, Harding, Mavor & Jones'),
('The Russian Messenger'),
('Pétion'),
('A. Lacroix, Verboeckhoven & Cie'),
('Ward, Lock & Co.'),
('Charles L. Webster And Company'),
('Archibald Constable and Company'),
('Francisco de Robles'),
('Charles Scribner\'s Sons'),
('Editorial Sudamericana'),
('Kurt Wolff Verlag'),
('Ancient Greece'),
('Verlag Die Schmiede'),
('Ballantine Books'),
('Alfred A. Knopf'),
('Librairie Gallimard'),
('The Viking Press-James Lloyd');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `registration` (
  `id` INT AUTO_INCREMENT NOT NULL,
  `name` varchar(100) default NULL,
  `email` varchar(200) default NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) default NULL,
  `usertype` varchar(10) default NULL,
  PRIMARY KEY  (`id`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;




--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO registration (id, name, email, username, password, usertype) VALUES
('1', 'Nguyen Van A', 'nguyenvana@gmail.com', 'nguyenvana', 'password123', 'user'),
('2', 'Tran Thi B', 'tranthib@gmail.com', 'tranthib', 'password456', 'user'),
('3', 'Le Van C', 'levanc@gmail.com', 'levanc', 'password789', 'admin'),
('4', 'Pham Thi D', 'phamthid@gmail.com', 'phamthid', 'password101', 'user'),
('5', 'Vu Van E', 'vuvane@gmail.com', 'vuvane', 'password112', 'user');
INSERT INTO registration (id, name, email, username, password, usertype) VALUES
('6', 'Nguyen Thi F', 'nguyenthif@gmail.com', 'nguyenthif', 'adminPassword123', 'admin');
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `subject` (
  `id` int(10) NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO subject (name) VALUES
('Fiction'),
('Dystopian'),
('Romance'),
('Adventure'),
('Fiction'),
('Adventure'),
('Dystopian'),
('Historical Fiction'),
('Fantasy'),
('Epic'),
('Psychological'),
('Romance'),
('Epic'),
('Horror'),
('Philosophical'),
('Adventure'),
('Historical Fiction'),
('Philosophical'),
('Adventure'),
('Horror'),
('Adventure'),
('Adventure'),
('Magical Realism'),
('Psychological'),
('Epic'),
('Philosophical'),
('Dystopian'),
('Post-apocalyptic'),
('Philosophical'),
('Historical Fiction');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;
