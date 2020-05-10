package tk.zedlabs.wallaperapp2019.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tk.zedlabs.wallaperapp2019.models.MainResponse
import tk.zedlabs.wallaperapp2019.models.UnsplashImageDetails
import tk.zedlabs.wallaperapp2019.models.WallHavenResponse

interface JsonApi {

    @GET("/photos/")
    suspend fun getImages(@Query("client_id") accessKey: String,
                  @Query("page") page : Int,
                  @Query("per_page") noPages : Int) : Response<List<UnsplashImageDetails>>

    @GET("/photos/")
    suspend fun getPopularImages(@Query("client_id") accessKey: String,
                         @Query("page") page : Int,
                         @Query("per_page") noPages : Int,
                         @Query("order_by") orderBy : String) : Response<List<UnsplashImageDetails>>

    @GET("/api/v1/search/")
    suspend fun getImageList(@Query("q") queryParam : String,
                             @Query("sorting") sorting : String,
                             @Query("page") page: Int) : Response<MainResponse>


}