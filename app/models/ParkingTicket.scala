package models

import java.time.Instant

case class ParkingTicket(
                          vehicle: Vehicle,
                          spaceNumber: Int,
                          timeIn: Instant
                        )