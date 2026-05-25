package requests

import play.api.libs.json.{Json, OFormat}

case class BillVehicleRequest(
                               vehicleReg: String
                             )

object BillVehicleRequest {
  implicit val format: OFormat[BillVehicleRequest] =
    Json.format[BillVehicleRequest]
}