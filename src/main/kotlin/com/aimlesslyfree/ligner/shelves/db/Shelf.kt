package com.aimlesslyfree.ligner.shelves.db

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Shelf(
        @Id
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId = ObjectId(),
        val userId: Int,
        val name: String,
        val books: MutableList<Book> = mutableListOf()
)
