DELETE from stock_value;
DELETE from title;
DELETE from broker;
DELETE from bank_account;
DELETE from company;


--COMPANIES
INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('AAPL', 'Apple Inc.', 8, 499.35, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('FB', 'Facebook, Inc.', 6, 134.67, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('E', 'Eni SpA', 7, 82.01, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('IBM', 'International Business Machines Corporation', 5, 190.85, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('F', 'Ford Motor Co.', 3, 65.87, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('WMT', 'Wal-Mart Stores Inc.', 3, 249.96, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('TI', 'Telecom Italia S.p.A.', 2, 17.95, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('G.MI', 'Assicurazioni Generali S.p.A.', 7, 25.31, 'USD');

--FINANCIAL INSTITUTIONS
INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('RBS.L', 'Royal Bank of Scotland Group PLC', 8, 35.96, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('NBG', 'National Bank of Greece SA', 2, 76.95, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('BKIR.L', 'Bank of Ireland (Governor & Co of)', 4, 7.91, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('BBVA', 'Banco Bilbao Vizcaya Argentaria, S.A.', 5, 74.16, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('LLOY.L', 'Lloyds Banking Group PLC', 7, 38.69, 'USD');

INSERT INTO company(market_code, brand_name, rating, capital, currency)
VALUES ('DB', 'Deutsche Bank AG', 9, 46.55, 'USD');


-- B) Create a bunch of bank accounts, say 2 per financial institution...

INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (12345, 'GB30SBVC18523767901629', 'RBS2343X', 'checking', 55654.56, 'GBP', 'EUR', 'RBS.L');
INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (11232, 'GB50IRLP57667999076476', 'RBS2343X', 'saving', 120345.44, 'GBP', 'USD', 'RBS.L');

INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (2322, 'GR7676090532907194452714662', 'NBG133S', 'checking', 67233.32, 'EUR', 'EUR', 'NBG');
INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (1323, 'GR8527198091543743375917122', 'NBG133S', 'checking', 94832234.32, 'EUR', 'EUR', 'NBG');

INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (98878, 'IE44RECW98816996287418', 'BIRL3423Y', 'checking', 5242534.12, 'EUR', 'GBP', 'BKIR.L');
INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (145565, 'IE67HDKH79508498599334', 'BIRL3423Y', 'checking', 1532655.45, 'EUR', 'EUR', 'BKIR.L');

INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (112133, 'ES5905905101653101555303', 'BBVA133D', 'checking', 544967.87, 'EUR', 'EUR', 'BBVA');
INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (112145, 'ES1392108455941330778140', 'BBVA133D', 'checking', 146.67, 'EUR', 'EUR', 'BBVA');

INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (90992, 'GB92NHKD31959143745207', 'LOY1389B', 'checking', 45389923.32, 'EUR', 'EUR', 'LLOY.L');
INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (84933, 'GB16MSCC34428119497341', 'LOY1389B', 'checking', 788342.23, 'EUR', 'EUR', 'LLOY.L');

INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (10384, 'DE32639099669176875792', 'DBK4673G', 'checking', 73423324.32, 'EUR', 'EUR', 'DB');
INSERT INTO bank_account(relation_number, iban, bic, type, balance, account_currency, local_currency, market_code)
VALUES (544836, 'DE75657868761909088443', 'DBK4673G', 'checking', 15432375432.34, 'EUR', 'EUR', 'DB');


-- C) Create a bunch of brokers woring for the different banks. Some banks may have none...

INSERT INTO broker(broker_number, rating, market_code)
VALUES (74389200, 6, 'RBS.L');
INSERT INTO broker(broker_number, rating, market_code)
VALUES (32784883, 7, 'RBS.L');
INSERT INTO broker(broker_number, rating, market_code)
VALUES (37239498, 8, 'RBS.L');

INSERT INTO broker(broker_number, rating, market_code)
VALUES (39400945, 4, 'DB');
INSERT INTO broker(broker_number, rating, market_code)
VALUES (39238843, 8, 'DB');

INSERT INTO broker(broker_number, rating, market_code)
VALUES (37839959, 6, 'LLOY.L');

INSERT INTO broker(broker_number, rating, market_code)
VALUES (54233, 9, 'NBG');

INSERT INTO broker(broker_number, rating, market_code)
VALUES (95494773, 9, 'BBVA');
INSERT INTO broker(broker_number, rating, market_code)
VALUES (75394885, 7, 'BBVA');
INSERT INTO broker(broker_number, rating, market_code)
VALUES (57493984, 5, 'BBVA');


-- D) Create a bunch of titles/shares for each company (not financial institution) that are stored in the above listed accounts.
-- it is possible that some accounts don't have shares for some companies. Or that the broker doens't exist anymore...

INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (6534, 'AAPL', 'GR7676090532907194452714662', 'NBG133S', 54233, '2013-02-06', 80.46, 'USD');
INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (6535, 'AAPL', 'DE32639099669176875792', 'DBK4673G', 39238843, '2013-10-10', 55.58, 'USD');

INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (1233, 'FB', 'GB16MSCC34428119497341', 'LOY1389B', 37839959, '2013-11-13', 82.27, 'USD');
INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (1243, 'FB', 'ES1392108455941330778140', 'BBVA133D', 95494773, '2013-12-16', 40.07, 'USD');
INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (1123, 'FB', 'ES1392108455941330778140', 'BBVA133D', 95494773, '2013-11-23', 14.53, 'USD');

INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (543344, 'E', 'IE44RECW98816996287418', 'BIRL3423Y', NULL, '2013-06-16', 81.88, 'USD');
INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (532544, 'E', 'IE67HDKH79508498599334', 'BIRL3423Y', NULL, '2013-04-03', 34.17, 'USD');

INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (22, 'WMT', 'GR7676090532907194452714662', 'NBG133S', 54233, '2013-02-26', 24.32, 'USD');
INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (4325, 'WMT', 'DE75657868761909088443', 'DBK4673G', 39400945, '2013-01-23', 17.42, 'USD');

INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (5334, 'TI', 'DE32639099669176875792', 'DBK4673G', 39400945, '2013-07-16', 63.07, 'USD');

INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (12344, 'G.MI', 'GB50IRLP57667999076476', 'RBS2343X', 32784883, '2013-03-23', 21.45, 'USD');
INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (43233, 'G.MI', 'GB30SBVC18523767901629', 'RBS2343X', 32784883, '2013-12-13', 31.30, 'USD');

INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (61736, 'F', 'IE44RECW98816996287418', 'BIRL3423Y', NULL, '2013-10-06', 33.31, 'USD');
INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (45742, 'F', 'IE44RECW98816996287418', 'BIRL3423Y', NULL, '2013-07-08', 93.00, 'USD');
INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (68546, 'F', 'ES1392108455941330778140', 'BBVA133D', 75394885, '2013-08-19', 41.11, 'USD');

INSERT INTO title(title_number, market_code, iban, bic, broker_number, created_day, initial_value, initial_value_currency)
VALUES (865, 'IBM', 'GR7676090532907194452714662', 'NBG133S', 54233, '2013-10-31', 17.74, 'USD');


-- D) Create different date stamps and prices for different titles/shares. Stock values are grouped for title.
/*
The prices are calculated randomly starting from the buying price, with a variance that depends on the
time stamp between the last and first date listed. This to make it seem more realistic.
I tried to make the trends match as much as possible between titles for the same company.
*/

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-03-17', 'AAPL', 6534, 75.87, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-04-17', 'AAPL', 6534, 76.33, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-05-17', 'AAPL', 6534, 79.43, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-06-17', 'AAPL', 6534, 80.40, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-07-17', 'AAPL', 6534, 78.46, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-10-17', 'AAPL', 6535, 50.63, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-10-22', 'AAPL', 6535, 52.70, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-11', 'AAPL', 6535, 54.01, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-15', 'AAPL', 6535, 54.70, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-17', 'AAPL', 6535, 50.69, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-17', 'FB', 1233, 70.17, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-20', 'FB', 1233, 64.02, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-22', 'FB', 1233, 78.21, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-25', 'FB', 1233, 75.10, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-30', 'FB', 1233, 63.47, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-17', 'FB', 1243, 32.78, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-18', 'FB', 1243, 33.50, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-27', 'FB', 1123, 13.73, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-29', 'FB', 1123, 13.04, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-30', 'FB', 1123, 14.70, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-06-20', 'E', 543344, 86.97, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-06-25', 'E', 543344, 83.75, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-06-30', 'E', 543344, 79.47, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-07-10', 'E', 543344, 84.68, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-04-05', 'E', 532544, 32.37, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-05-10', 'E', 532544, 36.34, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-06-10', 'E', 532544, 36.04, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-03-04', 'WMT', 22, 27.14, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-04-04', 'WMT', 22, 26.68, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-05-04', 'WMT', 22, 25.45, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-06-04', 'WMT', 22, 26.66, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-07-04', 'WMT', 22, 28.60, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-01-24', 'WMT', 4325, 17.63, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-02-24', 'WMT', 4325, 17.54, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-03-24', 'WMT', 4325, 15.73, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-04-24', 'WMT', 4325, 16.22, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-07-24', 'TI', 5334, 69.87, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-08-20', 'TI', 5334, 60.11, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-09-13', 'TI', 5334, 56.64, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-10-23', 'TI', 5334, 50.91, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-15', 'TI', 5334, 52.94, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-04-15', 'G.MI', 12344, 23.66, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-10-15', 'G.MI', 12344, 25.08, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-15', 'G.MI', 12344, 25.56, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-13', 'G.MI', 43233, 32.24, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-14', 'G.MI', 43233, 32.83, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-15', 'G.MI', 43233, 32.04, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-16', 'G.MI', 43233, 32.48, 'USD');

INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-10-25', 'F', 61736, 34.07, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-25', 'F', 61736, 33.95, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-26', 'F', 61736, 33.89, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-28', 'F', 61736, 33.87, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-30', 'F', 61736, 33.26, 'USD');


INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-07-09', 'F', 45742, 84.78, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-08-09', 'F', 45742, 89.12, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-09-09', 'F', 45742, 98.06, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-10-09', 'F', 45742, 97.29, 'USD');


INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-08-20', 'F', 68546, 40.85, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-08-21', 'F', 68546, 41.16, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-08-22', 'F', 68546, 41.07, 'USD');


INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-12', 'IBM', 865, 17.53, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-21', 'IBM', 865, 17.08, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-11-30', 'IBM', 865, 17.81, 'USD');
INSERT INTO stock_value(time, market_code, title_number, current_value, currency)
VALUES ('2013-12-16', 'IBM', 865, 18.19, 'USD');