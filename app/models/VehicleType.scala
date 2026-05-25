package models

sealed trait VehicleType {
  def ratePerMinute: BigDecimal
}

object VehicleType {
  case object Small extends VehicleType {
    override val ratePerMinute: BigDecimal = BigDecimal("0.10")
  }

  case object Medium extends VehicleType {
    override val ratePerMinute: BigDecimal = BigDecimal("0.20")
  }

  case object Large extends VehicleType {
    override val ratePerMinute: BigDecimal = BigDecimal("0.40")
  }

  def fromInt(value: Int): Option[VehicleType] =
    value match {
      case 1 => Some(Small)
      case 2 => Some(Medium)
      case 3 => Some(Large)
      case _ => None
    }
}