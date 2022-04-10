-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema pong
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pong
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pong` DEFAULT CHARACTER SET utf8mb4 ;
USE `pong` ;

-- -----------------------------------------------------
-- Table `pong`.`scoreboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pong`.`scoreboard` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `player` CHAR(3) NOT NULL,
  `score` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO pong.scoreboard (player, score)
VALUES ('duc', 69420);

INSERT INTO pong.scoreboard (player, score)
VALUES ('bob', 34);

INSERT INTO pong.scoreboard (player, score)
VALUES ('con', 12);

INSERT INTO pong.scoreboard (player, score)
VALUES ('lia', 6);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;