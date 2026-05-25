package services

import models._
import org.scalatestplus.play.PlaySpec

import java.time.Instant

class BillingServiceSpec extends PlaySpec {

  private val billingService = new BillingService

  "BillingService" should {

    "calculate bill with no additional charge under 5 minutes" in {
      val timeIn = Instant.parse("2026-05-22T10:00:00Z")
      val timeOut = Instant.parse("2026-05-22T10:03:00Z")

      val ticket = ParkingTicket(
        vehicle = Vehicle("SHORT123", VehicleType.Small),
        spaceNumber = 1,
        timeIn = timeIn
      )

      val bill = billingService.calculateBill(ticket, timeOut)

      bill.vehicleCharge mustBe "£0.30"
    }

    "calculate bill for a small car" in {
      val timeIn = Instant.parse("2026-05-22T10:00:00Z")
      val timeOut = Instant.parse("2026-05-22T10:10:00Z")

      val ticket = ParkingTicket(
        vehicle = Vehicle("ABC123", VehicleType.Small),
        spaceNumber = 1,
        timeIn = timeIn
      )

      val bill = billingService.calculateBill(ticket, timeOut)

      bill.vehicleReg mustBe "ABC123"
      bill.vehicleCharge mustBe "£3.00"
      bill.timeIn mustBe timeIn
      bill.timeOut mustBe timeOut
    }

    "calculate bill for a medium car" in {
      val timeIn = Instant.parse("2026-05-22T10:00:00Z")
      val timeOut = Instant.parse("2026-05-22T10:07:00Z")

      val ticket = ParkingTicket(
        vehicle = Vehicle("MED123", VehicleType.Medium),
        spaceNumber = 1,
        timeIn = timeIn
      )

      val bill = billingService.calculateBill(ticket, timeOut)

      bill.vehicleCharge mustBe "£2.40"
    }

    "calculate bill for a large car" in {
      val timeIn = Instant.parse("2026-05-22T10:00:00Z")
      val timeOut = Instant.parse("2026-05-22T10:05:00Z")

      val ticket = ParkingTicket(
        vehicle = Vehicle("BIG123", VehicleType.Large),
        spaceNumber = 1,
        timeIn = timeIn
      )

      val bill = billingService.calculateBill(ticket, timeOut)

      bill.vehicleCharge mustBe "£3.00"
    }
  }
}