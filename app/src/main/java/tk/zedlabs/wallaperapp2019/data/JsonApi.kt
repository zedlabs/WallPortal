package tk.zedlabs.wallaperapp2019.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tk.zedlabs.wallaperapp2019.models.MainResponse

interface JsonApi {

    @GET("/api/v1/search/")
    suspend fun getImageList(@Query("q") queryParam : String,
                             @Query("sorting") sorting : String,
                             @Query("page") page: Int) : Response<MainResponse>


}