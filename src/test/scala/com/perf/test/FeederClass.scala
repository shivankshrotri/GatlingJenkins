package com.perf.test
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class FeederClass extends Simulation  {

  // http config

 val httpurl= http. baseUrl("https://reqres.in/")
  val csvFeeder=  csv("request/users.csv").circular
  val scn=scenario("Get users").repeat(times = 1)
  {
    feed(csvFeeder).exec(http("get users").get(s"api/users/#{userId}")
      .check(bodyString.saveAs("BODY"))
      .check(status is 200)

    )

      .exec(session => {
     val response = session("BODY").as[String]
     println(s"Response body: \n$response")
     session
    })
  }



setUp(scn.inject(atOnceUsers(users = 10)).protocols(httpurl))




}


