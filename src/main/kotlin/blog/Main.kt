package blog

import io.javalin.ApiBuilder.*
import io.javalin.Javalin

val postService = PostService(Repository<Post>())

fun main(args: Array<String>) {
    val app = Javalin.start(7000)
    app.routes {
        get("/posts") { ctx ->
            ctx.json(postService.getPosts())
        }
        post("/posts") { ctx ->
            val post = ctx.bodyAsClass(Post::class.java)
            val id = postService.create(post)

            ctx.json(mapOf(Pair("id",id))).status(201)
            //ctx.status(204)
        }
    }
}