DROP DATABASE IF EXISTS bank; 
CREATE DATABASE bank DEFAULT CHARACTER SET utf8;

USE bank;

DROP TABLE IF EXISTS transactions; 
DROP TABLE IF EXISTS accounts; 
DROP TABLE IF EXISTS clients; 

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

INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('FFF', '123.45', '2023-01-31', 1);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('dsdfs', '678.90', '2022-02-26', 1);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('sdfg', '345.67', '2021-03-15', 2);