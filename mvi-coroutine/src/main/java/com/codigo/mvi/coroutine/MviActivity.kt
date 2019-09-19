package com.codigo.mvi.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe

abstract class MviActivity<VM: MviViewModel<VS, E>, VS, E>: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpLayout()

        getViewModel().streamViewSates().observe(this) {
            render(it)
        }

        getViewModel().streamEvents().observe(this) {
            renderEvent(it)
        }
    }

    abstract fun setUpLayout()

    abstract fun getViewModel(): VM

    abstract fun render(viewState: VS)

    abstract fun renderEvent(event: E)
}