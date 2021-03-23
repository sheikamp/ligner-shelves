package com.aimlesslyfree.ligner.shelves.db

import com.aimlesslyfree.ligner.shelves.graphql.ShelfMutation.Companion.createFakeBooks
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
class ShelfRepositoryTest(
        @Autowired val shelfRepository: ShelfRepository
) {

    @AfterEach
    fun tearDown() = runBlocking { shelfRepository.deleteAll() }

    @Test
    fun `should store shelf`() = runBlocking<Unit> {
        val shelve = Shelf(userId = 1, name = "Currently reading")

        shelfRepository.save(shelve)

        val shelves = shelfRepository.findAll().toList()
        assertThat(shelves).hasSize(1)
        assertThat(shelves[0].name).isEqualTo(shelve.name)
    }

    @Test
    fun `should store shelf with books`() = runBlocking<Unit> {
        val books = createFakeBooks()
        val shelve = Shelf(userId = 1, name = "Currently reading", books = books)

        shelfRepository.save(shelve)

        val shelves = shelfRepository.findAll().toList()
        assertThat(shelves).hasSize(1)
        assertThat(shelves[0].name).isEqualTo(shelve.name)
        assertThat(shelves[0].books).hasSize(books.size).containsAll(books)
    }

    @Test
    fun `should find shelf by user id`() = runBlocking<Unit> {
        val shelveUser1 = shelfRepository.save(Shelf(userId = 1, name = "Currently reading"))
        val shelveUser2 = shelfRepository.save(Shelf(userId = 2, name = "Currently reading"))

        val shelves = shelfRepository.findByUserId(1).toList()
        assertThat(shelves).hasSize(1)
        assertThat(shelves[0].name).isEqualTo(shelveUser1.name)
    }
}