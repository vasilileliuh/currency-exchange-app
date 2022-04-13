INSERT INTO currency (name) VALUES ('LEI');
INSERT INTO currency (name) VALUES ('EUR');
INSERT INTO currency (name) VALUES ('USD');
INSERT INTO currency (name) VALUES ('KZT');
INSERT INTO currency (name) VALUES ('JPY');

INSERT INTO exchange_office (office_name) VALUES ('Main office');

INSERT INTO available_funds (office_id, currency_id, amount) VALUES (1, 1, 1000);
INSERT INTO available_funds (office_id, currency_id, amount) VALUES (1, 2, 2000);
INSERT INTO available_funds (office_id, currency_id, amount) VALUES (1, 3, 3000);
INSERT INTO available_funds (office_id, currency_id, amount) VALUES (1, 4, 4000);
INSERT INTO available_funds (office_id, currency_id, amount) VALUES (1, 5, 5000);


INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (2, 1, 21.9,(CURRENT_DATE - interval '4 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (3, 1, 16.3, (CURRENT_DATE - interval '4 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (4, 10, 0.15, (CURRENT_DATE - interval '4 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (5, 100, 13, (CURRENT_DATE - interval '4 day'));

INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (2, 1, 21.5,(CURRENT_DATE - interval '3 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (3, 1, 16.5, (CURRENT_DATE - interval '3 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (4, 10, 0.25, (CURRENT_DATE - interval '3 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (5, 100, 14.5, (CURRENT_DATE - interval '3 day'));

INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (2, 1, 21,(CURRENT_DATE - interval '2 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (3, 1, 16, (CURRENT_DATE - interval '2 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (4, 10, 0.2, (CURRENT_DATE - interval '2 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (5, 100, 14, (CURRENT_DATE - interval '2 day'));

INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (2, 1, 21,(CURRENT_DATE - interval '1 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (3, 1, 16, (CURRENT_DATE - interval '1 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (4, 10, 0.3, (CURRENT_DATE - interval '1 day'));
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (5, 100, 15, (CURRENT_DATE - interval '1 day'));

INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (2, 1, 22,CURRENT_DATE);
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (3, 1, 17, CURRENT_DATE);
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (4, 10, 0.4, CURRENT_DATE);
INSERT INTO currency_exchange (foreign_currency_id, rate_foreign_currency, exchange_rate, updated_at) VALUES (5, 100, 16, CURRENT_DATE);

