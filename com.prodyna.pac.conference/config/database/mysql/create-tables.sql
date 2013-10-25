-- --------------------------------------------------------
--
-- MySQL DDL Script (without data)
--
-- Server Version:               5.1.49-community-log - MySQL Community Server (GPL)
-- Author:                       Nicolas Moser
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- -------------------
-- Conference DDL   --
-- -------------------

CREATE TABLE IF NOT EXISTS `conference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `endDate` date NOT NULL,
  `location` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `startDate` date NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- -------------------
-- Room DDL         --
-- -------------------

CREATE TABLE IF NOT EXISTS `room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `capacity` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  `conference_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK26F4FB1953212F` (`conference_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- -------------------
-- Speaker DDL      --
-- -------------------

CREATE TABLE IF NOT EXISTS `speaker` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `name` varchar(50) NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- -------------------
-- Talk DDL         --
-- -------------------

CREATE TABLE IF NOT EXISTS `talk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `duration` int(11) NOT NULL,
  `endDate` datetime NOT NULL,
  `name` varchar(50) NOT NULL,
  `startDate` datetime NOT NULL,
  `version` bigint(20) NOT NULL,
  `conference_id` bigint(20) NOT NULL,
  `room_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK27A8CC3118308F` (`room_id`),
  KEY `FK27A8CC1953212F` (`conference_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- -------------------
-- TalkSpeaker DDL  --
-- -------------------

CREATE TABLE IF NOT EXISTS `talkspeaker` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `speaker_id` bigint(20) NOT NULL,
  `talk_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4E5C119377461485` (`speaker_id`),
  KEY `FK4E5C119382D652EF` (`talk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
