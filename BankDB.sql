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
INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Сергей', 'Сергеев', 'sergeev@example.com', '+375291234569');
INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Анна', 'Антонова', 'antonova@example.com', '+375291234570');
INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Мария', 'Маркова', 'markova@example.com', '+375291234571');
INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Дмитрий', 'Дмитриев', 'dmitriev@example.com', '+375291234572');
INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Елена', 'Еленина', 'elenina@example.com', '+375291234573');
INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Алексей', 'Алексеев', 'alekseev@example.com', '+375291234574');
INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Ольга', 'Ольгина', 'olgina@example.com', '+375291234575');
INSERT INTO `clients` (`cl_name`, `cl_surname`, `cl_email`, `cl_phone`) VALUES ('Татьяна', 'Татьянова', 'tatyana@example.com', '+375291234576');

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
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '11223344', '1500.00', 3);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '44332211', '2500.00', 4);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '55667788', '3000.00', 5);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '88776655', '3500.00', 6);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '99887766', '4000.00', 7);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '66554433', '4500.00', 8);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '22334455', '5000.00', 9);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '55443322', '5500.00', 10);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '11112222', '6000.00', 1);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '22221111', '6500.00', 2);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '33334444', '7000.00', 3);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '44443333', '7500.00', 4);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '55556666', '8000.00', 5);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '66665555', '8500.00', 6);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '77778888', '9000.00', 7);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '88887777', '9500.00', 8);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('D', '99990000', '10000.00', 9);
INSERT INTO `accounts` (`ac_type`, `ac_number`, `ac_balance`, `cl_id`) VALUES ('S', '00009999', '10500.00', 10);

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
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '200.00', '2023-02-01', 3);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '500.00', '2023-02-02', 3);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '150.00', '2023-02-03', 4);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '300.00', '2023-02-04', 4);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '400.00', '2023-02-05', 5);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '600.00', '2023-02-06', 5);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '250.00', '2023-02-07', 6);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '700.00', '2023-02-08', 6);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '350.00', '2023-02-09', 7);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '800.00', '2023-02-10', 7);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '450.00', '2023-02-11', 8);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '900.00', '2023-02-12', 8);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '550.00', '2023-02-13', 9);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1000.00', '2023-02-14', 9);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '650.00', '2023-02-15', 10);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1100.00', '2023-02-16', 10);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '750.00', '2023-02-17', 1);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1200.00', '2023-02-18', 2);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '850.00', '2023-02-19', 3);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1300.00', '2023-02-20', 4);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '950.00', '2023-02-21', 5);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1400.00', '2023-02-22', 6);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '1050.00', '2023-02-23', 7);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1500.00', '2023-02-24', 8);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '1150.00', '2023-02-25', 9);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1600.00', '2023-02-26', 10);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '1250.00', '2023-02-27', 1);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1700.00', '2023-02-28', 2);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '1350.00', '2023-03-01', 3);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1800.00', '2023-03-02', 4);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '1450.00', '2023-03-03', 5);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '1900.00', '2023-03-04', 6);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '1550.00', '2023-03-05', 7);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '2000.00', '2023-03-06', 8);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('withdrawal', '1650.00', '2023-03-07', 9);
INSERT INTO `transactions` (`tr_type`, `tr_amount`, `tr_date`, `ac_id`) VALUES ('deposit', '2100.00', '2023-03-08', 10);

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
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client2', '$2a$12$deMZlg9.yUhSJ2hqeYKk8OBvzRNPUTHW0vFCsgnSD6sTcVRGvS56e', 'ROLE_USER', 2);
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client3', '$2a$12$rPHOuomaeCNFk9O3XfF1oejXL2ipUMGhvYlOAS/YmSt/zEP6QOQYW', 'ROLE_USER', 3);
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client4', '$2a$12$CiUf4avanx3TTISINeOZa.ENwPBhRU3yzk7/wYyU5qvxO/hatiI0u', 'ROLE_USER', 4);
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client5', '$2a$12$iBpNcq266sB.IEGM0SUXVu8YsnIE2qWN0Qim0LYuJqM2wbLZVokMu', 'ROLE_USER', 5);
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client6', '$2a$12$3jN2j3N68/n0BIse/hUWq.jJykwUdryP4FjnpruOpth/wt41Z/rvK', 'ROLE_USER', 6);
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client7', '$2a$12$w8hg/fNGVBt1CO0kEAHqKed3AukOItzv1yv0UcM4pku0.cQ/2hlxi', 'ROLE_USER', 7);
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client8', '$2a$12$MrKKrK.1hfLVKGE8CZ/wlul9Toap4QSmJvPgnaMxry0Jx9o1DVRKO', 'ROLE_USER', 8);
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client9', '$2a$12$XHk8WWCn9Qq946ZeA3Nca.y9KxKL2/k4f46QtBEWHxrPZNkUVC9C.', 'ROLE_USER', 9);
INSERT INTO `users` (`username`, `password`, `role`, `cl_id`) VALUES ('client10', '$2a$12$xtM3omSOj6X8j0i8TuHaJu9aaP5D.ke04QcdLgJKRpNhaMDVmJZ0u', 'ROLE_USER', 10);
INSERT INTO `users` (`username`, `password`, `role`) VALUES('superadmin', '$2a$12$viwYLojchkrK3joWvD0McOOo23tJ3rs9rpesCrBgfL3ncrZOmZ3MK', 'ROLE_SUPERADMIN');
INSERT INTO `users` (`username`, `password`, `role`) VALUES('admin', '$2a$12$Ua6Ls0dHTmxfpP63uXPfH.pvbQs2YlZpjLKrSFfcT6D/eqj/TA2QO', 'ROLE_ADMIN');