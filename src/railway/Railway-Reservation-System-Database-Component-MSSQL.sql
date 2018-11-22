-- Create Database To Store All Tables
CREATE DATABASE rrs;
USE rrs;

-- Create Table For The administrator Account
CREATE TABLE adminaccount (
    adminID INT UNIQUE,
    adminUserSSN VARCHAR(100) PRIMARY KEY,
    adminFullName VARCHAR(50),
    adminUserPass VARCHAR(100)
);


-- Create Table For Credit Card Information of The user 
-- For Processing Payment For Reservation
CREATE TABLE creditcardinfo (
    creditCardNO VARCHAR(16) PRIMARY KEY,
    nameOnCard VARCHAR(50),
    expiryDate VARCHAR(10),
    CSV INT,
    currentBalance INT
);

CREATE TABLE userIdentity(
	userSSN VARCHAR(50) PRIMARY KEY,
	userImage IMAGE
)

-- Create Table For The Users Login Information
-- Used To Authenicate The Users To The Application
CREATE TABLE userlogin (
    userSSN VARCHAR(50) PRIMARY key,
    userPass VARCHAR(100),
    userFirstName VARCHAR(50),
    userMiddleName VARCHAR(50),
    userLastName VARCHAR(50),
    userState VARCHAR(10),
	FOREIGN KEY(userSSN) REFERENCES userIdentity(userSSN)
);


-- Create Table To Store Registered Trains Information
CREATE TABLE traininfo (
	trainID varchar(10) PRIMARY KEY,
    trainNum INT,
    trainName VARCHAR(50),
    BPoint VARCHAR(30),
    DPoint VARCHAR(30),
    NOSFirstClass INT,
    FeeFirstClass INT,
    NOSSecondClass INT,
    FeeSecondClass INT,
    Day INT,
    Month INT,
    Year INT
);


-- Create Table To Store The Reserved Seat Detail 
-- And Total Payment For The Reserved Train Seat
CREATE TABLE requiredfee (
    userSSN VARCHAR(50),
	trainID varchar(10) PRIMARY KEY,
    trainNum INT,
    reservedClass VARCHAR(10),
    totalFee INT,
    NOSeat INT,
    trainName VARCHAR(50),
    Day INT,
    Month INT,
    Year INT,
    paymentState VARCHAR(20),
    FOREIGN KEY(userSSN) REFERENCES userlogin(userSSN)
);


-- Create Table For The Passengers 
-- Who Are Registered For The Travel
CREATE TABLE passengerdetail (
    reserverSSN VARCHAR(50),
    userSSN VARCHAR(50),
    passengerTicketNO VARCHAR(50),
    reservedClass VARCHAR(10),
	trainID varchar(10),
    trainNum INT,
    passengerName VARCHAR(50),
    passengerAge INT,
	PRIMARY KEY(passengerTicketNO,trainID),
    FOREIGN KEY(reserverSSN) REFERENCES userlogin(userSSN)
);

ALTER TABLE requiredfee ADD FOREIGN KEY(trainID) REFERENCES traininfo(trainID);
ALTER TABLE passengerdetail ADD FOREIGN KEY(trainID) REFERENCES traininfo(trainID);
ALTER TABLE passengerdetail Add FOREIGN KEY(trainID) REFERENCES requiredfee(trainID);

-- Inserting Some Information To Test The Databases
INSERT INTO `adminaccount` VALUES(1,'1111','Lakachew','lake');
INSERT INTO `creditcardinfo` VALUES('5410236987541023','Habib Endris','10/2018',254,1000);
INSERT INTO `useridentity` VALUES('1622',NULL);
INSERT INTO `userlogin` VALUES('1622','habib','Habib','Endris','Mohammed','NotApproved');
INSERT INTO `traininfo` VALUES(1020,'Boche','Kombolcha','Dessie',20,10,30,12,3,2,2018);
INSERT INTO `requiredfee` VALUES('1622',1020,'First',13,2,'Boche',2,3,2018,'NotPayed');
INSERT INTO `passengerdetail` VALUES('1622','2222','2222-3-2-2018','First',1020,'Aygen Abel',20)