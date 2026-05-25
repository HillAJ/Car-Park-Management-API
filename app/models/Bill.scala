package models

import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class Bill(
                 vehicleReg: String,
                 vehicleCharge: String,
                 timeIn: Instant,
                 timeOut: Instant
               )

object Bill {
  implicit val format: OFormat[Bill] = Json.format[Bill]
}