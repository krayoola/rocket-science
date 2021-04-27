package science.protocol

// TODO: Create a domain type for id, name, description and possible price
case class Item(id: Option[String], name: String, description: String, price: Int)