package tk.zedlabs.wallportal.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import tk.zedlabs.wallportal.data.JsonApi
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

    fun getBookmarks(): Flow<List<BookmarkImage>> = dao.getAll()
        .flowOn(Dispatchers.Main)
        .conflate()

    suspend fun getNewList(currentPage: Int, queryParam: String): Resource<List<WallHavenResponse>> {
        val response = try {
            wallpaperService.getImageList(
                queryParam,
                Constants.sortingNew,
                currentPage
            ).body()?.data
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response!!)
    }

    suspend fun getPopularList(currentPage: Int): Resource<List<WallHavenResponse>> {
        val response = try {
            wallpaperService.getImageList(
                Constants.queryParamPopular,
                Constants.sortingPopular,
                currentPage
            ).body()?.data
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response!!)

    }

    suspend fun getWallpaperData(id: String): Resource<WallHavenResponse> {
        val response = try {
            wallpaperService.getImageDetails(id).body()?.imageDetails
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response!!)
    }

    suspend fun checkBookmark(name: String) : Boolean = (dao.getItemByName(name).isNotEmpty())

    suspend fun deleteBookmark(id: String) {
        dao.deleteBookmark(id)
    }

    suspend fun setBookmark(imageDetails: WallHavenResponse) {
        dao.insert(
            BookmarkImage(
                imageName = imageDetails.id!!,
                imageUrlFull = imageDetails.path,
                imageUrlRegular = imageDetails.thumbs?.small
            )
        )
    }

}