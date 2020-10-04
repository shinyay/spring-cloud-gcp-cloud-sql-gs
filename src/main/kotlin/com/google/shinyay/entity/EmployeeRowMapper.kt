package com.google.shinyay.entity

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class EmployeeRowMapper : RowMapper<Employee> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Employee? {
        return Employee(
                rs.getLong("employee_id"),
                rs.getString("employee_name"),
                rs.getString("role"),
                rs.getLong("department_id")
        )
    }
}