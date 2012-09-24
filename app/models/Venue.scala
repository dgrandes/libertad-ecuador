package models

case class VenueModel(
  name: String, 
  phone: String,
  email: String,
  tags: Option[String],
  description: Option[String],
  place: LocationModel
)

case class LocationModel(
	city: String,
	state: String,
	country: String
)