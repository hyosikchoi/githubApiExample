package com.hyosik.android.data

import com.hyosik.android.data.remote.GithubApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
class RetrofitUnitTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubApiService::class.java)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get android repository`() {

        runBlocking {
            val actual = api.getRepository(
                token = "token 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",
                query = "Android",
                page = 1,
                perPage = 10
            )
            assertThat(actual.isSuccessful).isTrue()
            assertThat(actual.body()?.items?.size).isEqualTo(10)
        }

    }

}