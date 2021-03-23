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
    fun `should return shelves filtered by user id`() = runBlocking<Unit> {
        val currentlyReading = shelfRepository.save(Shelf(userId = 1, name = "Currently reading"))
        val wantToRead = shelfRepository.save(Shelf(userId = 2, name = "Want to read"))

        val shelfNames = dgsQueryExecutor.executeAndExtractJsonPath<List<String>>(
                "{ shelves(userIdFilter: 1) { name } }",
                "data.shelves[*].name"
        )

        assertThat(shelfNames).hasSize(1).contains(currentlyReading.name)
    }
}