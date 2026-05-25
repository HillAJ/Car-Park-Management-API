package models

import play.api.libs.json.{Json, OFormat}

case class ParkingStatus(
                          availableSpaces: Int,
                          occupiedSpaces: Int
                        )

object ParkingStatus {
  implicit val format: OFormat[ParkingStatus] = Json.format[ParkingStatus]
}