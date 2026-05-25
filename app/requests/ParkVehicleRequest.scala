package requests

import play.api.libs.json.{Json, OFormat}

case class ParkVehicleRequest(
                               vehicleReg: String,
                               vehicleType: Int
                             )

object ParkVehicleRequest {
  implicit val format: OFormat[ParkVehicleRequest] =
    Json.format[ParkVehicleRequest]
}