package tk.zedlabs.wallportal.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import tk.zedlabs.wallportal.data.JsonApi
import tk.zedlabs.wallportal.models.ImageDetails
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.persistence.BookmarkDao
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.util.Constants
import tk.zedlabs.wallportal.util.Resource
import javax.inject.Inject

class ImageDetailsRepository @Inject constructor(
    private val wallpaperService: JsonApi,
    private val dao: BookmarkDao
) {
    //change to response type
    suspend fun getNewList(currentPage: Int): List<WallHavenResponse> {
        return wallpaperService.getImageList(
            Constants.queryParamNew,
            Constants.sortingNew,
            currentPage
        ).body()?.data!!
    }

    suspend fun getPopularList(currentPage: Int): List<WallHavenResponse> {
        return wallpaperService.getImageList(
            Constants.queryParamPopular,
            Constants.sortingPopular,
            currentPage
        ).body()?.data!!
    }

    //add better error and exception handling(resource class)
    suspend fun getData(id: String) = wallpaperService.getImageDetails(id).body()?.imageDetails

    suspend fun getWallpaperData(id: String): Resource<ImageDetails> {
        val response = try {
            wallpaperService.getImageDetails(id).body()?.imageDetails
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response!!)
    }

    fun getBookmarks(): Flow<List<BookmarkImage>> = dao.getAll()
        .flowOn(Dispatchers.Main)
        .conflate()

}