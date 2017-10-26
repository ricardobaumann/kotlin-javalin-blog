package blog

data class Post(val title: String = "", val body: String = "")

class PostService(val repository: Repository<Post>) {

    fun getPosts(): MutableCollection<Post> {
        return repository.list()
    }

    fun create(post: Post) : Long {
        return repository.create(post)
    }

    fun getById(id: Long): Post? {
        return repository.get(id)
    }


}