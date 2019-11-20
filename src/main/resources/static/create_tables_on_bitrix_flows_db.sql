CREATE TABLE bitrix_flows.bitrix_contact (
	id varchar(10) NOT NULL,
	contact_name varchar(100) NULL,
	contact_second_name varchar(100) NULL,
	contact_last_name varchar(100) NULL,
	CONSTRAINT bitrix_contact_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

CREATE TABLE bitrix_flows.bitrix_contact_email (
	id varchar(10) NOT NULL,
	value_type varchar(100) NULL,
	value varchar(100) NULL,
	type_id varchar(100) NULL,
	contact_id varchar(10) NOT NULL,
	CONSTRAINT bitrix_contact_email_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

ALTER TABLE bitrix_flows.bitrix_contact_email ADD CONSTRAINT bitrix_contact_email_bitrix_contact_fk FOREIGN KEY (contact_id) REFERENCES bitrix_flows.bitrix_contact(id) ON DELETE CASCADE;

ALTER TABLE bitrix_flows.bitrix_contact ADD created_at TIMESTAMP NULL;
ALTER TABLE bitrix_flows.bitrix_contact ADD updated_at timestamp NULL;

ALTER TABLE bitrix_flows.bitrix_contact_email ADD created_at TIMESTAMP NULL;
ALTER TABLE bitrix_flows.bitrix_contact_email ADD updated_at TIMESTAMP NULL;

ALTER TABLE bitrix_flows.bitrix_contact ADD partner_code VARCHAR(100) NULL;

ALTER TABLE bitrix_flows.bitrix_contact ADD birthday DATETIME NULL;