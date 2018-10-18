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