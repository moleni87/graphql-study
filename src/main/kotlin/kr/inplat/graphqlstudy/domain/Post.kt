package kr.inplat.graphqlstudy.domain

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.*

@Entity
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var title: String,
    var category: String,
    var authorId: String
)

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findByAuthorId(authorId: String): List<Post>
}

@Component
class PostQueryResolver(
    val postRepository: PostRepository
) : GraphQLQueryResolver {

    fun getPosts(): List<Post> = postRepository.findAll()
}

@Component
class PostResolver(val authorRepository: AuthorRepository) : GraphQLResolver<Post> {

    fun getAuthor(post: Post): Author {
        return authorRepository.getById(post.authorId)
    }
}