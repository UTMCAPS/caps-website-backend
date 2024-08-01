CREATE DATABASE IF NOT EXISTS utmcaps;

USE utmcaps;

CREATE TABLE IF NOT EXISTS ledger (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item TEXT NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    expense_date DATE NOT NULL,
    invoice_url TEXT,
    notes TEXT
);
