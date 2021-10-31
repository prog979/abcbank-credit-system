CREATE TABLE IF NOT EXISTS clients
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    secname VARCHAR (255),
    name VARCHAR (255),
    patronymic VARCHAR (255),
    pasport_number VARCHAR (255),
    email VARCHAR (255),
    phone_number VARCHAR (255)
    )

CREATE TABLE IF NOT EXISTS credits
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    credit_name VARCHAR (255),
    credit_limit BIGINT NOT NULL,
    percent integer NOT NULL)

CREATE TABLE IF NOT EXISTS credit_offers
(
    id               BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    credit_sum       BIGINT,
    months_of_credit BIGINT,
    client_id        VARCHAR(255) NOT NULL,
    credit_id        VARCHAR(255) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE,
    FOREIGN KEY (credit_id) REFERENCES credit (id) ON DELETE CASCADE
)

CREATE TABLE IF NOT EXISTS payment_schedules
(
    id              BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    credit_body     BIGINT,
    credit_percents BIGINT,
    date            Date,
    payment_rest    BIGINT,
    payment_sum     BIGINT,
    credit_offer_id VARCHAR(255) not null,
    FOREIGN KEY (credit_offer_id) REFERENCES credit_offer (id)
)