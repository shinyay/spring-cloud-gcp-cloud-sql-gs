package com.google.shinyay.controller

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController(val jdbcTemplate: JdbcTemplate) {
}