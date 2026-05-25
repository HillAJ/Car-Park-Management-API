package repositories

import models.ParkingTicket

import javax.inject.Singleton
import scala.collection.mutable

@Singleton
class InMemoryParkingRepository extends ParkingRepository {

  private val totalSpaces: Int = 20

  private val parkedVehicles: mutable.Map[String, ParkingTicket] =
    mutable.Map.empty[String, ParkingTicket]

  override def findByRegistration(vehicleReg: String): Option[ParkingTicket] =
    parkedVehicles.get(vehicleReg)

  override def save(ticket: ParkingTicket): ParkingTicket = {
    parkedVehicles += ticket.vehicle.vehicleReg -> ticket
    ticket
  }

  override def remove(vehicleReg: String): Option[ParkingTicket] =
    parkedVehicles.remove(vehicleReg)

  override def occupiedSpaces: Int =
    parkedVehicles.size

  override def availableSpaces: Int =
    totalSpaces - occupiedSpaces

  override def firstAvailableSpace: Option[Int] = {
    val occupiedSpaceNumbers =
      parkedVehicles.values.map(_.spaceNumber).toSet

    (1 to totalSpaces).find(spaceNumber =>
      !occupiedSpaceNumbers.contains(spaceNumber)
    )
  }

  override def parkVehicle(
                            ticket: ParkingTicket
                          ): Either[String, ParkingTicket] =
    synchronized {

      if (
        parkedVehicles.contains(
          ticket.vehicle.vehicleReg
        )
      ) {

        Left("Vehicle is already parked")

      } else if (
        availableSpaces == 0
      ) {

        Left("No parking spaces available")

      } else {

        parkedVehicles +=
          ticket.vehicle.vehicleReg -> ticket

        Right(ticket)

      }
    }
}