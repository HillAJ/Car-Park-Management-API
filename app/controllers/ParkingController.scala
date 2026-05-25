package controllers

import models._
import play.api.libs.json._
import play.api.mvc._
import requests.{BillVehicleRequest, ParkVehicleRequest}
import services.ParkingService

import javax.inject.{Inject, Singleton}

@Singleton
class ParkingController @Inject()(
                                   cc: ControllerComponents,
                                   parkingService: ParkingService
                                 ) extends AbstractController(cc) {

  def getParking: Action[AnyContent] =
    Action {
      Ok(Json.toJson(parkingService.getParkingStatus))
    }

  def parkVehicle: Action[JsValue] =
    Action(parse.json) { request =>
      request.body.validate[ParkVehicleRequest].fold(
        _ =>
          BadRequest(Json.obj("error" -> "Invalid request")),

        parkRequest =>
          parkingService
            .parkVehicle(
              parkRequest.vehicleReg,
              parkRequest.vehicleType
            )
            .fold(
              error => BadRequest(Json.obj("error" -> error)),
              ticket =>
                Ok(
                  Json.obj(
                    "vehicleReg" -> ticket.vehicle.vehicleReg,
                    "timeIn" -> ticket.timeIn
                  )
                )
            )
      )
    }

  def billVehicle: Action[JsValue] =
    Action(parse.json) { request =>
      request.body.validate[BillVehicleRequest].fold(
        _ =>
          BadRequest(Json.obj("error" -> "Invalid request")),

        billRequest =>
          parkingService.billVehicle(billRequest.vehicleReg).fold(
            error => NotFound(Json.obj("error" -> error)),
            bill => Ok(Json.toJson(bill))
          )
      )
    }
}