CREATE DATABASE revolut;
USE revolut;
CREATE TABLE IF NOT EXISTS share (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    abbreviation VARCHAR(255),
    value DOUBLE,
    dividends DOUBLE
);

INSERT INTO share (id, name, abbreviation, value, dividends) VALUES (1, 'Apple Inc.', 'AAPL', 568.20, 0.0064);
INSERT INTO share (id, name, abbreviation, value, dividends) VALUES (2, 'Amazon.com Inc..', 'AMZN', 3749.80, 0);
INSERT INTO share (id, name, abbreviation, value, dividends) VALUES (3, 'Microsoft Corporation', 'MSFT', 397.30, 0.008);
INSERT INTO share (id, name, abbreviation, value, dividends) VALUES (4, 'Facebook, Inc.', 'FB', 303.20, 0);
INSERT INTO share (id, name, abbreviation, value, dividends) VALUES (5, 'Alphabet Inc.', 'GOOGL', 2237.50, 0);

CREATE TABLE IF NOT EXISTS crypto_currency (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    abbreviation VARCHAR(255),
    value DOUBLE,
    staking_return DOUBLE
);

INSERT INTO crypto_currency (id, name, abbreviation, value, staking_return) VALUES (1, 'Ethereum', 'ETH', 3683.43, 0.066);
INSERT INTO crypto_currency (id, name, abbreviation, value, staking_return) VALUES (2, 'Cardano', 'ADA', 3.01, 0.053);
INSERT INTO crypto_currency (id, name, abbreviation, value, staking_return) VALUES (3, 'Polkadot', 'DOT', 59.52, 0.0135);
INSERT INTO crypto_currency (id, name, abbreviation, value, staking_return) VALUES (4, 'Solana', 'SOL', 264.1, 0.085);
INSERT INTO crypto_currency (id, name, abbreviation, value, staking_return) VALUES (5, 'Binance Coin', 'BNB', 812.15, 0.091);

CREATE TABLE IF NOT EXISTS transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_iban VARCHAR(255),
    receiver_iban VARCHAR(255),
    amount DOUBLE,
    tax DOUBLE,
    transaction_date DATE
);

create TABLE IF NOT EXISTS vault (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_savings DOUBLE,
    savings_per_day DOUBLE
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
    balance DOUBLE,
    currency VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS card (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    tag VARCHAR(255),
    number VARCHAR(255),
    card_limit DOUBLE,
    cvv INT,
    expiration_date DATE,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS shares_owned (
    user_id INT,
    share_id INT,
    amount DOUBLE,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (share_id) REFERENCES share(id),
    PRIMARY KEY (user_id, share_id)
);

CREATE TABLE IF NOT EXISTS crypto_owned (
    user_id INT,
    crypto_id INT,
    amount DOUBLE,
    staked_amount DOUBLE,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (crypto_id) REFERENCES crypto_currency(id),
    PRIMARY KEY (user_id, crypto_id)
);
