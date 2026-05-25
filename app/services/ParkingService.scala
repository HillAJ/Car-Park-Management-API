package services

import models._
import repositories.ParkingRepository

import java.time.Instant
import javax.inject.{Inject, Singleton}

@Singleton
class ParkingService @Inject()(
                                parkingRepository: ParkingRepository,
                                billingService: BillingService
                              ) {

  def getParkingStatus: ParkingStatus =
    ParkingStatus(
      availableSpaces = parkingRepository.availableSpaces,
      occupiedSpaces = parkingRepository.occupiedSpaces
    )

  def parkVehicle(
                   vehicleReg: String,
                   vehicleTypeId: Int
                 ): Either[String, ParkingTicket] = {

    if (vehicleReg.trim.isEmpty) {
      Left("Vehicle registration cannot be empty")
    } else if (parkingRepository.findByRegistration(vehicleReg).isDefined) {
      Left("Vehicle is already parked")
    } else {
      VehicleType.fromInt(vehicleTypeId) match {
        case None =>
          Left("Vehicle type must be 1, 2, or 3")

        case Some(vehicleType) =>
          parkingRepository.firstAvailableSpace match {
            case None =>
              Left("No parking spaces available")

            case Some(spaceNumber) =>
              val ticket =
                ParkingTicket(
                  vehicle = Vehicle(vehicleReg, vehicleType),
                  spaceNumber = spaceNumber,
                  timeIn = Instant.now()
                )

              parkingRepository.parkVehicle(ticket)
          }
      }
    }
  }

  def billVehicle(vehicleReg: String): Either[String, Bill] = {
    parkingRepository.findByRegistration(vehicleReg) match {
      case None =>
        Left("Vehicle registration not found")

      case Some(ticket) =>
        val timeOut = Instant.now()
        val bill = billingService.calculateBill(ticket, timeOut)

        parkingRepository.remove(vehicleReg)

        Right(bill)
    }
  }
}