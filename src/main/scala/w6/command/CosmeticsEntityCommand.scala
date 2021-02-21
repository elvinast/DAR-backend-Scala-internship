package w6.command

trait CosmeticsEntityCommand {
  def orderID: String
}

case class CreateNewCosmeticCommand(orderID: String,
                                    itemName: String,
                                    companyName: String) extends CosmeticsEntityCommand
case class SearchCosmeticCommand(orderID: String, itemName: String, companyName: String) extends CosmeticsEntityCommand
case class OrderCommand(orderID: String) extends CosmeticsEntityCommand
case class PayOrderCommand(orderID: String) extends CosmeticsEntityCommand
