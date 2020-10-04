package com.google.shinyay.repository

import com.google.shinyay.entity.Employee
import com.google.shinyay.entity.EmployeeRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import java.sql.ResultSet


@Repository
class EmployeeRepository(val jdbcTemplate: JdbcTemplate) {

    fun getEmployees(): List<String>? = jdbcTemplate.queryForList("SELECT * FROM employee")
            .map { m: Map<String?, Any?> -> m.values.toString() }
            .toList()

    fun getEmployeeByName(name: String): Employee? {
        jdbcTemplate.queryForObject(
                "SELECT * FROM employee WHERE employee_name = ?",
                arrayOf<Any>(name),
                EmployeeRowMapper()
        )
    }
}