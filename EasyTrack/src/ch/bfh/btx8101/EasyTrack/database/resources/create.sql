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
  `accepted` DATETIME NULL,
  `pickedup` DATETIME NULL,
  `delivered` DATETIME NULL,
  `carrier` VARCHAR(30) NULL,
  `creator` VARCHAR(30) NULL,
  `created` DATETIME NOT NULL,
  `modified` DATETIME NULL,
  `deleted` DATETIME NULL
);