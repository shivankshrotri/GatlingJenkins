package com.perf.test

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GlobalSearch extends Simulation {

  private val httpProtocol = http.baseUrl("https://api-test.accesscorp.com")
		.header(name="authorization",value = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJ6Y1UtWi02YVdoM2h2QkhsY1ZEOSJ9.eyJodHRwczovL2NvbS5hY2Nlc3Njb3JwL2VtYWlsIjoiYWRtaW5AYWNjZXNzY29ycC5jb20iLCJodHRwczovL2NvbS5hY2Nlc3Njb3JwL3VzZXJOYW1lIjoiYWRtaW4iLCJpc3MiOiJodHRwczovL2NzcGF1dGgwdHN0LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2MTFlMGM0N2MxZDM5MTAwNjljNmUxOWQiLCJhdWQiOiJhcGktdHN0LWFjY2Vzc2NvcnAiLCJpYXQiOjE3NDE2ODk1NDksImV4cCI6MTc0MTY5MjI0OSwiZ3R5IjoicGFzc3dvcmQiLCJhenAiOiJtRmFZeHVNR0NucTNEYk1LUzhsRGhGS2lVNkJqRjkyRiIsInBlcm1pc3Npb25zIjpbXX0.Gph9miibbn5VLvEx-w8YSu3vLuvzybJNwtVzzJtJkEOcP6uh5PJ2crh-QcBqvT3hbrfSC6Y-DVCW4HH0Dy71-9acwpZDvVJFPZ4sgagqXoAfKMQrRtlGSGF3vbBArFm_T_cpBpzTNjVXDeS8MogmQgVs0A-i_OA1SKqqqAEd1wt1wQbUoO3ToXq4T2qvOPu9CKP0MwXjhMCIiIOiF5jgInjZ-BA3cV_ysUQRK4HlwgZy1ncKAzgnurefVYjKrUkHeV64OFjjLvQve0mZrm4sledllBb6JRlI6diA0y9h-rAMLx-ShulHba4clHMprEzhWUddoap3WfA1lIyVpWDBqA")
		.header(name="Content-Type",value = "application/json")
		.header(name = "accept",value = "application/json, text/plain, */*")

//Scenario Def
	private val scn = scenario("Search")
    .exec(
      http("Search API")

            .post("/v1/search?limit=25&offset=0")

            .body(RawFileBody("0001_request.json"))
				.check(status is 200)
				.check(jsonPath(path = "$[0].traceId").saveAs("traceID")).asJson
		)

		http(requestName = "count API")
			.get("/v1/search/count?traceId=${traceID}")
			.check(status is 200)

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
