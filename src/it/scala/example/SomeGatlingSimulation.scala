package example

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.Duration


class SomeGatlingSimulation extends Simulation {

  import SomeGatlingSimulation._

  val scn = scenario("Some gatling scenario")
    .exec(http("Hit sample gatling database")
        .get("/computers")
        .check(status.is(200))
    )

  setUp(scn.inject(constantUsersPerSec(userPerSec) during duration)
      .protocols(http.baseURL(baseUrl))
  )
}

object SomeGatlingSimulation {

  val scenarioConfig = ConfigFactory.load("SomeGatlingSimulation.conf").getConfig("simulation")

  val baseUrl = scenarioConfig.getString("baseUrl")

  val userPerSec = scenarioConfig.getInt("constantUserPerSec")

  val duration = Duration.fromNanos(scenarioConfig.getDuration("duration").toNanos)
}
