CREATE TABLE customer ( id INT UNSIGNED PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL,
 email VARCHAR(100) UNIQUE NOT NULL, date DATE NOT NULL);