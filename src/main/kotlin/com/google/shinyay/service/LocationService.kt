package com.google.shinyay.service

import com.google.shinyay.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(val repository: LocationRepository) {

    fun countLocations() = repository.countAll()
}