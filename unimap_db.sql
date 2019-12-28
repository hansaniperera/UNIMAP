-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema unimap_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema unimap_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `unimap_db` DEFAULT CHARACTER SET utf8 ;
USE `unimap_db` ;

-- -----------------------------------------------------
-- Table `unimap_db`.`batches`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unimap_db`.`batches` (
  `batchId` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`batchId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unimap_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unimap_db`.`users` (
  `userId` INT(11) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unimap_db`.`cordinators`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unimap_db`.`cordinators` (
  `cordinatorId` INT(11) NOT NULL,
  `userId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`cordinatorId`),
  INDEX `fk_cordinators_users_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_cordinators_users`
    FOREIGN KEY (`userId`)
    REFERENCES `unimap_db`.`users` (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unimap_db`.`students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unimap_db`.`students` (
  `studentId` INT(11) NOT NULL,
  `fname` VARCHAR(45) NULL DEFAULT NULL,
  `mname` VARCHAR(45) NULL DEFAULT NULL,
  `lname` VARCHAR(45) NULL DEFAULT NULL,
  `dob` DATE NULL DEFAULT NULL,
  `address` VARCHAR(45) NULL DEFAULT NULL,
  `telephone` INT(10) NULL DEFAULT NULL,
  `image` BLOB NULL DEFAULT NULL,
  `userId` INT(11) NULL DEFAULT NULL,
  `batchId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`studentId`),
  INDEX `fk_students_users1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_students_batches1_idx` (`batchId` ASC) VISIBLE,
  CONSTRAINT `fk_students_batches1`
    FOREIGN KEY (`batchId`)
    REFERENCES `unimap_db`.`batches` (`batchId`),
  CONSTRAINT `fk_students_users1`
    FOREIGN KEY (`userId`)
    REFERENCES `unimap_db`.`users` (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unimap_db`.`results`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unimap_db`.`results` (
  `resultId` INT(11) NOT NULL,
  `result` VARCHAR(45) NULL DEFAULT NULL,
  `studentId` INT(11) NULL DEFAULT NULL,
  `batchId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`resultId`),
  INDEX `fk_results_students1_idx` (`studentId` ASC) INVISIBLE,
  CONSTRAINT `studentId`
    FOREIGN KEY (`studentId`)
    REFERENCES `unimap_db`.`students` (`studentId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unimap_db`.`courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unimap_db`.`courses` (
  `courseId` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `resId` INT(11) NULL DEFAULT NULL,
  `studentId` INT(11) NULL DEFAULT NULL,
  `batchId` INT(11) NULL DEFAULT NULL,
  `year` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`courseId`),
  INDEX `fk_courses_results1_idx` (`resId` ASC) VISIBLE,
  CONSTRAINT `fk_courses_results1`
    FOREIGN KEY (`resId`)
    REFERENCES `unimap_db`.`results` (`resultId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unimap_db`.`lecturers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unimap_db`.`lecturers` (
  `lecturerId` INT(11) NOT NULL,
  `fname` VARCHAR(45) NULL DEFAULT NULL,
  `lname` VARCHAR(45) NULL DEFAULT NULL,
  `userId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`lecturerId`),
  INDEX `fk_lecturers_users1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_lecturers_users1`
    FOREIGN KEY (`userId`)
    REFERENCES `unimap_db`.`users` (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unimap_db`.`lecturers_has_courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unimap_db`.`lecturers_has_courses` (
  `lecturerId` INT(11) NOT NULL,
  `courseId` INT(11) NOT NULL,
  PRIMARY KEY (`lecturerId`, `courseId`),
  INDEX `fk_lecturers_has_courses_courses1_idx` (`courseId` ASC) VISIBLE,
  INDEX `fk_lecturers_has_courses_lecturers1_idx` (`lecturerId` ASC) VISIBLE,
  CONSTRAINT `fk_lecturers_has_courses_courses1`
    FOREIGN KEY (`courseId`)
    REFERENCES `unimap_db`.`courses` (`courseId`),
  CONSTRAINT `fk_lecturers_has_courses_lecturers1`
    FOREIGN KEY (`lecturerId`)
    REFERENCES `unimap_db`.`lecturers` (`lecturerId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unimap_db`.`students_has_courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unimap_db`.`students_has_courses` (
  `studentId` INT(11) NOT NULL,
  `courseId` INT(11) NOT NULL,
  PRIMARY KEY (`studentId`, `courseId`),
  INDEX `fk_students_has_courses_courses1_idx` (`courseId` ASC) VISIBLE,
  INDEX `fk_students_has_courses_students1_idx` (`studentId` ASC) VISIBLE,
  CONSTRAINT `fk_students_has_courses_courses1`
    FOREIGN KEY (`courseId`)
    REFERENCES `unimap_db`.`courses` (`courseId`),
  CONSTRAINT `fk_students_has_courses_students1`
    FOREIGN KEY (`studentId`)
    REFERENCES `unimap_db`.`students` (`studentId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
