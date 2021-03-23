package com.aimlesslyfree.ligner.shelves.graphql

import com.aimlesslyfree.ligner.shelves.db.Shelf
import com.aimlesslyfree.ligner.shelves.db.ShelfRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import kotlinx.coroutines.runBlocking

@DgsComponent
class ShelfMutation(
        val shelfRepository: ShelfRepository
) {

    @DgsData(parentType = "Mutation", field = "addShelf")
    fun addShelf(name: String): Shelf = runBlocking {
        val newShelf = Shelf(name = name)
        shelfRepository.save(newShelf)
    }
}