-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 11, 2016 at 02:59 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `asthmatrackerdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `aap`
--

CREATE TABLE `aap` (
  `tempID` int(11) NOT NULL,
  `uNameFK` varchar(30) NOT NULL,
  `mildMed` varchar(50) DEFAULT NULL,
  `mildAmt` int(20) DEFAULT NULL,
  `mildFreq` int(20) DEFAULT NULL,
  `modMed` varchar(50) DEFAULT NULL,
  `modAmt` int(20) DEFAULT NULL,
  `modFreq` int(20) DEFAULT NULL,
  `sevMed` varchar(50) DEFAULT NULL,
  `sevAmt` int(20) DEFAULT NULL,
  `sevFreq` int(20) DEFAULT NULL,
  `drName` varchar(50) DEFAULT NULL,
  `drPhone` varchar(12) DEFAULT NULL,
  `drCity` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `userID` int(11) NOT NULL,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `userName` varchar(30) NOT NULL,
  `password` text NOT NULL,
  `birthDate` varchar(10) NOT NULL,
  `fullName` varchar(50) NOT NULL,
  `relation` varchar(20) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `clicktracker`
--

CREATE TABLE `clicktracker` (
  `tempID` int(11) NOT NULL,
  `userNameFK` varchar(45) DEFAULT NULL,
  `clicks` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `aap`
--
ALTER TABLE `aap`
  ADD PRIMARY KEY (`tempID`),
  ADD KEY `uNameFK` (`uNameFK`);

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`userID`,`userName`),
  ADD UNIQUE KEY `userName` (`userName`);

--
-- Indexes for table `clicktracker`
--
ALTER TABLE `clicktracker`
  ADD PRIMARY KEY (`tempID`),
  ADD KEY `userNameTag_idx` (`userNameFK`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `aap`
--
ALTER TABLE `aap`
  MODIFY `tempID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;
--
-- AUTO_INCREMENT for table `clicktracker`
--
ALTER TABLE `clicktracker`
  MODIFY `tempID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `clicktracker`
--
ALTER TABLE `clicktracker`
  ADD CONSTRAINT `userNameTag` FOREIGN KEY (`userNameFK`) REFERENCES `account` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
