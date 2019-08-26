package tk.zedlabs.wallaperapp2019.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tk.zedlabs.wallaperapp2019.models.UnsplashImageDetails

interface JsonApi {

    @GET("/photos/")
    fun getImages(@Query("client_id") accessKey: String,
                  @Query("page") page : Int,
                  @Query("per_page") noPages : Int) : Call<List<UnsplashImageDetails>>

    @GET("/photos/curated")
    fun getPopularImages(@Query("client_id") accessKey: String,
                         @Query("page") page : Int,
                         @Query("per_page") noPages : Int,
                         @Query("order_by") orderBy : String) : Call<List<UnsplashImageDetails>>
}