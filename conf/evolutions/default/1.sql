#Venues schema

# --- !Ups

CREATE TABLE Venues(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	PRIMARY KEY (id)
);

# --- !Downs
Drop table Venues;