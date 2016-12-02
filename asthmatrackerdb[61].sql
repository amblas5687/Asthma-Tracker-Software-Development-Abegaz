CREATE TABLE `userinfo` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `userName` varchar(30) NOT NULL,
  `password` text NOT NULL,
  `birthDate` varchar(10) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `userName_UNIQUE` (`userName`)
) ENGINE=InnoDB;

CREATE TABLE `contactinfo` (
  `tempID` int(11) NOT NULL AUTO_INCREMENT,
  `uNameFK1` varchar(30) NOT NULL,
  `fullName` varchar(50) NOT NULL,
  `relation` varchar(20) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `email` varchar(30) NOT NULL,
  PRIMARY KEY (`tempID`),
  UNIQUE KEY `uNameFK1_UNIQUE` (`uNameFK1`),
  CONSTRAINT `userNameTag1` FOREIGN KEY (`uNameFK1`) REFERENCES `userinfo` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `clicktracker` (
  `tempID` int(11) NOT NULL AUTO_INCREMENT,
  `uNameFK2` varchar(30) DEFAULT NULL,
  `clicks` int(11) DEFAULT NULL,
  PRIMARY KEY (`tempID`),
  UNIQUE KEY `uNameFK2` (`uNameFK2`),
  CONSTRAINT `userNameTag2` FOREIGN KEY (`uNameFK2`) REFERENCES `userinfo` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `mildaap` (
  `tempID` int(11) NOT NULL AUTO_INCREMENT,
  `uNameFK3` varchar(30) NOT NULL,
  `mildMed` varchar(50) DEFAULT NULL,
  `mildAmt` int(20) DEFAULT NULL,
  `mildFreq` int(20) DEFAULT NULL,
  PRIMARY KEY (`tempID`),
  UNIQUE KEY `uNameFK3` (`uNameFK3`),
  CONSTRAINT `userNameTag3` FOREIGN KEY (`uNameFK3`) REFERENCES `userinfo` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `moderateaap` (
  `tempID` int(11) NOT NULL AUTO_INCREMENT,
  `uNameFK4` varchar(30) NOT NULL,
  `modMed` varchar(50) DEFAULT NULL,
  `modAmt` int(20) DEFAULT NULL,
  `modFreq` int(20) DEFAULT NULL,
  PRIMARY KEY (`tempID`),
  UNIQUE KEY `uNameFK4` (`uNameFK4`),
  CONSTRAINT `userNameTag4` FOREIGN KEY (`uNameFK4`) REFERENCES `userinfo` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `severeaap` (
  `tempID` int(11) NOT NULL AUTO_INCREMENT,
  `uNameFK5` varchar(30) NOT NULL,
  `sevMed` varchar(50) DEFAULT NULL,
  `sevAmt` int(20) DEFAULT NULL,
  `sevFreq` int(20) DEFAULT NULL,
  PRIMARY KEY (`tempID`),
  KEY `userNameTag5` (`uNameFK5`),
  CONSTRAINT `userNameTag5` FOREIGN KEY (`uNameFK5`) REFERENCES `userinfo` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `doctorinfo` (
  `tempID` int(11) NOT NULL AUTO_INCREMENT,
  `uNameFK6` varchar(30) NOT NULL,
  `drName` varchar(50) DEFAULT NULL,
  `drPhone` varchar(12) DEFAULT NULL,
  `drCity` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tempID`),
  UNIQUE KEY `uNameFK6` (`uNameFK6`),
  CONSTRAINT `userNameTag6` FOREIGN KEY (`uNameFK6`) REFERENCES `userinfo` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;