package services

import org.scalatestplus.play.PlaySpec
import repositories.InMemoryParkingRepository

class ParkingServiceSpec extends PlaySpec {

  "ParkingService" should {

    "return initial parking status" in {
      val repository = new InMemoryParkingRepository
      val service = new ParkingService(repository, new BillingService)

      val status = service.getParkingStatus

      status.availableSpaces mustBe 20
      status.occupiedSpaces mustBe 0
    }

    "park a valid vehicle" in {
      val repository = new InMemoryParkingRepository
      val service = new ParkingService(repository, new BillingService)

      val result = service.parkVehicle("ABC123", 1)

      result.isRight mustBe true
      service.getParkingStatus.availableSpaces mustBe 19
      service.getParkingStatus.occupiedSpaces mustBe 1
    }

    "reject an invalid vehicle type" in {
      val repository = new InMemoryParkingRepository
      val service = new ParkingService(repository, new BillingService)

      val result = service.parkVehicle("ABC123", 9)

      result mustBe Left("Vehicle type must be 1, 2, or 3")
    }

    "reject duplicate vehicle registration" in {
      val repository = new InMemoryParkingRepository
      val service = new ParkingService(repository, new BillingService)

      service.parkVehicle("ABC123", 1)
      val result = service.parkVehicle("ABC123", 1)

      result mustBe Left("Vehicle is already parked")
    }

    "bill a parked vehicle and free the space" in {
      val repository = new InMemoryParkingRepository
      val service = new ParkingService(repository, new BillingService)

      service.parkVehicle("ABC123", 1)

      val billResult = service.billVehicle("ABC123")

      billResult.isRight mustBe true
      service.getParkingStatus.availableSpaces mustBe 20
      service.getParkingStatus.occupiedSpaces mustBe 0
    }

    "return error when billing unknown vehicle" in {
      val repository = new InMemoryParkingRepository
      val service = new ParkingService(repository, new BillingService)

      val result = service.billVehicle("UNKNOWN")

      result mustBe Left("Vehicle registration not found")
    }
  }
}