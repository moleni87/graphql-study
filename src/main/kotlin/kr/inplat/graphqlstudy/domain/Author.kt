package kr.inplat.graphqlstudy.domain

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
data class Author(
    @Id
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var thumbnail: String
)

data class CreateAuthor(
    var name: String,
    var thumbnail: String
)

@Repository
interface AuthorRepository : JpaRepository<Author, String> {
    fun getById(id: String): Author
}

@Component
class AuthorQueryResolver(
    val authorRepository: AuthorRepository
) : GraphQLQueryResolver {

    fun getAuthors(): List<Author> = authorRepository.findAll()
}

@Component
class AuthorMutationResolver(
    val authorRepository: AuthorRepository
) : GraphQLMutationResolver {

    fun createAuthor(createAuthor: CreateAuthor): Author {
        return authorRepository.save(
            Author(
                name = createAuthor.name,
                thumbnail = createAuthor.thumbnail
            )
        )
    }
}

@Component
class AuthorResolver(val postRepository: PostRepository) : GraphQLResolver<Author> {

    fun getPosts(author: Author, environment: DataFetchingEnvironment): List<Post> {
        return postRepository.findByAuthorId(author.id)
    }
}
