CREATE TABLE `Categories` (
    `CategoryID` mediumint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `CategoryIndex` varchar(255) UNIQUE NOT NULL,
    `CategoryName` varchar(255) NOT NULL,
    `CreatedTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `UpdatedTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX `idx`
    ON `Categories`(`CategoryIndex`);

CREATE INDEX `id_categoryname`
    ON `Categories`(`CategoryID`, `CategoryName`);

CREATE INDEX `category`
    ON `Categories`(`CategoryIndex`, `CategoryName`);
