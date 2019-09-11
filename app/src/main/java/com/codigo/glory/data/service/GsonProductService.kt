package com.codigo.glory.data.service

import android.content.Context
import com.codigo.glory.R
import com.codigo.glory.data.model.data.IPhoneData
import com.codigo.glory.data.model.data.MacData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.codigo.glory.data.util.isNetworkConnected
import io.reactivex.Single
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter
import java.util.concurrent.TimeUnit


class GsonProductService constructor(
    private val context: Context
) {
    private val gson = Gson()
    private val macListType = object : TypeToken<List<MacData>>() {}.type
    private val iPhoneListType = object : TypeToken<List<IPhoneData>>() {}.type

    fun getMacs(): Single<List<MacData>> {

        return Single.fromCallable {
            if (context.isNetworkConnected()) {
                val macsJson = getJsonString(R.raw.macs)
                gson.fromJson<List<MacData>>(macsJson, macListType)
            } else {
                throw Throwable("Please Check your network")
            }
        }
            .delay(2, TimeUnit.SECONDS) // simulate response time
    }

    fun getIPhones(): Single<List<IPhoneData>> {

        return Single.fromCallable {
            if (context.isNetworkConnected()) {
                val iPhonesJson = getJsonString(R.raw.iphones)
                gson.fromJson<List<IPhoneData>>(iPhonesJson, iPhoneListType)
            } else {
                throw Throwable("Please Check your network")
            }
        }
            .delay(2, TimeUnit.SECONDS) // simulate response time
    }


    fun favouriteMac(macId: String): Single<Any> {
        return Single.fromCallable {
            if (context.isNetworkConnected()) {
                Any()
            } else {
                throw Throwable("Please Check your network")
            }
        }
            .delay(200, TimeUnit.MILLISECONDS) // simulate response time
    }

    fun unFavouriteMac(macId: String): Single<Any> {
        return Single.fromCallable {
            if (context.isNetworkConnected()) {
                Any()
            } else {
                throw Throwable("Please Check your network")
            }
        }
            .delay(200, TimeUnit.MILLISECONDS) // simulate response time
    }

    fun favouriteIPhone(iPhoneId: String): Single<Any> {
        return Single.fromCallable {
            if (context.isNetworkConnected()) {
                Any()
            } else {
                throw Throwable("Please Check your network")
            }
        }
            .delay(200, TimeUnit.MILLISECONDS) // simulate response time
    }

    fun unFavouriteIPhone(iPhoneId: String): Single<Any> {
        return Single.fromCallable {
            if (context.isNetworkConnected()) {
                Any()
            } else {
                throw Throwable("Please Check your network")
            }
        }
            .delay(200, TimeUnit.MILLISECONDS) // simulate response time
    }

    private fun getJsonString(jsonRes: Int): String {

        val inputStream = context.resources.openRawResource(jsonRes)
        val writer = StringWriter()
        val buffer = CharArray(1024)
        inputStream.use {
            val reader = BufferedReader(InputStreamReader(it, "UTF-8"))
            var n: Int = reader.read(buffer)
            while (n != -1) {
                writer.write(buffer, 0, n)
                n = reader.read(buffer)
            }
        }

        return writer.toString()
    }
}