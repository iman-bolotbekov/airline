CREATE TABLE search_results
(
    id BIGINT NOT NULL,
    CONSTRAINT "search_resultsPK" PRIMARY KEY (id)
);

ALTER TABLE search_results
    ADD CONSTRAINT "FKtja6uk2e7xcysfgqp5wj1yunp" FOREIGN KEY (id) REFERENCES searches (id);