CREATE TABLE IF NOT EXISTS USER(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `email` varchar(255) DEFAULT NULL,
     `age` varchar(255) DEFAULT NULL,
      `name` varchar(255) DEFAULT NULL,
       `password` varchar(255) DEFAULT NULL
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS BOOK(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `isbnNumber` varchar(255) DEFAULT NULL,
    `title` varchar(255) DEFAULT NULL,
    `author` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS BORROW(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `book_Id` varchar(255) DEFAULT NULL,
    `user_Id` varchar(255) DEFAULT NULL,
    `status` varchar(255) DEFAULT NULL,
    `borrowedDate` DATE DEFAULT NULL,
    `returnedDate` DATE DEFAULT NULL,
    `response` varchar(255),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;