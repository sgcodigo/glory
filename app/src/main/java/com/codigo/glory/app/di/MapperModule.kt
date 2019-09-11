package com.codigo.glory.app.di

import com.codigo.glory.data.mapper.IPhoneMapper
import com.codigo.glory.data.mapper.MacMapper
import org.koin.dsl.module

/**
 * Created by khunzohn on 2019-09-11 at Codigo
 */
val mapperModule = module {

    factory { IPhoneMapper() }

    factory { MacMapper() }
}