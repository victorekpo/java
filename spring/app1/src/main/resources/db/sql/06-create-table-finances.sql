CREATE TABLE `Finances` (
    `BudgetID` mediumint PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `PersonIndex` varchar(255) NOT NULL,
    CONSTRAINT `FK_Person_Finances`
        FOREIGN KEY (`PersonIndex`) REFERENCES `People`(`PersonIndex`),
    `MonthlyExpectedExpenses` mediumint NOT NULL,
    `MonthlyExpectedIncome` mediumint  NOT NULL,
    `MonthlyExpectedSavings` mediumint GENERATED ALWAYS AS (`MonthlyExpectedIncome` - `MonthlyExpectedExpenses`) VIRTUAL NOT NULL,
    `CreatedTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `UpdatedTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX `expenses`
    ON `Finances`(`MonthlyExpectedExpenses`);

CREATE INDEX `income`
    ON `Finances`(`MonthlyExpectedIncome`);

CREATE INDEX `savings`
    ON `Finances`(`MonthlyExpectedSavings`);

CREATE INDEX `id_expenses`
    ON `Finances`(`BudgetID`, `PersonIndex`, `MonthlyExpectedExpenses`);

CREATE INDEX `id_income`
    ON `Finances`(`BudgetID`, `PersonIndex`, `MonthlyExpectedIncome`);

CREATE INDEX id_savings
    ON `Finances`(`BudgetID`, `PersonIndex`, `MonthlyExpectedSavings`);

CREATE INDEX finances
    ON `Finances`(`PersonIndex`, `MonthlyExpectedExpenses`, `MonthlyExpectedIncome`, `MonthlyExpectedSavings`);