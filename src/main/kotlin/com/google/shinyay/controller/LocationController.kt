package com.google.shinyay.controller

import com.google.shinyay.service.LocationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LocationController(val service: LocationService) {


}