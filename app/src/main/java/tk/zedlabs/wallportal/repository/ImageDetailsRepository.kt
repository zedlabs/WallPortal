package tk.zedlabs.wallportal.repository

import tk.zedlabs.wallportal.data.JsonApi
import tk.zedlabs.wallportal.data.RetrofitService

class ImageDetailsRepository {

    private var jsonApi: JsonApi = RetrofitService.createService(JsonApi::class.java)

    suspend fun getData(id: String) = jsonApi.getImageDetails(id)
}