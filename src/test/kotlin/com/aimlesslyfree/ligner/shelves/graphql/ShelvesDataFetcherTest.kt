package com.aimlesslyfree.ligner.shelves.graphql

import com.aimlesslyfree.ligner.shelves.db.Shelf
import com.aimlesslyfree.ligner.shelves.db.ShelfRepository
import com.netflix.graphql.dgs.DgsQueryExecutor
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ShelvesDataFetcherTest(
        @Autowired val shelfRepository: ShelfRepository,
        @Autowired val dgsQueryExecutor: DgsQueryExecutor
) {

    @AfterEach
    fun tearDown() = runBlocking { shelfRepository.deleteAll() }

    @Test
    fun `should return shelves`() = runBlocking<Unit> {
        val currentlyReading = Shelf(name = "Currently reading")
        val wantToRead = Shelf(name = "Want to read")
        val shelves = shelfRepository.saveAll(listOf(currentlyReading, wantToRead)).toList()

        val shelfNames = dgsQueryExecutor.executeAndExtractJsonPath<List<String>>(
                "{ shelves { name } }",
                "data.shelves[*].name"
        )

        assertThat(shelfNames).hasSize(2).containsAll(shelves.map { it.name })
    }

    @Test
    fun `should return shelves filtered by name`() = runBlocking<Unit> {
        val currentlyReading = Shelf(name = "Currently reading")
        val wantToRead = Shelf(name = "Want to read")
        val shelves = shelfRepository.saveAll(listOf(currentlyReading, wantToRead)).toList()

        val shelfNames = dgsQueryExecutor.executeAndExtractJsonPath<List<String>>(
                "{ shelves(nameFilter: \"${currentlyReading.name}\") { name } }",
                "data.shelves[*].name"
        )

        assertThat(shelfNames).hasSize(1).contains(currentlyReading.name)
    }
}