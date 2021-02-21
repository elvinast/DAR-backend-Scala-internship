package w6.entity

import w6.Event.CosmeticsShopEvent

object CosmeticsShopEntity {
  case class CosmeticsItem(itemName: Option[String] = None,
                           orderID: Option[String] = None,
                           companyName: Option[String] = None)

  object CosmeticsShop {
    def empty = new CosmeticsItem()
  }

  trait State
  trait CosmeticsShopEntityState

  object CosmeticsShopEntityState {

    case object ORDER extends CosmeticsShopEntityState
    case object INIT extends CosmeticsShopEntityState
    case object PAY extends CosmeticsShopEntityState
    case object FIND extends CosmeticsShopEntityState
    case object FINISH extends CosmeticsShopEntityState

  }

  case class StateHolder(content: CosmeticsItem, state: CosmeticsShopEntityState) {

    def update(event: CosmeticsShopEvent): StateHolder = event match {
      case evt: CosmeticsShopEvent => {
        copy(
          content = content.copy(
            orderID = Some(evt.orderID),
            itemName = Some(evt.itemName),
            companyName = Some(evt.companyName)
          ),
          state = CosmeticsShopEntityState.FIND
        )
      }
    }
  }
}
