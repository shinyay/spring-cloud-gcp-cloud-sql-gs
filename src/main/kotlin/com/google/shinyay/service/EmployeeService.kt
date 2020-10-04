package com.google.shinyay.service

import com.google.shinyay.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(val repository: EmployeeRepository) {
}