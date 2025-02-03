CREATE TABLE donor
(
    id             BINARY(16)   NOT NULL,
    name           VARCHAR(255) NULL,
    cpf            VARCHAR(14)  NULL,
    rg             VARCHAR(12)  NULL,
    birth_date     VARCHAR(255) NOT NULL,
    gender         VARCHAR(255) NULL,
    mother_name    VARCHAR(255) NULL,
    father_name    VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    zip_code       VARCHAR(255) NULL,
    address        VARCHAR(255) NULL,
    number         INT          NOT NULL,
    neighborhood   VARCHAR(255) NULL,
    city           VARCHAR(255) NULL,
    state          VARCHAR(2)   NULL,
    landline_phone VARCHAR(255) NULL,
    mobile_phone   VARCHAR(255) NULL,
    weight         DOUBLE       NOT NULL,
    height         DOUBLE       NOT NULL,
    blood_type     VARCHAR(255) NULL,
    created_at     datetime     NULL,
    CONSTRAINT pk_donor PRIMARY KEY (id)
);

ALTER TABLE donor
    ADD CONSTRAINT uc_donor_cpf UNIQUE (cpf);

ALTER TABLE donor
    ADD CONSTRAINT uc_donor_rg UNIQUE (rg);