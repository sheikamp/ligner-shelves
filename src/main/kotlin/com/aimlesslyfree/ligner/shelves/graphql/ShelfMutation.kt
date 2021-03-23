package com.aimlesslyfree.ligner.shelves.graphql

import com.aimlesslyfree.ligner.shelves.db.Book
import com.aimlesslyfree.ligner.shelves.db.Shelf
import com.aimlesslyfree.ligner.shelves.db.ShelfRepository
import com.github.javafaker.Faker
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@DgsComponent
class ShelfMutation(
        val shelfRepository: ShelfRepository
) {

    @DgsData(parentType = "Mutation", field = "addShelf")
    fun addShelf(userId: Int, name: String): Shelf = runBlocking {
        val newShelf = shelfRepository.save(Shelf(userId = userId, name = name))
        LOG.info("Added shelf $name for user $userId.")
        newShelf
    }

    @DgsData(parentType = "Mutation", field = "addBookToShelf")
    fun addBookToShelf(userId: Int, shelfName: String, author: String, title: String): Shelf = runBlocking {
        val shelf = shelfRepository.findByUserId(userId).toList().find { it.name == shelfName }
                ?: error("Could not find shelf $shelfName. Please make sure it exists.")
        val newBook = Book(author, title)
        shelf.books += newBook
        val updatedShelf = shelfRepository.save(shelf)
        LOG.info("Added book $newBook to shelf $shelfName from user $userId.")
        updatedShelf
    }

    @DgsData(parentType = "Mutation", field = "populateShelves")
    fun populateShelves(userId: Int): List<Shelf> = runBlocking {
        val shelves = shelfRepository.findByUserId(userId).toList()
        val filledShelves = shelves.map { it.apply { books += createFakeBooks() } }
        val shelveNames = filledShelves.map { it.name }.joinToString { ", " }
        LOG.info("Filled shelves $shelveNames from user $userId with fake books.")
        filledShelves
    }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(ShelfMutation::class.java)
        private val FAKER = Faker()

        fun createFakeBooks(): MutableList<Book> {
            val fakeBooks = mutableListOf<Book>()
            repeat(10) { fakeBooks += Book(FAKER.book().author(),  FAKER.book().title()) }
            return fakeBooks
        }
    }
}