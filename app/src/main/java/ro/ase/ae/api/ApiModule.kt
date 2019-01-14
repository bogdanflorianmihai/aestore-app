package ro.ase.ae.api

import android.content.Context
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.readystatesoftware.chuck.ChuckInterceptor
import net.mready.photon.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Suppress("unused")
object ApiModule {

    @Singleton
    @Provides
    fun okHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(context))
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun objectMapper() = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient, objectMapper: ObjectMapper) = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://aestore.bmapps.ro/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .build()

    @Singleton
    @Provides
    fun categoriesApi(retrofit: Retrofit): CategoriesApi =
        retrofit.create(CategoriesApi::class.java)

    @Singleton
    @Provides
    fun productsApi(retrofit: Retrofit): ProductsApi = retrofit.create(ProductsApi::class.java)
}