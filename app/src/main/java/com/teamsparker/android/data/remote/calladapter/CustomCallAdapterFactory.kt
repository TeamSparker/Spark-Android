package com.teamsparker.android.data.remote.calladapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CustomCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkState<Foo>> or Call<NetworkState<out Foo>>"
        }

        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != NetworkState::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            "Response must be parameterized as NetworkState<Foo> or NetworkState<out Foo>"
        }

        val bodyType = getParameterUpperBound(0, responseType)

        return CustomCallAdapter<Any>(bodyType)
    }
}
