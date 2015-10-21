package com.emaginalabs.towerlocator.controller

import com.twitter.finagle.httpx.Request
import com.twitter.finatra.http.Controller

/**
 * @author Sergio Arroyo - @delr3ves
 */
class UserController extends Controller {


  get("user/:id/location") { request: Request =>
    "last known location"
  }

  post("user/:id/location") { request: Request =>
    "location created"
  }

  get("user/:id/locations") { request: Request =>
    "last locations"
  }

}
