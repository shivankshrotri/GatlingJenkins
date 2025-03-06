package com.perf.test

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class apiTest extends Simulation
{
//http protocol
val httpProtocol1= http.baseUrl("https://reqres.in")

  // Scenario Def
  val scn= scenario("get users")
    .exec(http(requestName = "get users list").get("/api/users?page=2") ).pause(duration = 5)
  //setup

  setUp(scn.inject(rampUsers(users = 50).during(2))).protocols(httpProtocol1)

}

