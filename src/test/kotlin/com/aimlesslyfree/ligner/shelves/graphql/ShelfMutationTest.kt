package com.aimlesslyfree.ligner.shelves.graphql

import com.aimlesslyfree.ligner.shelves.db.Shelf
import com.aimlesslyfree.ligner.shelves.db.ShelfRepository
import com.netflix.graphql.dgs.DgsQueryExecutor
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ShelfMutationTest(
        @Autowired val shelfRepository: ShelfRepository,
        @Autowired val dgsQueryExecutor: DgsQueryExecutor
) {

    @AfterEach
    fun tearDown() = runBlocking { shelfRepository.deleteAll() }

    @Test
    fun `should add shelf`() = runBlocking<Unit> {
        val newShelfName = "Want to read"

        val shelfName = dgsQueryExecutor.executeAndExtractJsonPath<String>(
                "mutation { addShelf(userId: 1, name: \"$newShelfName\") { name } }",
                "data.addShelf.name"
        )

        assertThat(shelfName).isEqualTo(newShelfName)
        val shelves = shelfRepository.findAll().toList()
        assertThat(shelves).hasSize(1)
        assertThat(shelves[0].name).isEqualTo(newShelfName)
    }

    @Test
    fun `should add book to shelf`() = runBlocking<Unit> {
        val shelf = shelfRepository.save(Shelf(userId = 1, name = "Want to read"))
        val newBookTitle = "Chamber of secrets"

        val bookTitle = dgsQueryExecutor.executeAndExtractJsonPath<String>(
                """mutation { addBookToShelf(
                    userId: ${shelf.userId}, 
                    shelfName: "${shelf.name}", 
                    author: "JK Rowling", 
                    title: "$newBookTitle") 
                    { books { title } } }""",
                "data.addBookToShelf.books[0].title"
        )

        assertThat(bookTitle).isEqualTo(newBookTitle)
        val shelves = shelfRepository.findAll().toList()
        assertThat(shelves).hasSize(1)
        assertThat(shelves[0].books).hasSize(1)
        assertThat(shelves[0].books[0].title).isEqualTo(newBookTitle)
    }
}