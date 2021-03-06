package com.teamsparker.android.data.remote

import com.teamsparker.android.data.remote.service.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private const val BASE_URL = "https://asia-northeast3-we-sopt-spark.cloudfunctions.net/api/"

    private val headerInterceptor = Interceptor { chain ->
        with(chain) {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", LocalPreferences.getAccessToken())
                    .build()
            )
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(headerInterceptor)
        .build()


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 이 밑에다가 이런식으로 서비스 객체 생성하기
    // val sampleService: SampleService = retrofit.create(SampleService::class.java)

    val habitService : HabitService = retrofit.create(HabitService::class.java)
    val storageService: StorageService = retrofit.create(StorageService::class.java)
    val photoCollectionService: PhotoCollectionService = retrofit.create(PhotoCollectionService::class.java)
    val photoMainService: PhotoMainService = retrofit.create(PhotoMainService::class.java)
    val setStatusService: SetStatusService = retrofit.create(SetStatusService::class.java)
    val sendSparkService: SendSparkService = retrofit.create(SendSparkService::class.java)
    val certifyService: CertifyService = retrofit.create(CertifyService::class.java)
    val setPurposeService: SetPurposeService = retrofit.create(SetPurposeService::class.java)
    val leaveRoomService: LeaveRoomService = retrofit.create(LeaveRoomService::class.java)
}
