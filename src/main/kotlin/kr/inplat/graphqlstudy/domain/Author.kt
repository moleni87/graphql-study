package kr.inplat.graphqlstudy.domain

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.persistence.*

@Entity
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: String,
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
class AuthorResolver(val postRepository: PostRepository) : GraphQLResolver<Author> {

    fun getPosts(author: Author): List<Post> {
        return postRepository.findByAuthorId(author.id)
    }
}
