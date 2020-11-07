package tk.zedlabs.wallportal.repository

import tk.zedlabs.wallportal.data.JsonApi
import tk.zedlabs.wallportal.data.RetrofitService
import javax.inject.Inject

class ImageDetailsRepository @Inject constructor(
    private val wallpaperService: JsonApi
) {
    //add better error and exception handling
    suspend fun getData(id: String) = wallpaperService.getImageDetails(id).body()?.imageDetails

}