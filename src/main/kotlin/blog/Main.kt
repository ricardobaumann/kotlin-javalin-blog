package blog

import io.javalin.ApiBuilder.*
import io.javalin.Javalin

val postService = PostService(Repository<Post>())

fun main(args: Array<String>) {
    val app = Javalin.start(7000)
    app.routes {
        path("/posts", {
            get { ctx ->
                ctx.json(postService.getPosts())
            }
            post { ctx ->
                val post = ctx.bodyAsClass(Post::class.java)
                val id = postService.create(post)

                ctx.json(mapOf(Pair("id",id))).status(201)
            }
            path(":id", {
                get { ctx ->
                    val post = postService.getById(ctx.param("id")!!.toLong())
                    if (post!=null) {
                        ctx.json(post)
                    } else {
                        ctx.status(404)
                    }
                }
                put { ctx ->
                    val id = ctx.param("id")!!.toLong()
                    val post = ctx.bodyAsClass(Post::class.java)
                    postService.update(post,id)
                    ctx.status(200)
                }
                delete { ctx ->
                    postService.delete(ctx.param("id")!!.toLong())
                    ctx.status(204)
                }
            })
        })
    }
}