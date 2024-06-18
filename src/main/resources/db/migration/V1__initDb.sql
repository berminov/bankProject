CREATE TABLE bank_accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    balance DECIMAL(19, 4) NOT NULL
);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    bank_account_id INT NOT NULL,
    UNIQUE (bank_account_id),
    FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id)
);

CREATE TABLE deposit_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    deposit_type_name VARCHAR(255) NOT NULL
);

CREATE TABLE interest_payment_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    interest_payment_type_name VARCHAR(255) NOT NULL
);

CREATE TABLE deposits (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19, 4) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    deposit_rate DECIMAL(5, 2) NOT NULL,
    next_payment_date DATE NOT NULL,
    piggy_bank DECIMAL(19, 4),
    active BOOLEAN,
    deposit_type_id INT NOT NULL,
    interest_payment_type_id INT NOT NULL,
    customer_id INT NOT NULL,
    deposit_account_id INT NOT NULL,
    FOREIGN KEY (deposit_type_id) REFERENCES deposit_types(id),
    FOREIGN KEY (interest_payment_type_id) REFERENCES interest_payment_types(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (deposit_account_id) REFERENCES bank_accounts(id)
);