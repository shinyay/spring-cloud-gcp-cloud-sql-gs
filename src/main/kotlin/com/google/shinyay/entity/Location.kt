package com.google.shinyay.entity

import org.springframework.data.annotation.Id

data class Location(@Id val id: Long,
                    val city: String,
                    val postal_code: String)