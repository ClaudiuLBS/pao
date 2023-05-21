CREATE TABLE IF NOT EXISTS share (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    abbreviation VARCHAR(255),
    value DOUBLE(10, 5),
    dividends DOUBLE(10, 5)
);

CREATE TABLE IF NOT EXISTS crypto_currency (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    abbreviation VARCHAR(255),
    value DOUBLE(10, 5),
    staking_return DOUBLE(10, 5)
);

CREATE TABLE IF NOT EXISTS transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_iban VARCHAR(255),
    receiver_iban VARCHAR(255),
    amount DOUBLE(10, 5),
    tax DOUBLE(2, 2),
    transaction_date DATE
);

create TABLE IF NOT EXISTS vault (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_savings DOUBLE(10,5),
    savings_per_day DOUBLE(10, 5)
);

CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    vault_id INT,
    FOREIGN KEY (vault_id) REFERENCES vault(id)
);

CREATE TABLE IF NOT EXISTS account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    iban VARCHAR(255),
    balance DOUBLE(10, 5),
    currency VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS card (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    tag VARCHAR(255),
    number VARCHAR(255),
    card_limit DOUBLE(10, 5),
    cvv INT,
    expiration_date DATE,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS shares_owned (
    user_id INT,
    share_id INT,
    amount DOUBLE(10, 5),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (share_id) REFERENCES share(id)
);

CREATE TABLE IF NOT EXISTS crypto_owned (
    user_id INT,
    crypto_id INT,
    amount DOUBLE(10, 5),
    staked_amount DOUBLE(10, 5),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (crypto_id) REFERENCES crypto_currency(id)
);