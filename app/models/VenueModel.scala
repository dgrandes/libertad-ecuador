package models

case class VenueModel(
  name: String, 
  phone: String,
  email: String,
  tags: Option[String],
  description: Option[String],
  segmentId: Long,
  place: LocationModel
)

case class LocationModel(
	city: String,
	state: String,
	country: String
)

object VenueModel{
	def all(): List[VenueModel] = Nil

	def create(label: String){}

	def delete(id: Long) {}
}