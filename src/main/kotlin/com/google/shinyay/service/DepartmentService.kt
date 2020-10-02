package com.google.shinyay.service

import com.google.shinyay.repository.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(val repository: DepartmentRepository) {

    fun getDepartmentById(departmentId: Int) = repository.findById(departmentId)
}