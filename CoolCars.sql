# noinspection SqlNoDataSourceInspectionForFile

DROP SCHEMA IF EXISTS CoolCars;
CREATE SCHEMA CoolCars;
USE CoolCars;

DELIMITER //

CREATE PROCEDURE user_car_search(IN sid INT,
                            IN yearJ  INT,
                            IN priceJ  INT )
BEGIN
    SELECT CarCondition, Style, Make, Model, Year, Color, Price
        FROM AvailCars WHERE StoreID = sid AND Year >= yearJ AND Price <= priceJ;
end //

CREATE PROCEDURE user_login(IN u VARCHAR(30),
                            IN p VARCHAR(20))
BEGIN
    SELECT Username FROM User WHERE Username = u AND Password = aes_encrypt(p, u);
end //

CREATE PROCEDURE employee_login(IN eid INT,
                                IN p VARCHAR(20))
BEGIN
    SELECT EmployeeID FROM Employee WHERE EmployeeID = eid AND Password = aes_encrypt(p, eid);
END //

CREATE PROCEDURE add_car(IN vinJ INT, IN storeIDJ INT,
                        IN conditionJ INT, IN styleJ VARCHAR(15),
                        IN makeJ VARCHAR(15), IN modelJ VARCHAR(15),
                        IN yearJ INT, IN colorJ VARCHAR(15),
                        IN priceJ INT)
BEGIN
    INSERT INTO Cars (VIN, StoreID, CarCondition, Style,
                      Make, Model, Year, Color, Price)
                VALUES (vinJ, storeIDJ, conditionJ, styleJ, makeJ,
                        modelJ, yearJ, colorJ, priceJ);
end //


CREATE PROCEDURE edit_car(IN vinJ INT, IN storeIDJ INT,
                          IN conditionJ INT, IN colorJ VARCHAR(15),
                          IN priceJ INT)
BEGIN
    UPDATE Cars
        SET StoreID =  storeIDJ, CarCondition = conditionJ,
            Price = priceJ, Color = colorJ
            WHERE VIN = vinJ;
end //



DELIMITER ;



CREATE TABLE Location (
	StoreID INT NOT NULL,
    Address VARCHAR (50) NOT NULL,
    PhoneNumber VARCHAR (14) NULL,
    ManagerID INT NOT NULL,
    PRIMARY KEY (StoreID),
    CONSTRAINT FK_Employee_ManagerID FOREIGN KEY (ManagerID) REFERENCES Employee(EmployeeID));

CREATE TABLE Employee (
	EmployeeID INT NOT NULL,
    Password VARCHAR (50) NOT NULL,
    Name VARCHAR (25) NOT NULL,
    EmployeeType INT NOT NULL,
    StoreID INT NOT NULL,
    PhoneNumber VARCHAR (14) NULL,
    PRIMARY KEY (EmployeeID),
    CONSTRAINT FK_Location_StoreID FOREIGN KEY (StoreID) REFERENCES Location(StoreID));

CREATE TABLE Cars (
	VIN INT NOT NULL,
    StoreID INT NOT NULL,
    CarCondition INT NOT NULL,
    Style VARCHAR (15) NOT NULL,
    Make VARCHAR (15) NOT NULL,
    Model VARCHAR (30) NOT NULL,
    Year INT NOT NULL,
    Color VARCHAR (15) NOT NULL,
    Price INT NOT NULL,
    PRIMARY KEY (VIN),
    CONSTRAINT FK_Location_StoreID FOREIGN KEY (StoreID) REFERENCES Location(StoreID));

CREATE TABLE User (
	Username VARCHAR (30) NOT NULL,
    Password VARCHAR (50) NOT NULL,
    Name VARCHAR (25) NOT NULL,
    Age INT NOT NULL,
    CreditScore INT NOT NULL,
    License VARCHAR (25) NOT NULL,
    Insurance VARCHAR (25) NOT NULL,
    Balance INT NULL,
    PhoneNumber VARCHAR (14) NULL,
    PRIMARY KEY (Username));

CREATE TABLE Rents (
	VIN INT NOT NULL,
    Username VARCHAR (30) NOT NULL,
    Duration VARCHAR (50) NOT NULL,
    PRIMARY KEY (VIN, Username),
    CONSTRAINT FK_Cars_VIN FOREIGN KEY (VIN) REFERENCES Cars(VIN),
    CONSTRAINT FK_User_Username FOREIGN KEY (Username) REFERENCES User(Username));

CREATE TABLE Helped_By (
	Ticket INT NOT NULL,
    EmployeeID INT NOT NULL,
    Username VARCHAR (30) NOT NULL,
    PRIMARY KEY (Ticket),
    CONSTRAINT FK_Employee_EmployeeID FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID),
    CONSTRAINT FK_User_Username FOREIGN KEY (Username) REFERENCES User(Username));

INSERT INTO Location (StoreID, Address, PhoneNumber, ManagerID)
VALUES (1, '422 Roberts St', '(122)435-7688', 1), (2, '256 South Andes Ave', '(623)455-6113', 2), (3, '754 Main St', '(816)435-7222', 3);

INSERT INTO Cars ( VIN, StoreID, CarCondition, Style, Make, Model, Year, Color, Price )
VALUES
	(2546, 1, 1, 'SUV', 'Cadillac', 'Escalade', 2019, 'Black', 100),
    (8456, 1, 2, 'Sport', 'Mazda', 'Miata', 2015, 'Blue', 60),
    (1932, 2, 1, 'Coupe', 'BMW', 'M4', 2020, 'Silver', 90),
    (1957, 2, -1, 'Coupe', 'Porsche', '911 GT2 RS', 2017, 'Yellow', 210),
    (9845, 3, 1, 'Sport', 'Bugatti', 'Veyron', 2012, 'Silver', 560),
    (7298, 3, 2, 'Sport', 'Toyota', 'Supra', 2017, 'Silver', 240),
    (9172, 2, 1, 'Sedan', 'Mitsubishi', 'Lancer Evolution', 2016, 'Red', 50),
    (6721, 1, 3, 'Coupe', 'Ford', 'GT', 2017, 'Blue', 220),
    (1826, 2, -1, 'Sport', 'Lamborghini', 'Centenario', 2017, 'Yellow', 420),
	(9374, 1, 1, 'Sport', 'Pagani', 'Zonda HP Barchetta', 2017, 'Silver', 530);
    
INSERT INTO User (Username, Password, Name, Age, CreditScore, License, Insurance, Balance, PhoneNumber)
VALUES ('koverbay', aes_encrypt('pass', 'koverbay'), 'Kory Overbay', 20, 400, 3333, 3434, 0, '(816)673-8479'),
	('user1', aes_encrypt('pass1', 'user1'), 'Bob Builder', 43, 400, 1234, 5678, 0, '(816)712-3245'),
	('user2', aes_encrypt('pass2', 'user2'), 'Dora Explora', 18, 200, 3245, 8573, 3000, '(281)343-2367'),
    ('user3', aes_encrypt('pass3', 'user3'), 'Jimmy Neutron', 32, 350, 2024, 3951, 0, '(382)333-5973'),
    ('user4', aes_encrypt('pass4', 'user4'), 'Fred Rogers', 51, 530, 3942, 6941, 0, '(394)359-6998'),
    ('user5', aes_encrypt('pass5', 'user5'), 'Steve Rogers', 30, 450, 5908, 0899, 2500, '(959)844-6327'),
    ('user6', aes_encrypt('pass6', 'user6'), 'Charlie Brown', 21, 390, 9943, 7462, 6000, '(733)573-8788'),
    ('user7', aes_encrypt('pass7', 'user7'), 'Jack Daniels', 57, 500, 8612, 8374, 0, '(816)838-3342'),
    ('user8', aes_encrypt('pass8', 'user8'), 'Lucile Ball', 38, 375, 8382, 7633, 1750, '(211)382-3733'),
    ('user9', aes_encrypt('pass9', 'user9'), 'Tommy Tutone', 45, 642, 6634, 1332, 1000, '(722)867-5309'),
    ('user10', aes_encrypt('pass10', 'user10'), 'Alec Baldwin', 56, 300, 5643, 8843, 0, '(833)356-4656');

INSERT INTO Employee (EmployeeID, Password, Name, EmployeeType, StoreID, PhoneNumber)
VALUES (1, aes_encrypt('password1', '1'), 'Robert Smith', 1, 1, '(281)342-2367'),
  	(2, aes_encrypt('password2', '2'), 'Mark Markus', 2, 1,'(816)245-1356'),
	(3, aes_encrypt('password3', '3'), 'Walter Twoshoes', 1, 2,'(913)183-1945'),
    (4, aes_encrypt('password4', '4'), 'Samus Metroid', 2, 2,'(913)917-1845'),
    (5, aes_encrypt('password5', '5'), 'Steven Even', 1, 3,'(913)819-1924'),
    (6, aes_encrypt('password6', '6'), 'Steeeeeven Eeeeeven', 2, 3,'(816)817-1845'),
    (7, aes_encrypt('password7', '7'), 'Mario Peach', 1, 2,'(816)294-1935'),
    (8, aes_encrypt('password8', '8'), 'Sasquatch Hair', 2, 2,'(913)817-8495'),
    (9, aes_encrypt('password9', '9'), 'Philipo Demagio', 1, 1,'(816)917-8562'),
    (10, aes_encrypt('password10', '10'), 'Jacque Frenchie', 2, 3,'(913)846-1947');

/*INSERT INTO Rents (VIN, Username, Duration)
VALUES (
*/
CREATE VIEW AvailCars AS
SELECT Year, Make, Model, Price, Color, Style, CarCondition, StoreID, VIN
FROM Cars
WHERE CarCondition != -1;




