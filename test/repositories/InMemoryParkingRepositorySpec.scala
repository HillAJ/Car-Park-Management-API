// test/repositories/InMemoryParkingRepositorySpec.scala
package repositories

import models._
import org.scalatestplus.play.PlaySpec

import java.time.Instant

class InMemoryParkingRepositorySpec extends PlaySpec {

  "InMemoryParkingRepository" should {

    "start with all spaces available" in {
      val repository = new InMemoryParkingRepository

      repository.availableSpaces mustBe 20
      repository.occupiedSpaces mustBe 0
    }

    "save and find a parked vehicle" in {
      val repository = new InMemoryParkingRepository

      val ticket = ParkingTicket(
        vehicle = Vehicle("ABC123", VehicleType.Small),
        spaceNumber = 1,
        timeIn = Instant.now()
      )

      repository.save(ticket)

      repository.findByRegistration("ABC123") mustBe Some(ticket)
      repository.occupiedSpaces mustBe 1
      repository.availableSpaces mustBe 19
    }

    "remove a parked vehicle" in {
      val repository = new InMemoryParkingRepository

      val ticket = ParkingTicket(
        vehicle = Vehicle("ABC123", VehicleType.Small),
        spaceNumber = 1,
        timeIn = Instant.now()
      )

      repository.save(ticket)

      repository.remove("ABC123") mustBe Some(ticket)
      repository.findByRegistration("ABC123") mustBe None
      repository.occupiedSpaces mustBe 0
      repository.availableSpaces mustBe 20
    }

    "return None when removing an unknown vehicle" in {
      val repository = new InMemoryParkingRepository

      repository.remove("UNKNOWN") mustBe None
    }

    "return first available space" in {
      val repository = new InMemoryParkingRepository

      val ticket = ParkingTicket(
        vehicle = Vehicle("ABC123", VehicleType.Small),
        spaceNumber = 1,
        timeIn = Instant.now()
      )

      repository.save(ticket)

      repository.firstAvailableSpace mustBe Some(2)
    }

    "reject duplicate vehicle registration when parking vehicle" in {
      val repository = new InMemoryParkingRepository

      val firstTicket = ParkingTicket(
        vehicle = Vehicle("ABC123", VehicleType.Small),
        spaceNumber = 1,
        timeIn = Instant.now()
      )

      val duplicateTicket = ParkingTicket(
        vehicle = Vehicle("ABC123", VehicleType.Medium),
        spaceNumber = 2,
        timeIn = Instant.now()
      )

      repository.parkVehicle(firstTicket)
      val result = repository.parkVehicle(duplicateTicket)

      result mustBe Left("Vehicle is already parked")
      repository.occupiedSpaces mustBe 1
    }
  }
}