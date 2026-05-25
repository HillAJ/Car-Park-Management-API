package services

import models.{Bill, ParkingTicket}

import java.time.{Duration, Instant}
import javax.inject.Singleton

@Singleton
class BillingService {

  def calculateBill(ticket: ParkingTicket, timeOut: Instant): Bill = {
    val minutesParked =
      Duration.between(ticket.timeIn, timeOut).toMinutes

    val baseCharge =
      ticket.vehicle.vehicleType.ratePerMinute * BigDecimal(minutesParked)

    val additionalCharge: BigDecimal =
      BigDecimal(minutesParked / 5)

    val formattedCharge =
      f"£${(baseCharge + additionalCharge).setScale(2)}"

    Bill(
      vehicleReg = ticket.vehicle.vehicleReg,
      vehicleCharge = formattedCharge,
      timeIn = ticket.timeIn,
      timeOut = timeOut
    )
  }
}