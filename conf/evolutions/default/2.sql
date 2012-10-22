
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


# --- !Downs

delete from SegmentCategories;
delete from Segments;