CREATE TABLE `People` (
    `PersonID` mediumint PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `PersonIndex` varchar(255) UNIQUE NOT NULL,
    `LastName` varchar(255) NOT NULL,
    `FirstName` varchar(255) NOT NULL,
    `FullName` varchar(255) GENERATED ALWAYS AS (CONCAT(`FirstName`, ' ', `LastName`)) NOT NULL,
    `Birthdate` DATETIME NOT NULL,
    `CreatedTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `UpdatedTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX `indx`
    ON `People`(`PersonIndex`);

CREATE INDEX `lastname`
    ON `People`(`LastName`);

CREATE INDEX `firstname`
    ON `People`(`FirstName`);

CREATE INDEX `fullname`
    ON `People`(`FullName`);

CREATE INDEX `birthdate`
    ON `People`(`Birthdate`);

CREATE INDEX `id_lastname`
    ON `People`(`PersonID`, `LastName`);

CREATE INDEX `id_firstname`
    ON `People`(`PersonID`, `FirstName`);

CREATE INDEX `id_fullname`
    ON `People`(`PersonID`, `FullName`);

CREATE INDEX `index_lastname`
    ON `People`(`PersonIndex`, `LastName`);

CREATE INDEX `index_firstname`
    ON `People`(`PersonIndex`, `FirstName`);

CREATE INDEX `index_fullname`
    ON `People`(`PersonIndex`, `FullName`);

CREATE INDEX `lastname_firstname`
    ON `People`(`LastName`, `FirstName`);

CREATE INDEX `person`
    ON `People`(`PersonIndex`, `LastName`, `FirstName`, `Birthdate`);

CREATE INDEX `person_fullname`
    ON `People`(`PersonIndex`, `FullName`, `Birthdate`);