-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 01, 2025 at 05:20 PM
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
-- Table structure for table `accomodationcategory`
--

CREATE TABLE `accomodationcategory` (
  `SNo` int(2) NOT NULL,
  `acategory` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accomodationcategory`
--

INSERT INTO `accomodationcategory` (`SNo`, `acategory`, `Status`, `Comment`, `Note`) VALUES
(1, 'Home-PG', 1, '.', '.'),
(2, 'Hotel', 1, '.', '.'),
(3, 'Temporary Tents', 1, '.', '.'),
(4, 'School', 1, '.', '.'),
(5, 'Dharmshala', 1, '.', '.'),
(6, 'Guest House', 1, '.', '.'),
(7, 'Lodge', 1, '.', '.'),
(8, 'College', 1, '.', '.'),
(9, 'Temple', 1, '.', '.'),
(10, 'Gurudawaras', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `accomodationtype`
--

CREATE TABLE `accomodationtype` (
  `SNo` int(1) NOT NULL,
  `AType` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accomodationtype`
--

INSERT INTO `accomodationtype` (`SNo`, `AType`, `Status`, `Comment`, `Note`) VALUES
(1, 'Private', 0, '', ''),
(2, 'Public', 0, '', ''),
(3, 'Commercial', 0, '', '');

-- --------------------------------------------------------

--
-- Table structure for table `checkpointlocation`
--

CREATE TABLE `checkpointlocation` (
  `SNo` int(3) NOT NULL,
  `CName` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `circle`
--

CREATE TABLE `circle` (
  `SNo` int(2) NOT NULL,
  `CNo` varchar(20) NOT NULL,
  `Status` int(30) NOT NULL,
  `Comment` int(30) NOT NULL,
  `Note` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `citylocaltransport`
--

CREATE TABLE `citylocaltransport` (
  `SNo` int(2) DEFAULT NULL,
  `VType` varchar(30) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `commercialvehicletype`
--

CREATE TABLE `commercialvehicletype` (
  `SNo` int(2) DEFAULT NULL,
  `VType` varchar(40) DEFAULT NULL,
  `Status` int(1) DEFAULT 1,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `commercialvehicletype`
--

INSERT INTO `commercialvehicletype` (`SNo`, `VType`, `Status`, `Comment`, `Note`) VALUES
(1, 'Taxi', 1, '.', '.'),
(2, 'Bus', 1, '.', '.'),
(3, 'Auto', 1, '.', '.'),
(4, 'Thela-goods', 1, '.', '.'),
(5, 'Thela-passanger', 1, '.', '.'),
(6, 'Rikshaw', 1, '.', '.'),
(7, 'E-RiKshaw', 1, '.', '.'),
(8, 'GoodsVehicle', 1, '.', '.'),
(9, 'Cycleric-Goods', 1, '.', '.'),
(10, 'Cycle-Passenger', 1, '.', '.'),
(11, 'Bike', 1, '.', '.'),
(12, 'Ambulance', 1, '.', '.'),
(13, 'Police', 1, '.', '.'),
(14, 'Fire Fighter', 1, '.', '.'),
(15, 'Nagarnigam-Watersupply', 1, '.', '.'),
(16, 'Nagarnigm-Garbagecollection', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `empdesignation`
--

CREATE TABLE `empdesignation` (
  `Sno` int(5) DEFAULT NULL,
  `Designation` varchar(30) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `empposting`
--

CREATE TABLE `empposting` (
  `Sno` int(3) NOT NULL,
  `Pcheckpoint` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `groupregtype`
--

CREATE TABLE `groupregtype` (
  `SNo` int(1) DEFAULT NULL,
  `GroupType` varchar(20) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `groupregtype`
--

INSERT INTO `groupregtype` (`SNo`, `GroupType`, `Status`, `Comment`, `Note`) VALUES
(1, 'Family', 1, '.', '.'),
(2, 'Friends', 1, '.', '.'),
(3, 'Individual', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `gzservicereg`
--

CREATE TABLE `gzservicereg` (
  `SNo` int(3) NOT NULL,
  `ServiceID` varchar(30) NOT NULL,
  `ShopName` varchar(50) NOT NULL,
  `SType` varchar(50) NOT NULL,
  `SNature` varchar(50) NOT NULL,
  `SCapacity` int(5) NOT NULL,
  `StaffNo.` int(4) NOT NULL,
  `OperationCircle` varchar(10) NOT NULL,
  `OperationZone` varchar(10) NOT NULL,
  `Shift` varchar(10) NOT NULL,
  `OwnerName` varchar(50) NOT NULL,
  `OwnerID` varchar(50) NOT NULL,
  `OwnerMob` varchar(15) NOT NULL,
  `OwnerAddress` varchar(50) NOT NULL,
  `Lattitude` varchar(50) NOT NULL,
  `Longitude` varchar(50) NOT NULL,
  `OEmergencyMob` varchar(15) NOT NULL,
  `EmergencyName` varchar(50) NOT NULL,
  `EmergencyMob` varchar(15) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(50) NOT NULL,
  `Note` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gzservicetype`
--

CREATE TABLE `gzservicetype` (
  `SNo` int(2) NOT NULL,
  `SType` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gzservicetype`
--

INSERT INTO `gzservicetype` (`SNo`, `SType`, `Status`, `Comment`, `Note`) VALUES
(1, 'Cloth', 1, '.', '.'),
(2, 'Food', 1, '.', '.'),
(3, 'Pooja Item', 1, '.', '.'),
(4, 'Utility', 1, '.', '.'),
(5, 'General Item', 1, '.', '.'),
(6, 'Fastfood', 1, '.', '.'),
(7, 'Friuts', 1, '.', '.'),
(8, 'Drink', 1, '.', '.'),
(9, 'Pandal', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `gzvehicletype`
--

CREATE TABLE `gzvehicletype` (
  `SNo` int(2) NOT NULL,
  `VType` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gzvehicletype`
--

INSERT INTO `gzvehicletype` (`SNo`, `VType`, `Status`, `Comment`, `Note`) VALUES
(1, 'Taxi', 0, '', ''),
(2, 'Bus', 0, '', ''),
(3, 'Thela', 0, '', ''),
(4, 'Auto', 0, '', ''),
(5, 'RickShaw', 0, '', ''),
(6, 'E-rickShaw', 0, '', '');

-- --------------------------------------------------------

--
-- Table structure for table `itemtype`
--

CREATE TABLE `itemtype` (
  `Sno` int(7) NOT NULL,
  `Itype` varchar(30) NOT NULL,
  `ItemID` varchar(30) NOT NULL,
  `Status` varchar(30) NOT NULL,
  `Comment` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `locationtype`
--

CREATE TABLE `locationtype` (
  `SNo` int(10) DEFAULT NULL,
  `LocType` varchar(50) DEFAULT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parkingregistration`
--

CREATE TABLE `parkingregistration` (
  `SNo` int(9) NOT NULL,
  `ParkingID` varchar(10) NOT NULL,
  `PType` varchar(20) NOT NULL,
  `Permitted Vehicle` varchar(50) NOT NULL,
  `ParkingZone` varchar(2) NOT NULL,
  `ParkingCircle` varchar(2) NOT NULL,
  `PlotNo` varchar(50) NOT NULL,
  `Street` varchar(50) NOT NULL,
  `Landmark` varchar(50) NOT NULL,
  `District` varchar(50) NOT NULL,
  `Block` varchar(50) NOT NULL,
  `Lattitude` varchar(50) NOT NULL,
  `Longitude` varchar(50) NOT NULL,
  `Image1` varchar(50) NOT NULL,
  `Image2` varchar(50) NOT NULL,
  `POwnerName` varchar(30) NOT NULL,
  `POwneraddress` varchar(50) NOT NULL,
  `POwnerMobileNo` varchar(15) NOT NULL,
  `NearestPoliceStation` varchar(50) NOT NULL,
  `NearestHospital1` varchar(50) NOT NULL,
  `NearestHospital2` varchar(50) NOT NULL,
  `ParkingCapacity` varchar(3) NOT NULL,
  `CheckPoint1` varchar(50) NOT NULL,
  `RegisteredMobileNo.` varchar(15) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parkingtype`
--

CREATE TABLE `parkingtype` (
  `SNo` int(2) NOT NULL,
  `PType` varchar(50) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parkingtype`
--

INSERT INTO `parkingtype` (`SNo`, `PType`, `Status`, `Comment`, `Note`) VALUES
(1, 'Private', 1, '.', '.'),
(2, 'Public', 1, '.', '.'),
(3, 'Individual', 1, '.', '.'),
(4, 'School/college', 1, '.', '.'),
(5, 'Parking Stand', 1, '.', '.'),
(6, 'Commercial Parking', 1, '.', '.'),
(7, 'Home', 1, '.', '.'),
(8, 'Rooftop Parking', 1, '.', '.'),
(9, 'Electric Parking', 1, '.', '.'),
(10, 'Air Taxi Stand', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `permittedvehicle`
--

CREATE TABLE `permittedvehicle` (
  `SNo` int(2) DEFAULT NULL,
  `PType` varchar(20) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `personalregtype`
--

CREATE TABLE `personalregtype` (
  `SNo` int(2) NOT NULL,
  `PRegType` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `personalregtype`
--

INSERT INTO `personalregtype` (`SNo`, `PRegType`, `Status`, `Comment`, `Note`) VALUES
(1, 'General', 1, '.', '.'),
(2, 'VIP', 1, '.', '.'),
(3, 'Special Permission', 1, '.', '.'),
(4, 'Caretaker', 1, '.', '.'),
(5, 'Seva Dhari', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `personalvehicletype`
--

CREATE TABLE `personalvehicletype` (
  `SNo` int(2) DEFAULT NULL,
  `VCategory` varchar(50) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `regfrom`
--

CREATE TABLE `regfrom` (
  `SNo` int(1) NOT NULL,
  `RFrom` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `regfrom`
--

INSERT INTO `regfrom` (`SNo`, `RFrom`, `Status`, `Comment`, `Note`) VALUES
(1, 'Individual', 1, '.', '.'),
(2, 'Panchayat', 1, '.', '.'),
(3, 'Online', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `registrationpartner`
--

CREATE TABLE `registrationpartner` (
  `SNo` int(7) NOT NULL,
  `PName` varchar(5) NOT NULL,
  `GovID` varchar(50) NOT NULL,
  `ShopNo` varchar(30) NOT NULL,
  `Colony` varchar(50) NOT NULL,
  `CityORVillage` varchar(50) NOT NULL,
  `Block` varchar(50) NOT NULL,
  `Tehsil` varchar(50) NOT NULL,
  `Pincode` varchar(12) NOT NULL,
  `District` varchar(50) NOT NULL,
  `State` varchar(50) NOT NULL,
  `Landmark` varchar(50) NOT NULL,
  `Mob1` varchar(15) NOT NULL,
  `Mob2` varchar(15) NOT NULL,
  `BankName` varchar(50) NOT NULL,
  `IFSCcode` varchar(15) NOT NULL,
  `AccountNo` varchar(50) NOT NULL,
  `UPIID` varchar(50) NOT NULL,
  `QRcode` blob NOT NULL,
  `EMailID` varchar(50) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `servicenature`
--

CREATE TABLE `servicenature` (
  `SNo` int(11) NOT NULL,
  `SNature` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `softwaretype`
--

CREATE TABLE `softwaretype` (
  `SNo` int(2) NOT NULL,
  `SofType` varchar(20) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `softwaretype`
--

INSERT INTO `softwaretype` (`SNo`, `SofType`, `Status`, `Comment`, `Note`) VALUES
(1, 'Registration', 1, '.', '.'),
(2, 'CheckIn', 1, '.', '.'),
(3, 'CheckOut', 1, '.', '.'),
(4, 'Control', 1, '.', '.'),
(5, 'Monitoring', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `zone`
--

CREATE TABLE `zone` (
  `SNo` int(2) NOT NULL,
  `ZNo` varchar(10) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` int(30) NOT NULL,
  `Note` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
