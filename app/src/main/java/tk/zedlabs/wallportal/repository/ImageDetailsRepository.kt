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

    fun getBookmarks(): Flow<List<BookmarkImage>> = dao.getAll()
        .flowOn(Dispatchers.Main)
        .conflate()

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

    suspend fun getWallpaperData(id: String): Resource<ImageDetails> {
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

    suspend fun setBookmark(imageDetails: ImageDetails) {
        dao.insert(
            BookmarkImage(
                imageName = imageDetails.id1!!,
                imageUrlFull = imageDetails.path1,
                imageUrlRegular = imageDetails.thumbs?.small
            )
        )
    }

}