package com.techtask.cats.data

import com.google.common.truth.Truth
import com.techtask.cats.common.Result
import com.techtask.cats.domain.model.Cat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class RemoteDataSourceTest {

    private val mockWebServer = MockWebServer()
    private lateinit var catsApi: CatsApi

    private lateinit var SUT: RemoteDataSource

    @Before
    fun setup() {
        mockWebServer.start()

        catsApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatsApi::class.java)

        SUT = RemoteDataSource(catsApi)
    }

    @Test
    fun `GIVEN successful response WHEN retrieveCats executed THEN success result returned`() = runBlocking {
        // Assign
        val mockedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestFileUtils.readTestResourceFile(
                fileName = TestFileUtils.ResponsesFiles.RESPONSE_CATS)
            )
        mockWebServer.enqueue(mockedResponse)

        // Act
        val result = SUT.retrieveCats(FAKE_BREED_ID)

        // Assert
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
        val data: List<Cat> = (result as Result.Success<List<Cat>>).data
        Truth.assertThat(data.size).isEqualTo(2)
        Truth.assertThat(data.get(0).id).isEqualTo("0XYvRd7oD")
        Truth.assertThat(data.get(0).breeds.get(0).id).isEqualTo("abys")
    }

    @Test
    fun `GIVEN empty response WHEN retrieveCats executed THEN success empty result returned`() = runBlocking {
        // Assign
        val mockedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestFileUtils.readTestResourceFile(
                fileName = TestFileUtils.ResponsesFiles.RESPONSE_CATS_EMPTY)
            )
        mockWebServer.enqueue(mockedResponse)

        // Act
        val result = SUT.retrieveCats(FAKE_BREED_ID)

        // Assert
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
        val data: List<Cat> = (result as Result.Success<List<Cat>>).data
        Truth.assertThat(data.size).isEqualTo(0)
    }

    @Test
    fun `GIVEN wrong response WHEN retrieveCats executed THEN failure result returned`() = runBlocking {
        // Assign
        val mockedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestFileUtils.readTestResourceFile(
                fileName = TestFileUtils.ResponsesFiles.RESPONSE_CATS_FAIL)
            )
        mockWebServer.enqueue(mockedResponse)

        // Act
        val result = SUT.retrieveCats(FAKE_BREED_ID)

        // Assert
        Truth.assertThat(result).isInstanceOf(Result.Failure::class.java)
    }

    object TestFileUtils {

        object ResponsesFiles {
            const val RESPONSE_CATS = "response_cats.json"
            const val RESPONSE_CATS_EMPTY = "response_cats_empty.json"
            const val RESPONSE_CATS_FAIL = "response_cats_fail.json"
        }

        fun readTestResourceFile(fileName: String): String {
            val fileInputStream = javaClass.classLoader?.getResourceAsStream(fileName)
            return fileInputStream?.bufferedReader()?.readText().orEmpty()
        }
    }

    companion object {
        private const val FAKE_BREED_ID = "abys"
    }
}
