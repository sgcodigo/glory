package com.codigo.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.codigo.movies.data.datasource.local.MovieLocal
import com.codigo.movies.data.datasource.local.MovieLocalImpl
import com.codigo.movies.data.db.MovieDatabase
import com.codigo.movies.data.db.dao.MovieDao
import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.model.Movie
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor

@RunWith(AndroidJUnit4::class)
class MovieLocalImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieDatabase: MovieDatabase
    private lateinit var movieLocal: MovieLocal

    @Before
    fun setUp() {
        movieLocal = MovieDatabase.createForTest(
            InstrumentationRegistry.getInstrumentation().context
        ).also { movieDatabase = it }.movieDao().let { MovieLocalImpl(it) }
    }

    @Test
    fun insertPopularMovies() {
        val data = MovieFactory.makeMovie()
        movieLocal.insertPopularMovies(data)
        val observer: Observer<List<Movie>> = mock()
        val listClass = ArrayList::class.java as Class<ArrayList<Movie>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)
        movieLocal.streamPopularMovies().observeForever(observer)
        verify(observer).onChanged(argumentCaptor.capture())
        assertEquals(1, argumentCaptor.value.size)
    }

    @Test
    fun insertUpcomingMovies() {
        val data = MovieFactory.makeMovie()
        movieLocal.insertUpcomingMovies(data)
        val observer: Observer<List<Movie>> = mock()
        val listClass = ArrayList::class.java as Class<ArrayList<Movie>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)
        movieLocal.streamUpcomingMovies().observeForever(observer)
        verify(observer).onChanged(argumentCaptor.capture())
        assertEquals(1, argumentCaptor.value.size)
    }

    @Test
    fun getCorrectPopularMovies() {
        val upcomingMovie = MovieFactory.makeMovie(2)
        val popularMovie = MovieFactory.makeMovie(2)

        movieLocal.insertUpcomingMovies(upcomingMovie)
        movieLocal.insertPopularMovies(popularMovie)

        val observer: Observer<List<Movie>> = mock()
        val listClass = ArrayList::class.java as Class<ArrayList<Movie>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)
        movieLocal.streamPopularMovies().observeForever(observer)
        verify(observer).onChanged(argumentCaptor.capture())
        verify(observer).onChanged(popularMovie)
        assertEquals(2, argumentCaptor.value.size)
    }

    @Test
    fun getCorrectUpcomingMovies() {
        val upcomingMovie = MovieFactory.makeMovie(2)
        val popularMovie = MovieFactory.makeMovie(2)

        movieLocal.insertUpcomingMovies(upcomingMovie)
        movieLocal.insertPopularMovies(popularMovie)

        val observer: Observer<List<Movie>> = mock()
        val listClass = ArrayList::class.java as Class<ArrayList<Movie>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)
        movieLocal.streamUpcomingMovies().observeForever(observer)
        verify(observer).onChanged(argumentCaptor.capture())
        verify(observer).onChanged(upcomingMovie)
        assertEquals(2, argumentCaptor.value.size)
    }

    @Test
    fun replaceIfSameMovie() {
        val oldName = "Endgame"
        val updateName = "Avenger-Endgame"
        val data = listOf(
            Movie(1, oldName, ""),
            Movie(1, updateName, "")
        )
        movieLocal.insertUpcomingMovies(data)
        val observer: Observer<List<Movie>> = mock()
        val listClass = ArrayList::class.java as Class<ArrayList<Movie>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)
        movieLocal.streamUpcomingMovies().observeForever(observer)
        verify(observer).onChanged(argumentCaptor.capture())
        assertEquals(updateName, argumentCaptor.value.first().title)
    }

    @After
    fun closeDb() {
        movieDatabase.close()
    }
}