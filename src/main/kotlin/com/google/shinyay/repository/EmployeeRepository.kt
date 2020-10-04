package com.google.shinyay.repository

import com.google.shinyay.entity.EmployeeRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class EmployeeRepository(val jdbcTemplate: JdbcTemplate) {

    fun getEmployees(): List<String>? = jdbcTemplate.queryForList("SELECT * FROM employee")
            .map { m: Map<String?, Any?> -> m.values.toString() }
            .toList()

    fun getEmployeeByName(name: String){
        jdbcTemplate.queryForObject(
                "SELECT * FROM EMPLOYEE WHERE employee_name = ?",
                arrayOf<Any>(name),
                EmployeeRowMapper()
        )
    }
}