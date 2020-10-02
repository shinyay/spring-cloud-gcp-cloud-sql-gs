package com.google.shinyay.entity

import javax.persistence.*

@Entity
@Table(name = "department")
data class Department(@Id @GeneratedValue(strategy = GenerationType.AUTO) val department_id: Long,
                      val name: String)