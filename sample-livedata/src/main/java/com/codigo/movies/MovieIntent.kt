package com.codigo.movies

sealed class MovieIntent {
    object RefreshPopularMoviesIntent : MovieIntent()
    object RefreshUpcomingMoviesIntent : MovieIntent()
    object StreamPopularMoviesIntent : MovieIntent()
    object StreamUpcomingMoviesIntent : MovieIntent()
}
