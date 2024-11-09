CREATE TABLE `Transactions` (
    `TransactionID` mediumint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `Type` enum('credit', 'debit') NOT NULL,
    `Amount` mediumint NOT NULL DEFAULT 0,
    `CreatedTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `UpdatedTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    `CategoryIndex` varchar(255) NOT NULL,
    CONSTRAINT `FK_Category_Transactions`
        FOREIGN KEY (`CategoryIndex`) REFERENCES `Categories`(`CategoryIndex`),
    `Description` varchar(255) NOT NULL,
    `PersonIndex` varchar(255) NOT NULL,
    CONSTRAINT `FK_Person_Transactions`
        FOREIGN KEY (`PersonIndex`) REFERENCES `People`(`PersonIndex`)
);

CREATE INDEX `amount`
    ON `Transactions`(`Amount`);

CREATE INDEX `description`
    ON `Transactions`(`Description`);

CREATE INDEX `amount_description`
    ON `Transactions`(`Amount`, `Description`);

CREATE INDEX `amount_personindx`
    ON `Transactions`(`Amount`, `PersonIndex`);

CREATE INDEX `amount_categoryindx`
    ON `Transactions`(`Amount`, `CategoryIndex`);

CREATE INDEX `amount_createdtime`
    ON `Transactions`(`Amount`, `CreatedTime`);

CREATE INDEX `transaction`
ON `Transactions`(`Type`, `Amount`, `CreatedTime`, `CategoryIndex`, `PersonIndex`)