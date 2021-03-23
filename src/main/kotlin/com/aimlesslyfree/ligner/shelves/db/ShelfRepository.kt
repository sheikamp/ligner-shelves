package com.aimlesslyfree.ligner.shelves.db

import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShelfRepository: CoroutineCrudRepository<Shelf, ObjectId> {

    fun findByUserId(userId: Int): Flow<Shelf>
}