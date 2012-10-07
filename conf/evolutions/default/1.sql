#Venues schema

# --- !Ups

CREATE TABLE SegmentCategories(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE Segments(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	parentId bigint(20) NOT NULL,
	INDEX (parentId),
	FOREIGN KEY (parentId) REFERENCES SegmentCategories(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE Tags(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE Venues(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	idSegment bigint(20) NOT NULL,
	INDEX (idSegment),
	FOREIGN KEY (idSegment) REFERENCES Segments(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE Languages(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	translatedName varchar(255) NOT NULL,
	acceptLangHeader varchar(4) NOT NULL,
	PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE SegmentTranslations(
	idSegment bigint(20) NOT NULL,
	idLanguage bigint(20) NOT NULL,
	translation varchar(255) NOT NULL,
	INDEX (idSegment),
	INDEX (idLanguage),
	FOREIGN KEY (idSegment) REFERENCES Segments(id) ON DELETE CASCADE,
	FOREIGN KEY (idLanguage) REFERENCES Languages(id) ON DELETE CASCADE,
	PRIMARY KEY (idSegment, idLanguage)
)ENGINE=InnoDB;

Create TABLE VenueTags(
	idVenue bigint(20) NOT NULL,
	idTag bigint(20) NOT NULL,
	INDEX (idVenue),
	INDEX (idTag),
	FOREIGN KEY (idVenue) REFERENCES Venues(id) ON DELETE CASCADE,
	FOREIGN KEY (idTag) REFERENCES Tags(id) ON DELETE CASCADE,
	PRIMARY KEY (idVenue, idTag)
)ENGINE=InnoDB;

CREATE TABLE Files (
	id bigint(20)  NOT NULL AUTO_INCREMENT,
	datatype varchar(60) NOT NULL DEFAULT 'application/octet-stream',
	name varchar(120) NOT NULL DEFAULT '',
	size bigint(20) unsigned NOT NULL DEFAULT '1024',
	filedate datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	PRIMARY KEY (id) 
) ENGINE=InnoDB;

CREATE TABLE FilesData (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	idFile bigint(20) NOT NULL DEFAULT '0',
	filedata blob NOT NULL,
	PRIMARY KEY (id),
	INDEX (idFile),
	FOREIGN KEY (idFile) REFERENCES Files(id) ON DELETE CASCADE
)ENGINE=InnoDB;


# --- !Downs
Drop table VenueTags;
Drop table SegmentTranslations;
Drop table Languages;
Drop table FilesData;
Drop table Tags;
Drop table Venues;
Drop table Segments;
Drop table SegmentCategories;
Drop table Files;