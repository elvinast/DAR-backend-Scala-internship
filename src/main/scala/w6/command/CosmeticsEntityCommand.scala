package w6.command

trait CosmeticsEntityCommand {
  def orderID: String
}
case class CreateNewCosmeticCommand(orderID: String, itemName: String, companyName: String)
            extends CosmeticsEntityCommand

case class SearchCosmeticCommand(orderID: String, itemName: String, companyName: String)
            extends CosmeticsEntityCommand
case class OrderCosmeticCommand(orderID: String) extends CosmeticsEntityCommand
case class DeleteCosmeticCommand(orderID: String) extends CosmeticsEntityCommand
case class PayOrderCommand(orderID: String) extends CosmeticsEntityCommand
case class CancelOrderCommand(orderID: String) extends CosmeticsEntityCommand
case class ConfirmOrderDeliveryCommand(orderID: String) extends CosmeticsEntityCommand
