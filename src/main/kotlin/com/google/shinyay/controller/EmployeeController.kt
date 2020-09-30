package com.google.shinyay.controller

import com.google.shinyay.repository.EmployeeRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController(val repository: EmployeeRepository) {

    @GetMapping("/employees")
    fun findAllEmployees() = repository.getEmployees()
}