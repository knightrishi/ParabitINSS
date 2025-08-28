-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 14, 2025 at 07:44 AM
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
-- Table structure for table `accomodationreg`
--

CREATE TABLE `accomodationreg` (
  `SNo` int(5) NOT NULL,
  `AccID` varchar(10) NOT NULL,
  `AccTypeFK` int(1) NOT NULL,
  `AcategoryFK` int(2) NOT NULL,
  `OwnerAddhar` int(15) NOT NULL,
  `OwnerName` varchar(30) NOT NULL,
  `RegMobNo1` varchar(15) NOT NULL,
  `RegMobNo2` varchar(15) NOT NULL,
  `CareTakerAddhar` int(15) NOT NULL,
  `CareTakerName` varchar(30) NOT NULL,
  `CareTakerMob1` varchar(15) NOT NULL,
  `CareTakerMob2` varchar(15) NOT NULL,
  `AccName` varchar(30) NOT NULL,
  `PlotNo` int(5) NOT NULL,
  `Street` varchar(30) NOT NULL,
  `Colony` varchar(30) NOT NULL,
  `Landmark` varchar(30) NOT NULL,
  `City` varchar(30) NOT NULL,
  `District` varchar(30) NOT NULL,
  `Block` varchar(30) NOT NULL,
  `Lattitude` varchar(15) NOT NULL,
  `Longitude` varchar(15) NOT NULL,
  `Pincode` int(7) NOT NULL,
  `image1` blob NOT NULL,
  `image2` blob NOT NULL,
  `image3` blob NOT NULL,
  `image4` blob NOT NULL,
  `image5` blob NOT NULL,
  `ZoneFK` int(2) NOT NULL,
  `CircleFK` int(2) NOT NULL,
  `DistanceFromGZ` int(3) NOT NULL,
  `Capacity` int(3) NOT NULL,
  `ChargeType` varchar(1) NOT NULL,
  `Charge` int(4) NOT NULL,
  `Status` int(1) NOT NULL,
  `Note` varchar(30) NOT NULL,
  `Comment` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `CpNo` int(5) NOT NULL,
  `CpName` varchar(30) NOT NULL,
  `CpGroup` varchar(1) NOT NULL,
  `Circle` varchar(10) NOT NULL,
  `Zone` varchar(10) NOT NULL,
  `Type` varchar(3) NOT NULL,
  `Lattitude` varchar(30) NOT NULL,
  `Longitude` varchar(30) NOT NULL,
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
  `CNo` varchar(3) NOT NULL,
  `Status` int(30) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
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
  `VehicleTypeFK` int(2) NOT NULL,
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

--
-- Dumping data for table `empdesignation`
--

INSERT INTO `empdesignation` (`Sno`, `Designation`, `Status`, `Comment`, `Note`) VALUES
(1, 'Toll-Checkers', 1, '.', '.'),
(2, 'Zone Operators', 1, '.', '.'),
(3, 'Railway Checker', 1, '.', '.'),
(4, 'Circle-Checker', 1, '.', '.'),
(5, 'Circle-Operator', 1, '.', '.'),
(6, 'Railway Operator', 1, '.', '.'),
(7, 'Toll Operator', 1, '.', '.'),
(8, 'Marine Operator', 1, '.', '.'),
(9, 'Zone Supervisor', 1, '.', '.'),
(10, 'Circle-supervisor', 1, '.', '.');

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
-- Table structure for table `empreg`
--

CREATE TABLE `empreg` (
  `SNo` int(4) NOT NULL,
  `EmpID` varchar(10) NOT NULL,
  `EmpName` varchar(30) NOT NULL,
  `EmpMob1` varchar(15) NOT NULL,
  `EmpMob2` varchar(15) NOT NULL,
  `Gender` varchar(7) NOT NULL,
  `AadharNo` int(15) NOT NULL,
  `PostFK` int(2) NOT NULL,
  `EmpDesignationFK` int(2) NOT NULL,
  `WorkProfile` varchar(20) NOT NULL,
  `BossID` varchar(10) NOT NULL,
  `ReportingID` varchar(10) NOT NULL,
  `Shift` int(4) NOT NULL,
  `ItemIDFK` int(4) NOT NULL,
  `ItemtypeFK` int(2) NOT NULL,
  `EmpLogin` varchar(30) NOT NULL,
  `EmpPassword` varchar(10) NOT NULL,
  `EmpPin` int(6) NOT NULL,
  `OTP` int(6) NOT NULL,
  `EmpCategory` varchar(1) NOT NULL,
  `EmpImage` blob NOT NULL,
  `ZoneFK` int(2) NOT NULL,
  `CircleFK` int(2) NOT NULL,
  `CheckpointFK` int(4) NOT NULL,
  `TotalWorkingHours` int(4) NOT NULL,
  `EmpAge` int(2) NOT NULL,
  `EmpJoinDate` date NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `faltucitylocaltransport`
--

CREATE TABLE `faltucitylocaltransport` (
  `SNo` int(2) DEFAULT NULL,
  `VType` varchar(30) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `fueltype`
--

CREATE TABLE `fueltype` (
  `SNo` int(1) DEFAULT NULL,
  `FType` varchar(30) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
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
  `STypeFK` varchar(50) NOT NULL,
  `SNatureFK` varchar(50) NOT NULL,
  `SCapacity` int(5) NOT NULL,
  `NoOfStaff` int(4) NOT NULL,
  `CircleNo` varchar(10) NOT NULL,
  `ZoneNo` varchar(10) NOT NULL,
  `Shift` int(4) NOT NULL,
  `OwnerName` varchar(50) NOT NULL,
  `IDProof` varchar(40) NOT NULL,
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
-- Table structure for table `gztransportreg`
--

CREATE TABLE `gztransportreg` (
  `SNo` int(7) NOT NULL,
  `TransportID` varchar(17) NOT NULL,
  `VNo` varchar(11) NOT NULL,
  `VRegNo` varchar(20) NOT NULL,
  `VTypeFK` int(2) NOT NULL,
  `VCapacity` int(2) NOT NULL,
  `OwnerName` varchar(30) NOT NULL,
  `OwnerMob` varchar(15) NOT NULL,
  `DriverName` varchar(30) NOT NULL,
  `DriverMob` varchar(15) NOT NULL,
  `DrivedBy` varchar(1) NOT NULL,
  `OperationRoute` varchar(30) NOT NULL,
  `CircleFK` varchar(30) NOT NULL,
  `ZoneFK` varchar(30) NOT NULL,
  `Shift` int(4) NOT NULL,
  `HouseNo` varchar(10) NOT NULL,
  `Colony` varchar(30) NOT NULL,
  `TownCity` varchar(30) NOT NULL,
  `Tehsil` varchar(30) NOT NULL,
  `Block` varchar(30) NOT NULL,
  `District` varchar(30) NOT NULL,
  `StateFK` int(2) NOT NULL,
  `Pincode` int(12) NOT NULL,
  `LandMark` varchar(30) NOT NULL,
  `OwnerAddharNo` int(15) NOT NULL,
  `Driving License` varchar(16) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gzvehicletype`
--

CREATE TABLE `gzvehicletype` (
  `SNo` int(2) NOT NULL,
  `VType` varchar(30) NOT NULL,
  `UseIn` varchar(3) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gzvehicletype`
--

INSERT INTO `gzvehicletype` (`SNo`, `VType`, `UseIn`, `Status`, `Comment`, `Note`) VALUES
(1, 'Taxi', '', 1, '.', '.'),
(2, 'Bus', '', 1, '.', '.'),
(3, 'Auto', '', 1, '.', '.'),
(4, 'Thela-goods', '', 1, '.', '.'),
(5, 'Thela-passanger', '', 1, '.', '.'),
(6, 'Rikshaw', '', 1, '.', '.'),
(7, 'E-RiKshaw', '', 1, '.', '.'),
(8, 'GoodsVehicle', '', 1, '.', '.'),
(9, 'Cycleric-Goods', '', 1, '.', '.'),
(10, 'Cycle-Passenger', '', 1, '.', '.'),
(11, 'Bike', '', 1, '.', '.'),
(12, 'Ambulance', '', 1, '.', '.'),
(13, 'Police', '', 1, '.', '.'),
(14, 'Fire Fighter', '', 1, '.', '.'),
(15, 'Nagarnigam-Watersupply', '', 1, '.', '.'),
(16, 'Nagarnigm-Garbagecollection', '', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `itemreg`
--

CREATE TABLE `itemreg` (
  `SNo` int(3) DEFAULT NULL,
  `ItemId` varchar(36) NOT NULL,
  `ItemTypeFK` int(1) DEFAULT NULL,
  `EmpIdFK` int(4) NOT NULL,
  `CPFK` int(3) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `itemtype`
--

CREATE TABLE `itemtype` (
  `SNo` int(2) DEFAULT NULL,
  `ItemName` varchar(30) NOT NULL,
  `Type` varchar(30) DEFAULT NULL,
  `ItemBrand` varchar(30) NOT NULL,
  `Speciality` varchar(50) NOT NULL,
  `ItemRange` varchar(30) NOT NULL,
  `Purpose` varchar(50) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `locationtype`
--

CREATE TABLE `locationtype` (
  `SNo` int(2) DEFAULT NULL,
  `LocType` varchar(50) DEFAULT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `locationtype`
--

INSERT INTO `locationtype` (`SNo`, `LocType`, `Status`, `Comment`, `Note`) VALUES
(1, 'StateHighwayToll', 1, '.', '.'),
(2, 'NationalHighway', 1, '.', '.'),
(3, 'Railwayreservation', 1, '.', '.'),
(4, 'RaiwayStationGroundZero', 1, '.', '.'),
(5, 'RailwayStationNearBy', 1, '.', '.'),
(6, 'Airports', 1, '.', '.'),
(7, 'GhatsRegistration', 1, '.', '.'),
(8, 'Parking', 1, '.', '.'),
(9, 'CircleLocation', 1, '.', '.'),
(10, 'Circle-ControlRoom', 1, '.', '.'),
(11, 'ZoneLocation ', 1, '.', '.'),
(12, 'Zone-ControlRoom', 1, '.', '.'),
(13, 'ControlRoom', 1, '.', '.'),
(14, 'ControlRoom-PoliceStation', 1, '.', '.'),
(15, 'ControlRoom-Emergency', 1, '.', '.'),
(16, 'LiveMonitoringRoom', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `parkingregistration`
--

CREATE TABLE `parkingregistration` (
  `SNo` int(9) NOT NULL,
  `ParkingID` varchar(10) NOT NULL,
  `PTypeFK` varchar(20) NOT NULL,
  `Permitted VehicleFK` varchar(50) NOT NULL,
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
-- Table structure for table `parkingvehtype`
--

CREATE TABLE `parkingvehtype` (
  `SNo` int(2) DEFAULT NULL,
  `VType` varchar(20) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(30) DEFAULT NULL,
  `Note` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

--
-- Dumping data for table `permittedvehicle`
--

INSERT INTO `permittedvehicle` (`SNo`, `PType`, `Status`, `Comment`, `Note`) VALUES
(1, 'TwoWheeler', 1, '.', '.'),
(2, 'FourWheeler', 1, '.', '.'),
(3, 'Van', 1, '.', '.'),
(4, 'MiniBus', 1, '.', '.'),
(5, 'Bus', 1, '.', '.'),
(6, 'ValvoBus', 1, '.', '.'),
(7, 'Drone', 1, '.', '.'),
(8, 'AirTaxi', 1, '.', '.'),
(9, 'Helicopter', 1, '.', '.'),
(10, 'Tractor', 1, '.', '.');

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
-- Table structure for table `personalvehreg`
--

CREATE TABLE `personalvehreg` (
  `SNo` int(7) NOT NULL,
  `VRegNo` varchar(20) NOT NULL,
  `VNo` varchar(11) NOT NULL,
  `VCategoryFK` int(2) NOT NULL,
  `VName` varchar(30) NOT NULL,
  `RTORegNo` varchar(15) NOT NULL,
  `OwnerName` varchar(30) NOT NULL,
  `OwnerMob` varchar(12) NOT NULL,
  `EmergencyName` varchar(30) NOT NULL,
  `EmergencyMob` varchar(15) NOT NULL,
  `DriverName` varchar(30) NOT NULL,
  `DriverMob` varchar(15) NOT NULL,
  `DrivedBy` varchar(1) NOT NULL,
  `VIP` int(1) NOT NULL,
  `FuelTypeFK` int(1) NOT NULL,
  `FromCity` varchar(30) NOT NULL,
  `FromStateFK` int(2) NOT NULL,
  `ToCity` varchar(30) NOT NULL,
  `ToStateFK` int(2) NOT NULL,
  `ArrivalDate` varchar(10) NOT NULL,
  `ReturnDate` varchar(10) NOT NULL,
  `ApproxDay` int(2) NOT NULL,
  `OtherDest` varchar(100) NOT NULL,
  `NoOfPassenger` int(2) NOT NULL,
  `CpInFK` int(4) NOT NULL,
  `CPOutFK` int(4) NOT NULL,
  `Vimg` blob NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `personalvehtype`
--

CREATE TABLE `personalvehtype` (
  `Sno` int(2) DEFAULT NULL,
  `VecType` tinytext DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` tinytext DEFAULT NULL,
  `Note` tinytext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `personalvehtype`
--

INSERT INTO `personalvehtype` (`Sno`, `VecType`, `Status`, `Comment`, `Note`) VALUES
(1, 'HatchBack', 1, '.', '.'),
(2, 'Sedan', 1, '.', '.'),
(3, 'SUV', 1, '.', '.'),
(4, 'Compact SUV', 1, '.', '.'),
(5, 'MUV', 1, '.', '.'),
(6, 'Van', 1, '.', '.'),
(7, 'PickUp Truck', 1, '.', '.'),
(8, 'Boat', 1, '.', '.'),
(9, 'Cruise', 1, '.', '.'),
(10, 'Mini Cruise', 1, '.', '.'),
(11, 'Bus', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `personlog`
--

CREATE TABLE `personlog` (
  `SNo` bigint(9) NOT NULL,
  `TrackID` varchar(10) NOT NULL,
  `PersonOrVehicleIDFK` bigint(9) NOT NULL,
  `AccIDFK` int(5) NOT NULL,
  `ParkingIDFK` bigint(8) NOT NULL,
  `GroupID` int(6) NOT NULL,
  `Roadway Transport` int(1) NOT NULL,
  `Railway Transport` int(1) NOT NULL,
  `Airway Transport` int(1) NOT NULL,
  `Waterway Transport` int(1) NOT NULL,
  `Entry Date` date NOT NULL,
  `Entry Time` time NOT NULL,
  `Exit Date` date NOT NULL,
  `Exit Time` time NOT NULL,
  `ZoneFK` int(2) NOT NULL,
  `CircleNo and Date and Time` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `personreg`
--

CREATE TABLE `personreg` (
  `SNo` bigint(8) NOT NULL,
  `PersonID` varchar(17) NOT NULL,
  `MobNo` varchar(15) NOT NULL,
  `GroupMobNo` varchar(15) NOT NULL,
  `GroupFK` int(1) NOT NULL,
  `Gender` varchar(5) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Age` int(3) NOT NULL,
  `Nationality` varchar(20) NOT NULL,
  `EmergencyMob` varchar(15) NOT NULL,
  `RegTypeFK` int(1) NOT NULL,
  `RegFromFK` int(1) NOT NULL,
  `TransportNo` varchar(11) NOT NULL,
  `ATypeFK` int(2) NOT NULL,
  `HealthIssues` varchar(30) NOT NULL,
  `HouseNo` varchar(10) NOT NULL,
  `Colony` varchar(30) NOT NULL,
  `TownCity` varchar(30) NOT NULL,
  `Tehsil` varchar(30) NOT NULL,
  `Block` varchar(30) NOT NULL,
  `District` varchar(30) NOT NULL,
  `StateFK` int(2) NOT NULL,
  `Pincode` int(12) NOT NULL,
  `Landmark` varchar(30) NOT NULL,
  `PoliceStation` varchar(30) NOT NULL,
  `AadharNo` int(15) NOT NULL,
  `PanchyatNo` varchar(20) NOT NULL,
  `IdentificationMark` varchar(30) NOT NULL,
  `RegDate` date NOT NULL,
  `TravellingDate` date NOT NULL,
  `ReturnDate` date NOT NULL,
  `ReturnType` varchar(100) NOT NULL,
  `TotalDays` int(2) NOT NULL,
  `CpInFK` int(4) NOT NULL,
  `CpOutFK` int(4) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `railwaystation`
--

CREATE TABLE `railwaystation` (
  `Sno` int(3) DEFAULT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `ShortName` varchar(10) DEFAULT NULL,
  `StationCode` int(8) DEFAULT NULL,
  `StationSecurityno` varchar(15) DEFAULT NULL,
  `StationMasterNo` varchar(15) DEFAULT NULL,
  `StationPhoneNo` varchar(15) DEFAULT NULL,
  `EmpId` varchar(20) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(50) DEFAULT NULL,
  `Note` varchar(50) DEFAULT NULL
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
  `SNo` int(1) NOT NULL,
  `SNature` varchar(30) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `servicenature`
--

INSERT INTO `servicenature` (`SNo`, `SNature`, `Status`, `Comment`, `Note`) VALUES
(1, 'Temporary', 1, '.', '.'),
(2, 'Fix', 1, '.', '.'),
(3, 'Movable', 1, '.', '.'),
(4, 'Permanent', 1, '.', '.');

-- --------------------------------------------------------

--
-- Table structure for table `softwarergestration`
--

CREATE TABLE `softwarergestration` (
  `sno` int(4) DEFAULT NULL,
  `LocationId` int(5) DEFAULT NULL,
  `LocationName` varchar(50) DEFAULT NULL,
  `Locationtype` varchar(50) DEFAULT NULL,
  `SoftwareType` varchar(50) DEFAULT NULL,
  `ZoneFK` int(2) DEFAULT NULL,
  `CircleFK` int(2) DEFAULT NULL,
  `TotalEmployee` int(2) DEFAULT NULL,
  `EmployeeCheckInNo` int(4) DEFAULT NULL,
  `EmployeeCheckOutNo` int(4) DEFAULT NULL,
  `Controlers` varchar(50) DEFAULT NULL,
  `Volenteers` varchar(50) DEFAULT NULL,
  `EmpNo` int(5) DEFAULT NULL,
  `locationSupervisorId` varchar(10) DEFAULT NULL,
  `locationSupName` varchar(50) DEFAULT NULL,
  `ReportingLocId` varchar(20) DEFAULT NULL,
  `ReportEmpID` varchar(20) DEFAULT NULL,
  `LocLandmark` varchar(50) DEFAULT NULL,
  `Lattitude` varchar(20) DEFAULT NULL,
  `Longitude` varchar(20) DEFAULT NULL,
  `LocationImage1` blob DEFAULT NULL,
  `LocationImage2` blob DEFAULT NULL,
  `LocationImage3` blob DEFAULT NULL,
  `LocationImage4` blob DEFAULT NULL,
  `LocationImage5` blob DEFAULT NULL,
  `DistanceFromGZ` int(2) DEFAULT NULL,
  `DistanceFromRailways` int(2) DEFAULT NULL,
  `DistanceFromMetro` int(2) DEFAULT NULL,
  `DistancefromAIR` int(3) DEFAULT NULL,
  `Nearestpolicestat` varchar(70) DEFAULT NULL,
  `NearesrHospital` varchar(70) DEFAULT NULL,
  `DiversionLOCIDDistance` varchar(30) DEFAULT NULL
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
-- Table structure for table `stddef`
--

CREATE TABLE `stddef` (
  `SNo` int(2) NOT NULL,
  `CircleFK` int(2) NOT NULL,
  `ZoneFk` int(2) NOT NULL,
  `Approx AreaIn` int(4) NOT NULL,
  `Approx AreaOut` int(4) NOT NULL,
  `W1MaxCap` int(7) NOT NULL,
  `W2MaxCap` int(7) NOT NULL,
  `W3MaxCap` int(7) NOT NULL,
  `W1TotalCap` int(7) NOT NULL,
  `W2TotalCap` int(7) NOT NULL,
  `W3TotalCap` int(7) NOT NULL,
  `W1TotalGreenCap` int(7) NOT NULL,
  `W1TotalYellowCap` int(7) NOT NULL,
  `W1TotalRedCap` int(7) NOT NULL,
  `W2TotalGreenCap` int(7) NOT NULL,
  `W2TotalYellowCap` int(7) NOT NULL,
  `W2TotalRedCap` int(7) NOT NULL,
  `W3TotalGreenCap` int(7) NOT NULL,
  `W3TotalYellowCap` int(7) NOT NULL,
  `W3TotalRedCap` int(7) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `traintable`
--

CREATE TABLE `traintable` (
  `Sno` int(9) DEFAULT NULL,
  `TrainName` varchar(50) DEFAULT NULL,
  `TrainNumber` int(7) DEFAULT NULL,
  `TrainMove` varchar(10) DEFAULT NULL,
  `StopName` varchar(50) DEFAULT NULL,
  `Category` varchar(30) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `Comment` varchar(50) DEFAULT NULL,
  `Note` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `weatherdensity`
--

CREATE TABLE `weatherdensity` (
  `W1` int(1) NOT NULL,
  `W2` int(1) NOT NULL,
  `W3` int(1) NOT NULL,
  `ResDensity` float NOT NULL,
  `W1Max` float NOT NULL,
  `W1Green` float NOT NULL,
  `W1Yellow` float NOT NULL,
  `W1Red` float NOT NULL,
  `W2Max` float NOT NULL,
  `W2Green` float NOT NULL,
  `W2Yellow` float NOT NULL,
  `W2Red` float NOT NULL,
  `W3Max` float NOT NULL,
  `W3Green` float NOT NULL,
  `W3Yellow` float NOT NULL,
  `W3Red` float NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `zone`
--

CREATE TABLE `zone` (
  `SNo` int(2) NOT NULL,
  `ZNo` varchar(3) NOT NULL,
  `Status` int(1) NOT NULL,
  `Comment` varchar(30) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
