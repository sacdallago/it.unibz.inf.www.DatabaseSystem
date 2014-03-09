CREATE TABLE company
    (
     market_code VARCHAR(10) PRIMARY KEY NOT NULL,
     brand_name VARCHAR(50) NOT NULL,
     rating INTEGER NOT NULL CHECK (rating between 0 and 10),
     capital DOUBLE PRECISION NOT NULL,
     currency VARCHAR(3) NOT NULL
     );
     

CREATE TABLE bank_account
    (relation_number INTEGER NOT NULL,
     iban VARCHAR(31) NOT NULL,
     bic VARCHAR(11) NOT NULL,
     type VARCHAR(8) NOT NULL CHECK (type in ('saving', 'checking')),
     balance DOUBLE PRECISION NOT NULL,
     account_currency VARCHAR(3) NOT NULL,
     local_currency VARCHAR(3) NOT NULL,
     market_code VARCHAR(10) NOT NULL,
     FOREIGN KEY (market_code) REFERENCES company ON DELETE CASCADE ON UPDATE CASCADE,
     PRIMARY KEY (iban, bic)
     );

CREATE TABLE broker
    (
     broker_number INTEGER PRIMARY KEY NOT NULL,
     rating INTEGER NOT NULL CHECK (rating between 0 and 10),
     market_code VARCHAR(10),
     FOREIGN KEY (market_code) REFERENCES company ON DELETE SET NULL ON UPDATE CASCADE
     );
     
CREATE TABLE title
    (
     title_number INTEGER NOT NULL,
     market_code VARCHAR(10) NOT NULL,
     iban VARCHAR(31) NOT NULL,
     bic VARCHAR(11) NOT NULL,
     broker_number INTEGER REFERENCES broker ON DELETE SET NULL ON UPDATE CASCADE,
     created_day DATE NOT NULL,
     initial_value DOUBLE PRECISION NOT NULL,
     initial_value_currency VARCHAR(3) NOT NULL,
     FOREIGN KEY (market_code) REFERENCES company ON DELETE CASCADE ON UPDATE CASCADE,
     FOREIGN KEY (iban, bic) REFERENCES bank_account ON DELETE CASCADE ON UPDATE CASCADE,
     PRIMARY KEY (title_number, market_code)
     );

CREATE TABLE stock_value
    (
     time DATE NOT NULL,
     market_code VARCHAR(10) NOT NULL,
     title_number INTEGER NOT NULL,
     current_value DOUBLE PRECISION NOT NULL,
     currency VARCHAR(3) NOT NULL,
     FOREIGN KEY (title_number, market_code) REFERENCES title ON DELETE CASCADE ON UPDATE CASCADE,
     PRIMARY KEY (time, title_number, market_code)
     );