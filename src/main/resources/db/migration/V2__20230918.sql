CREATE TYPE dml_type AS ENUM ('INSERT', 'UPDATE', 'DELETE');

CREATE TABLE IF NOT EXISTS stock_audit_log
(
    id            BIGINT    NOT NULL,
    old_row_data  jsonb,
    new_row_data  jsonb,
    dml_type      dml_type  NOT NULL,
    dml_timestamp TIMESTAMP NOT NULL
)