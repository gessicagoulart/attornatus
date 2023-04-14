CREATE SCHEMA attornatus;

CREATE TABLE attornatus.person (
	id uuid PRIMARY KEY NOT NULL,
	"name" varchar NOT NULL,
	date_of_birth date NOT NULL
);
CREATE TABLE attornatus.address (
	id uuid PRIMARY KEY NOT NULL,
	person_id uuid NOT NULL,
	street varchar NOT NULL,
	post_code varchar NOT NULL,
	number varchar NOT NULL,
	is_main_address boolean NOT NULL,
	city varchar NOT NULL,
	CONSTRAINT address_fk FOREIGN KEY (person_id) REFERENCES attornatus.person(id)
);
