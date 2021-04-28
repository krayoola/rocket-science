package science.protocol

case class Purchase(ids: List[String])

case class PurchaseResponse(item: List[Item], totalValue: Int)

