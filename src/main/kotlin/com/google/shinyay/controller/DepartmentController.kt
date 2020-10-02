package com.google.shinyay.controller

import com.google.shinyay.service.DepartmentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DepartmentController(val service: DepartmentService) {

    @GetMapping("/departments")
    fun getDepartments() = service.getDepartmentList()
}