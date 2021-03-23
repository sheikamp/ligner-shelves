package com.aimlesslyfree.ligner.shelves.graphql

import com.aimlesslyfree.ligner.shelves.db.Shelf
import com.aimlesslyfree.ligner.shelves.db.ShelfRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

@DgsComponent
class ShelvesDataFetcher(
        val shelfRepository: ShelfRepository
) {

    @DgsData(parentType = "Query", field = "shelves")
    fun shelves(@InputArgument("nameFilter") nameFilter: String?) = GlobalScope.future {
        if (nameFilter == null) {
            shelfRepository.findAll()
        } else {
            shelfRepository.findByName(nameFilter)
        }.toList()
    }
}