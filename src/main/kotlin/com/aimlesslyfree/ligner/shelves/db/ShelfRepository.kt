package com.aimlesslyfree.ligner.shelves.db

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShelfRepository: CoroutineCrudRepository<Shelf, ObjectId> {
    suspend fun findByName(name: String)
}