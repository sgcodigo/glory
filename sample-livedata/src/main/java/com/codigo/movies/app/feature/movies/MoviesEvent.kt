package com.codigo.movies.app.feature.movies

sealed class MoviesEvent {
    object OfflineEvent : MoviesEvent()
}
