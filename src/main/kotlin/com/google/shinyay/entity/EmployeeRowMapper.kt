package com.google.shinyay.entity

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class EmployeeRowMapper(val employee: Employee) : RowMapper<Employee> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Employee? {
        TODO("Not yet implemented")
    }
}