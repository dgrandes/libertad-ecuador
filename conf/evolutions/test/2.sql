
# --- !Ups

insert into SegmentCategories (name) values ('Restaurants');
insert into SegmentCategories (name) values ('Hotels');

insert into Segments(name, segmentCategoryId) values ('Dinner',1);
insert into Segments(name, segmentCategoryId) values ('Fast Food',1);
insert into Segments(name, segmentCategoryId) values ('Lunch',1);
insert into Segments(name, segmentCategoryId) values ('Take out',1);

insert into Segments(name, segmentCategoryId) values ('Hostel',2);
insert into Segments(name, segmentCategoryId) values ('5 Stars',2);
insert into Segments(name, segmentCategoryId) values ('4 Stars',2);

insert into Venues(name) values ('My first Venue');
insert into VenueDescriptions(idVenue, email, phone, website, description) values (1, 'first@gmail.com', 1, 'first@trip.com', 'Im the first yo');

insert into Venues(name) values ('My second Venue');
insert into VenueDescriptions(idVenue, email, phone, website, description) values (2, 'second@gmail.com', 2, null, 'Im the second yo');		

insert into VenueSegments(idVenue, idSegment) values(1,1);
insert into VenueSegments(idVenue, idSegment) values(1,2);

# --- !Downs

delete from SegmentCategories;
delete from Segments;
delete from Venues;
delete from VenueDescriptions;
delete from VenueSegments;