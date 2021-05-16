package tk.zedlabs.wallportal.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tk.zedlabs.wallportal.models.Data
import tk.zedlabs.wallportal.models.MainResponse

interface JsonApi {

    @GET("/api/v1/search/")
    suspend fun getImageList(
        @Query("q") queryParam: String,
        @Query("sorting") sorting: String,
        @Query("page") page: Int
    ): Response<MainResponse>

    @GET("/api/v1/w/{id}")
    suspend fun getImageDetails(
        @Path("id") id: String
    ): Response<Data>

}