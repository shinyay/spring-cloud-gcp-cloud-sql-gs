package com.google.shinyay.service

import com.google.shinyay.entity.Department
import com.google.shinyay.repository.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(val repository: DepartmentRepository) {

    fun getDepartmentById(departmentId: Int) = repository.findById(departmentId)

    fun getDepartmentList(): List<Department> = repository.findAll()
}