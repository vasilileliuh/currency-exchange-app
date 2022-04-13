CREATE TABLE available_funds (
    office_id int4,
    currency_id int4,
    amount float4 NOT NULL,
    primary key (office_id,currency_id),
      CONSTRAINT fk_available_funds_office
              FOREIGN KEY(office_id)
        	  REFERENCES exchange_office(id),
     CONSTRAINT fk_available_funds_currency
            FOREIGN KEY(currency_id)
            REFERENCES currency(id)

)