CREATE TABLE IF NOT EXISTS company
(
    id     SERIAL PRIMARY KEY,
    symbol VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS stock
(
    id              SERIAL PRIMARY KEY,
    symbol          VARCHAR(255)   NOT NULL,
    change          NUMERIC(38, 2),
    latest_price    NUMERIC(38, 2) NOT NULL,
    previous_volume INT            NOT NULL,
    volume          INT,
    company_name    varchar(255)   NOT NULL
);