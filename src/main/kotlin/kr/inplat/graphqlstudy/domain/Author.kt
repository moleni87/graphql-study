package kr.inplat.graphqlstudy.domain

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import graphql.execution.instrumentation.Instrumentation
import graphql.schema.DataFetchingEnvironment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.persistence.*
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentation

import org.dataloader.DataLoaderRegistry

import org.dataloader.DataLoader
import org.springframework.context.annotation.Bean


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
//    https://github.com/graphql-java-kickstart/graphql-java-tools/discussions/58
    fun getPosts(author: Author, environment: DataFetchingEnvironment): CompletableFuture<List<Post>>? {
        return environment.getDataLoader<String, List<Post>>("posts")
            .load(author.id)
//        return postRepository.findByAuthorId(author.id)
    }
}

/*
@Bean
fun dataLoaderRegistry(loaderList: List<DataLoader<*, *>>): DataLoaderRegistry? {
    val registry = DataLoaderRegistry()
    for (loader in loaderList) {
        registry.register(loader.javaClass.simpleName, loader)
    }
    return registry
}

@Bean
fun instrumentation(dataLoaderRegistry: DataLoaderRegistry): Instrumentation? {
    return DataLoaderDispatcherInstrumentation(dataLoaderRegistry)
}
*/
