package com.codigo.movies.data.exception

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@ExperimentalCoroutinesApi
object GlobalEventStream {

    private val eventChannel = BroadcastChannel<GlobalEvent>(1)

    @FlowPreview
    fun eventFlow(): Flow<GlobalEvent> {
        return eventChannel.asFlow()
    }

    fun emit(event: GlobalEvent) {
        eventChannel.offer(event)
    }

    fun closeStream() {
        eventChannel.close()
    }
}

sealed class GlobalEvent {
    data class ServerError(val message: String? = null) : GlobalEvent()
    data class Maintenance(val message: String? = null) : GlobalEvent()
    data class Forbidden(val message: String? = null) : GlobalEvent()
}