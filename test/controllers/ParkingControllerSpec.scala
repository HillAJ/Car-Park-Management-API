package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

class ParkingControllerSpec
  extends PlaySpec
    with GuiceOneAppPerTest {

  "ParkingController" should {

    "return parking status" in {
      val request =
        FakeRequest(GET, "/parking")

      val response =
        route(app, request).get

      status(response) mustBe OK
      contentAsJson(response) mustBe Json.obj(
        "availableSpaces" -> 20,
        "occupiedSpaces" -> 0
      )
    }

    "show one occupied space after parking a vehicle" in {

      val parkRequest =
        FakeRequest(POST, "/parking")
          .withJsonBody(
            Json.obj(
              "vehicleReg" -> "ABC123",
              "vehicleType" -> 1
            )
          )

      val parkResponse =
        route(app, parkRequest).get

      status(parkResponse) mustBe OK

      val statusRequest =
        FakeRequest(GET, "/parking")

      val response =
        route(app, statusRequest).get

      status(response) mustBe OK

      contentAsJson(response) mustBe Json.obj(
        "availableSpaces" -> 19,
        "occupiedSpaces" -> 1
      )
    }

    "park a vehicle" in {
      val request =
        FakeRequest(POST, "/parking")
          .withJsonBody(
            Json.obj(
              "vehicleReg" -> "ABC123",
              "vehicleType" -> 1
            )
          )

      val response =
        route(app, request).get

      status(response) mustBe OK

      val json = contentAsJson(response)

      (json \ "vehicleReg").as[String] mustBe "ABC123"
      (json \ "timeIn").asOpt[String].isDefined mustBe true
    }

    "reject invalid vehicle type" in {
      val request =
        FakeRequest(POST, "/parking")
          .withJsonBody(
            Json.obj(
              "vehicleReg" -> "ABC123",
              "vehicleType" -> 99
            )
          )

      val response =
        route(app, request).get

      status(response) mustBe BAD_REQUEST
      contentAsJson(response) mustBe Json.obj(
        "error" -> "Vehicle type must be 1, 2, or 3"
      )
    }

    "return not found when billing unknown vehicle" in {
      val request =
        FakeRequest(POST, "/parking/bill")
          .withJsonBody(
            Json.obj(
              "vehicleReg" -> "UNKNOWN"
            )
          )

      val response =
        route(app, request).get

      status(response) mustBe NOT_FOUND
      contentAsJson(response) mustBe Json.obj(
        "error" -> "Vehicle registration not found"
      )
    }
  }
}