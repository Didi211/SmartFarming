package com.elfak.smartfarming.domain.retrofit

import android.util.Log
import com.elfak.smartfarming.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
class RetrofitClient() {
    companion object {
        private const val SMART_FARMING_API = BuildConfig.API_URL

        fun googleRoutesApiClient(): Retrofit {
            Log.d("RETROFIT", SMART_FARMING_API)

            return Retrofit.Builder()
                .baseUrl(SMART_FARMING_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }


}