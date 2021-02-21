package w6.Event

trait CosmeticsShopEvent

case class CreateNewCosmeticCommand(orderID: String,
                                    itemName: String,
                                    companyName: String) extends CosmeticsShopEvent

case class FindCosmeticsItemEvent(orderID: String,
                                  itemName: Option[String],
                                  companyName: Option[String]) extends CosmeticsShopEvent

case class OrderedCosmetics(orderID: String) extends CosmeticsShopEvent
case class SellCosmeticsEvent(orderID: String) extends CosmeticsShopEvent
