CREATE TABLE currency_exchange (
    id SERIAL PRIMARY KEY,
    foreign_currency_id int4,
    rate_foreign_currency numeric,
    exchange_rate numeric,
    updated_at timestamp,
     CONSTRAINT fk_foreign_currency_id
            FOREIGN KEY(foreign_currency_id)
          REFERENCES currency(id)
)