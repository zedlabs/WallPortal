package tk.zedlabs.wallportal.repository

import tk.zedlabs.wallportal.data.JsonApi
import javax.inject.Inject

class ImageDetailsRepository @Inject constructor(
    private val wallpaperService: JsonApi
) {
    //add better error and exception handling(resource class)
    suspend fun getData(id: String) = wallpaperService.getImageDetails(id).body()?.imageDetails
}