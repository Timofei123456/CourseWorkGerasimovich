DROP DATABASE IF EXISTS bank; 
CREATE DATABASE bank DEFAULT CHARACTER SET utf8;

USE bank;

DROP TABLE IF EXISTS transactions; 
DROP TABLE IF EXISTS accounts; 
DROP TABLE IF EXISTS clients; 
DROP TABLE IF EXISTS users; 

CREATE TABLE `clients` (
  `cl_id` BIGINT NOT NULL AUTO_INCREMENT,
  `cl_name` VARCHAR(45) NULL,
  `cl_surname` VARCHAR(45) NULL,
  `cl_email` VARCHAR(45) NULL,
  `cl_phone` VARCHAR(45) NULL,
  PRIMARY KEY (`cl_id`)
);

INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Иван', 'Иванов', 'ivanov@example.com', '+375291234567');
INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Петр', 'Петров', 'petrov@example.com', '+375291234568');

CREATE TABLE `accounts` (
  `ac_id` BIGINT NOT NULL AUTO_INCREMENT,
  `ac_type` VARCHAR(45) NULL,
  `ac_number` VARCHAR(45) NULL,
  `ac_balance` DECIMAL(10, 2) NULL,
  `cl_id` BIGINT NOT NULL,
  PRIMARY KEY (`ac_id`),
  CONSTRAINT `ack`
    FOREIGN KEY (`cl_id`)
    REFERENCES `clients` (`cl_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '12345678', '1000.00', 1);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '87654321', '2000.00', 2);

CREATE TABLE `transactions` (
  `tr_id` BIGINT NOT NULL AUTO_INCREMENT,
  `tr_type` VARCHAR(45) NULL,
  `tr_amount` DECIMAL(10, 2) NULL,
  `tr_date` DATE NULL,
  `ac_id` BIGINT NOT NULL,
  PRIMARY KEY (`tr_id`),
  CONSTRAINT `trk`
    FOREIGN KEY (`ac_id`)
    REFERENCES `accounts` (`ac_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '123.45', '2023-01-31', 1);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '678.90', '2022-02-26', 1);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '345.67', '2021-03-15', 2);

CREATE TABLE `users` (
  `us_id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `role` ENUM('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPERADMIN') NOT NULL,
  `cl_id` BIGINT,
  PRIMARY KEY (`us_id`),
  CONSTRAINT `usk`
    FOREIGN KEY (`cl_id`)
    REFERENCES `clients` (`cl_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client1', '$2a$12$hywNaTjlnrvSZSmWK1Ow0ukltmehitcHlwHvPQpOnQpzxnPcGZgg6', 'ROLE_USER', 1);
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES('client2', '$2a$12$deMZlg9.yUhSJ2hqeYKk8OBvzRNPUTHW0vFCsgnSD6sTcVRGvS56e', 'ROLE_USER', 2);
INSERT INTO `users` (`username`, `password`, `role`) VALUES('superadmin', '$2a$12$viwYLojchkrK3joWvD0McOOo23tJ3rs9rpesCrBgfL3ncrZOmZ3MK', 'ROLE_SUPERADMIN');
INSERT INTO `users` (`username`, `password`, `role`) VALUES('admin', '$2a$12$Ua6Ls0dHTmxfpP63uXPfH.pvbQs2YlZpjLKrSFfcT6D/eqj/TA2QO', 'ROLE_ADMIN');