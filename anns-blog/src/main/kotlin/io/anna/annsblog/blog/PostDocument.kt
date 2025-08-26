package io.anna.annsblog.blog

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "posts")
data class PostDocument (
    @Id
    var id: ObjectId = ObjectId.get(),

    var title: String,
    var content: String,

    var date: Date = Date(),

    @Indexed(unique = true)
    var slug: String = title.toSlug()
)

fun String.toSlug(): String {
    return this.lowercase()
        .replace(Regex("[^a-z0-9]+"), "-")
        .trim('-')
}