package com.codigo.movies

import com.codigo.movies.domain.model.Movie
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object MovieFactory {

    private fun makeRandomString() = UUID.randomUUID().toString()
    private fun makeRandomInt() = ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    private fun makeRandomLong() = makeRandomInt().toLong()

    fun makeMovie(count: Int = 1): List<Movie> {
        val list = mutableListOf<Movie>()
        repeat(count) {
            list += Movie(makeRandomLong(), makeRandomString(), "")
        }
        return list
    }
}