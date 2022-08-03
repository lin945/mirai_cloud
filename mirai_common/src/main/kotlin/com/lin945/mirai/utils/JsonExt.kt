package com.lin945.mirai.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public val mapper=  jacksonObjectMapper()

public inline fun <reified T> String.convertTo():T =mapper.readValue<T>(this)

public inline fun <reified T> convertJsonTo(json:String) = mapper.readValue<T>(json)
public inline fun <reified T> convertJsonTo(byteArray: ByteArray) = mapper.readValue<T>(byteArray)
public fun <T> T.toJsonString()= mapper.writeValueAsString(this)!!


public suspend inline fun <reified T> String.convertToS():T {
    return withContext(Dispatchers.IO){
         mapper.readValue(this@convertToS,T::class.java)
    }
}