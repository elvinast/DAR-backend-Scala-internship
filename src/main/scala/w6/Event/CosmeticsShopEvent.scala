package w6.Event

trait CosmeticsShopEvent

case class CreateNewCosmeticEvent(orderID: String, itemName: String, companyName: String)
          extends CosmeticsShopEvent

case class FindCosmeticsItemEvent(orderID: String, itemName: Option[String], companyName: Option[String])
          extends CosmeticsShopEvent

case class OrderedCosmeticsEvent(orderID: String) extends CosmeticsShopEvent
case class DeletedCosmeticsEvent(orderID: Option[String]) extends CosmeticsShopEvent
case class CanceledOrderEvent(orderID: String) extends CosmeticsShopEvent
case class ConfirmedDeliveryEvent(orderID: String) extends CosmeticsShopEvent
