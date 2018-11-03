CREATE TABLE `properties` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) NULL UNIQUE,
  `property` VARCHAR(5000) NULL
);
CREATE TABLE `destinations` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) NOT NULL,
  `created` DATETIME NOT NULL,
  `modified` DATETIME NULL,
  `deleted` DATETIME NULL
);
CREATE TABLE `order` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `fid` VARCHAR(20) NOT NULL,
  `destination_start` INTEGER NOT NULL,
  `destination_end` INTEGER NOT NULL,
  `ts_start` DATETIME NULL,
  `ts_end` DATETIME NULL,
  `mode` VARCHAR(20) NOT NULL,
  `isolation` BOOL,
  `emergency` BOOL,
  `message` VARCHAR(500),
  `planed` DATETIME NULL,
  `accepted` DATETIME NULL,
  `pickedup` DATETIME NULL,
  `delivered` DATETIME NULL,
  `carrier` VARCHAR(30) NULL,
  `planed_carrier` VARCHAR(30) NULL,
  `creator` VARCHAR(30) NULL,
  `created` DATETIME NOT NULL,
  `modified` DATETIME NULL,
  `deleted` DATETIME NULL
);
CREATE TABLE `carrier` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) NOT NULL,
  `heartbeat` DATETIME NOT NULL,
  `created` DATETIME NOT NULL,
  `modified` DATETIME NULL,
  `deleted` DATETIME NULL
);
CREATE TABLE `users` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `employeenumber` VARCHAR(20),
  `title` VARCHAR(10),
  `firstname` VARCHAR(20),
  `lastname` VARCHAR(20),
  `sex` CHAR(1),
  `email` VARCHAR(50),
  `username` VARCHAR(45) NOT NULL UNIQUE,
  `password` VARCHAR(32) NULL
);
CREATE TABLE `roles` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL
);
CREATE TABLE `location` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(45) NOT NULL,
  `varia` JSON NULL,
  `created` DATETIME NOT NULL,
  `modified` DATETIME NULL,
  `deleted` DATETIME NULL
);