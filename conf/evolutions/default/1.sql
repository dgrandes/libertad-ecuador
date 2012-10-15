#Venues schema

# --- !Ups
Drop Table if exists VenueTags;
Drop Table if exists SegmentTranslations;
Drop Table if exists Languages;
Drop Table if exists FilesData;
Drop Table if exists Tags;
Drop Table if exists Markers;
Drop table if exists VenueDescriptions;
Drop table if exists VenueRegions;
Drop Table if exists Venues;
Drop Table if exists Towns;
Drop table if exists Regions;
Drop Table if exists Provinces;
Drop Table if exists Countries;
Drop Table if exists Segments;
Drop Table if exists SegmentCategories;
Drop Table if exists Files;


Create Table IF NOT EXISTS SegmentCategories(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

Create Table IF NOT EXISTS Segments(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	segmentCategoryId bigint(20) NOT NULL,
	name varchar(255) NOT NULL,
	FOREIGN KEY (segmentCategoryId) REFERENCES SegmentCategories(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);

CREATE INDEX fkSegmentParentId ON Segments(segmentCategoryId);

Create Table IF NOT EXISTS Tags(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	PRIMARY KEY (id)
);

Create Table IF NOT EXISTS Countries (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	isoCode varchar(4) NOT NULL,
	PRIMARY KEY (id)
);

Create Table IF NOT EXISTS Regions (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	idCountry bigint(20) NOT NULL, 
	FOREIGN KEY (idCountry) REFERENCES Countries(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);

CREATE INDEX fkRegionsCountryId on Regions(idCountry);

Create Table IF NOT EXISTS Provinces (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	idCountry bigint(20) NOT NULL,
	FOREIGN KEY (idCountry) REFERENCES Countries(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);

CREATE INDEX fkProvincesCountryId on Provinces(idCountry);

Create Table IF NOT EXISTS Towns (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	idProvince bigint(20) NOT NULL,
	FOREIGN KEY (idProvince) REFERENCES Provinces(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);

CREATE INDEX fkTownsProvinceId on Towns(idProvince);


Create Table IF NOT EXISTS Venues(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	idSegment bigint(20) NOT NULL,
	idTown bigint(20) NOT NULL,
	FOREIGN KEY (idSegment) REFERENCES Segments(id) ON DELETE CASCADE,
	FOREIGN KEY (idTown) REFERENCES Towns(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);


CREATE INDEX fkVenueSegmentId on Venues(idSegment);
CREATE INDEX fkVenueTownId on Venues(idTown);


CREATE Table if NOT EXISTS VenueDescriptions(
	idVenue bigint(20) NOT NULL,
	email varchar(255) NOT NULL,
	phone varchar(25) NOT NULL,
	website varchar(255),
	description text NOT NULL,
	FOREIGN KEY (idVenue) REFERENCES Venues(id) on DELETE CASCADE
);

CREATE INDEX fkVenueDescriptionVenueId on VenueDescriptions(idVenue);

CREATE Table IF NOT EXISTS VenueRegions(
	idVenue bigint(20) NOT NULL,
	idRegion bigint(20) NOT NULL,
	FOREIGN KEY (idVenue) REFERENCES Venues(id) ON DELETE CASCADE,
	FOREIGN KEY (idRegion) REFERENCES Regions(id) ON DELETE CASCADE
);

CREATE INDEX fkVenueRegionsVenueId on VenueRegions(idVenue);
CREATE INDEX fkVenueRegionsRegionId on VenueRegions(idRegion);

CREATE Table IF NOT EXISTS Markers(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	idVenue bigint(20) NOT NULL,
	lat REAL NOT NULL,
	lng REAL NOT NULL,
	FOREIGN KEY (idVenue) REFERENCES Venues(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);

CREATE INDEX fkMarkersVenueId on Markers(idVenue);

Create Table IF NOT EXISTS Languages(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	translatedName varchar(255) NOT NULL,
	acceptLangHeader varchar(4) NOT NULL,
	PRIMARY KEY (id)
);

Create Table IF NOT EXISTS SegmentTranslations(
	idSegment bigint(20) NOT NULL,
	idLanguage bigint(20) NOT NULL,
	translation varchar(255) NOT NULL,
	FOREIGN KEY (idSegment) REFERENCES Segments(id) ON DELETE CASCADE,
	FOREIGN KEY (idLanguage) REFERENCES Languages(id) ON DELETE CASCADE,
	PRIMARY KEY (idSegment, idLanguage)
);

CREATE INDEX fkSegmentTranslationsSegmentId on SegmentTranslations(idSegment);
CREATE INDEX fkSegmentTranslationsLanguageId on SegmentTranslations(idLanguage);

Create Table IF NOT EXISTS VenueTags(
	idVenue bigint(20) NOT NULL,
	idTag bigint(20) NOT NULL,
	FOREIGN KEY (idVenue) REFERENCES Venues(id) ON DELETE CASCADE,
	FOREIGN KEY (idTag) REFERENCES Tags(id) ON DELETE CASCADE,
	PRIMARY KEY (idVenue, idTag)
);

CREATE INDEX fkVenueTagsVenueId on VenueTags(idVenue);
CREATE INDEX fkVenueTagsTagId on VenueTags(idTag);

Create Table IF NOT EXISTS Files (
	id bigint(20)  NOT NULL AUTO_INCREMENT,
	datatype varchar(60) NOT NULL DEFAULT 'application/octet-stream',
	name varchar(120) NOT NULL DEFAULT '',
	size bigint(20) unsigned NOT NULL DEFAULT '1024',
	filedate datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	idVenue bigint(20) NOT NULL,
	FOREIGN KEY (idVenue) REFERENCES Venues(id) ON DELETE CASCADE,
	PRIMARY KEY (id) 
) ;

CREATE INDEX fkFilesVenueId on Files(idVenue);

Create Table IF NOT EXISTS FilesData (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	idFile bigint(20) NOT NULL DEFAULT '0',
	filedata blob NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (idFile) REFERENCES Files(id) ON DELETE CASCADE
);

CREATE INDEX fkFilesDataFileId on FilesData(idFile);


# --- !Downs
Drop Table if exists VenueTags;
Drop Table if exists SegmentTranslations;
Drop Table if exists Languages;
Drop Table if exists FilesData;
Drop Table if exists Tags;
Drop Table if exists Markers;
Drop table if exists VenueDescriptions;
Drop table if exists VenueRegions;
Drop Table if exists Venues;
Drop Table if exists Towns;
Drop table if exists Regions;
Drop Table if exists Provinces;
Drop Table if exists Countries;
Drop Table if exists Segments;
Drop Table if exists SegmentCategories;
Drop Table if exists Files;

