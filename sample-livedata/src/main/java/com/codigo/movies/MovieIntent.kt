package com.codigo.movies

sealed class MovieIntent {
    object GetMoviesIntent : MovieIntent()
    object RetryPopularMoviesIntent : MovieIntent()
    object RetryUpcomingMoviesIntent : MovieIntent()
}