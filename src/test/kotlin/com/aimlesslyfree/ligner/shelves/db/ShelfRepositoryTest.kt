package com.aimlesslyfree.ligner.shelves.db

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
class ShelfRepositoryTest(
        @Autowired val shelveRepository: ShelfRepository
) {

    @Test
    fun `should store shelf`() = runBlocking<Unit> {
        val shelve = Shelf(name = "Currently reading")

        shelveRepository.save(shelve)

        val shelves = shelveRepository.findAll().toList()
        assertThat(shelves).hasSize(1)
        assertThat(shelves[0].name).isEqualTo(shelve.name)
    }
}