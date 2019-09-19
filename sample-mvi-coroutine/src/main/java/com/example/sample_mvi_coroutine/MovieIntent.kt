package com.example.sample_mvi_coroutine

sealed class MovieIntent {
    object GetMoviesIntent : MovieIntent()
    object RetryPopularMoviesIntent : MovieIntent()
    object RetryUpcomingMoviesIntent : MovieIntent()
}