CREATE TABLE PUBLIC.BLOCK (
	END_DATE DATE,
	START_DATE DATE,
	ID BIGINT NOT NULL AUTO_INCREMENT,
	CONSTRAINT CONSTRAINT_3 PRIMARY KEY (ID)
);
CREATE TABLE PUBLIC.BOOKING (
	ACTIVE BOOLEAN,
	END_DATE DATE,
	START_DATE DATE,
	ID BIGINT NOT NULL AUTO_INCREMENT,
	GUEST_NAME CHARACTER VARYING(255),
	CONSTRAINT CONSTRAINT_2 PRIMARY KEY (ID)
);