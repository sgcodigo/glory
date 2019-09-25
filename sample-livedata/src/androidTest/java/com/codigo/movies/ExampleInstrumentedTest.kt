package com.codigo.movies

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("DEPRECATION")
@RunWith(androidx.test.runner.AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.sample_mvi_coroutine", appContext.packageName)
    }
}
