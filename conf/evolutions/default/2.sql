
# --- !Ups

insert into SegmentCategories (name) values ('Restaurants');
insert into SegmentCategories (name) values ('Hotels');

# --- !Downs

delete from SegmentCategories;