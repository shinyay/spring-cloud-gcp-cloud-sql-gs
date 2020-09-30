package com.google.shinyay.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class EmployeeRepository(val jdbcTemplate: JdbcTemplate) {

    fun getEmployees(): List<String>? = jdbcTemplate.queryForList("SELECT * FROM employee")
            .map { m: Map<String?, Any?> -> m.values.toString() }
            .toList()
}