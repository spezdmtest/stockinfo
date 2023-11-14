ALTER TABLE stock_audit_log
    ADD PRIMARY KEY (id, dml_type, dml_timestamp)