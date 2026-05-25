package models

import org.scalatestplus.play.PlaySpec

class VehicleTypeSpec extends PlaySpec {

  "VehicleType" should {

    "convert valid ids to vehicle types" in {
      VehicleType.fromInt(1) mustBe Some(VehicleType.Small)
      VehicleType.fromInt(2) mustBe Some(VehicleType.Medium)
      VehicleType.fromInt(3) mustBe Some(VehicleType.Large)
    }

    "return None for invalid ids" in {
      VehicleType.fromInt(0) mustBe None
      VehicleType.fromInt(4) mustBe None
      VehicleType.fromInt(99) mustBe None
    }
  }
}