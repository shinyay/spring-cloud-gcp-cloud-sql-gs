package com.google.shinyay.repository

import com.google.shinyay.entity.Location
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : CrudRepository<Location, Long> {

    @Query("SELECT COUNT(*) FROM location")
    fun countAll(): Long
}