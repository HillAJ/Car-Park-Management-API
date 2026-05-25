import com.google.inject.AbstractModule
import repositories.{InMemoryParkingRepository, ParkingRepository}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ParkingRepository])
      .to(classOf[InMemoryParkingRepository])
  }
}