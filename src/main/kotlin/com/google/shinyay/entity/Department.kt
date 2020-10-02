package com.google.shinyay.entity

import javax.persistence.*

@Entity
data class Department(val department_id: Long,
                      val name: String)