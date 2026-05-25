package repositories

import models.ParkingTicket

trait ParkingRepository {
  def findByRegistration(vehicleReg: String): Option[ParkingTicket]

  def save(ticket: ParkingTicket): ParkingTicket

  def remove(vehicleReg: String): Option[ParkingTicket]

  def occupiedSpaces: Int

  def availableSpaces: Int

  def firstAvailableSpace: Option[Int]

  def parkVehicle(ticket: ParkingTicket): Either[String, ParkingTicket]
}