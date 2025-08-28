-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 07, 2025 at 05:29 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parabitinss`
--

-- --------------------------------------------------------

--
-- Table structure for table `commercialvehreg`
--

CREATE TABLE `commercialvehreg` (
  `Sno` int(9) NOT NULL,
  `OwnerName` varchar(30) NOT NULL,
  `OwnerMob1` varchar(14) NOT NULL,
  `OwnerMob2` varchar(14) NOT NULL,
  `DriverName` varchar(30) NOT NULL,
  `DriverMobNo` varchar(14) NOT NULL,
  `VehicleType` varchar(20) NOT NULL,
  `FuelType` varchar(15) NOT NULL,
  `AutherizedFor` varchar(15) NOT NULL,
  `VehicleCapacity` int(3) NOT NULL,
  `PermitType` varchar(15) NOT NULL,
  `ZoneAllowed` int(1) NOT NULL,
  `PermitValidity` date NOT NULL,
  `Fromcity` varchar(30) NOT NULL,
  `FromState` varchar(30) NOT NULL,
  `ToCity` varchar(30) NOT NULL,
  `ToState` varchar(30) NOT NULL,
  `Entrypoint` varchar(15) NOT NULL,
  `ExitPoint` varchar(15) NOT NULL,
  `TripCount` int(4) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
