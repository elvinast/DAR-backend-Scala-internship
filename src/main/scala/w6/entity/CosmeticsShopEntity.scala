package w6.entity

import akka.actor.typed.Behavior
import w6.Event.{CanceledOrderEvent, ConfirmedDeliveryEvent, CosmeticsShopEvent, CreateNewCosmeticEvent, DeletedCosmeticsEvent, FindCosmeticsItemEvent, OrderedCosmeticsEvent}
import akka.cluster.sharding.typed.scaladsl.EntityTypeKey
import akka.japi.Effect
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}
import w6.command.{CosmeticsEntityCommand, CreateNewCosmeticCommand, DeleteCosmeticCommand, OrderCosmeticCommand, PayOrderCommand, SearchCosmeticCommand}

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
    case object PAY_ORDER extends CosmeticsShopEntityState
    case object FIND extends CosmeticsShopEntityState
    case object FINISH extends CosmeticsShopEntityState
    case object CANCEL extends CosmeticsShopEntityState
    case object DELETE extends CosmeticsShopEntityState

  }

  case class StateHolder(content: CosmeticsItem, state: CosmeticsShopEntityState) {

    def update(event: CosmeticsShopEvent): StateHolder = event match {
      case evt: CreateNewCosmeticEvent => {
        copy(
          content = content.copy(
            orderID = Some(evt.orderID),
            itemName = Some(evt.itemName),
            companyName = Some(evt.companyName)
          ),
          state = CosmeticsShopEntityState.FIND
        )
      }

      case evt: FindCosmeticsItemEvent => {
        copy(
          content = content.copy(
            orderID = Some(evt.orderID)
          ),
          state = CosmeticsShopEntityState.ORDER
        )
      }

      case evt: ConfirmedDeliveryEvent => {
        copy(
          content = content.copy(
            orderID = Some(evt.orderID)
          ),
          state = CosmeticsShopEntityState.FINISH
        )
      }

      case evt: CanceledOrderEvent => {
        copy(
          content = content.copy(
            orderID = Some(evt.orderID)
          ),
          state = CosmeticsShopEntityState.CANCEL
        )
      }
    }
  }

  object StateHolder {
    def empty: StateHolder = StateHolder(content = CosmeticsShop.empty, state = CosmeticsShopEntityState.INIT)
  }

  val EntityKey: EntityTypeKey[CosmeticsEntityCommand] = EntityTypeKey[CosmeticsEntityCommand]("Cosmetics Shop")

  def apply(orderID: String): Behavior[CosmeticsEntityCommand] = {
    EventSourcedBehavior[CosmeticsEntityCommand, CosmeticsShopEvent, StateHolder](
      persistenceId = PersistenceId(EntityKey.name, orderID),
      StateHolder.empty,
      (state, command) => commandHandler(orderID, state, command),
      (state, event) => handleEvent(state, event)
    )
  }


  def commandHandler(orderID: String, state: StateHolder, command: CosmeticsEntityCommand): Effect[CosmeticsShopEvent, StateHolder] = {
    command match {
      case cmd: CreateNewCosmeticCommand => {
        state.state match {
          case CosmeticsShopEntityState.INIT => {
            val evt = CreateNewCosmeticEvent(orderID = cmd.orderID, itemName = cmd.itemName, companyName = cmd.companyName)
            Effect.persist(evt)
          }
          case _ => throw new RuntimeException("Error!")
        }
      }

      case cmd: SearchCosmeticCommand =>{
        state.state match {
          case CosmeticsShopEntityState.FIND => {
            val evt = FindCosmeticsItemEvent(orderID = cmd.orderID, itemName = cmd.itemName[Option], companyName = cmd.companyName[Option])
            Effect.persist(evt)
          }
        }
      }

      case cmd: OrderCosmeticCommand =>{
        state.state match {
          case CosmeticsShopEntityState.ORDER => {
            val evt = OrderedCosmeticsEvent(orderID = cmd.orderID)
            Effect.persist(evt)
          }
        }
      }

      case cmd: DeleteCosmeticCommand =>{
        state.state match {
          case CosmeticsShopEntityState.DELETE => {
            val evt = DeletedCosmeticsEvent(orderID = cmd.orderID[Option])
            Effect.persist(evt)
          }
        }
      }

      case cmd: PayOrderCommand =>{
        state.state match {
          case CosmeticsShopEntityState.PAY_ORDER => {
            val evt = ConfirmedDeliveryEvent(orderID = cmd.orderID[Option])
            Effect.persist(evt)
          }
        }
      }
    }
  }

  def handleEvent(state: StateHolder, event: CosmeticsShopEvent): StateHolder = {
    state.update(event)
  }

}
