package tk.zedlabs.wallportal.repository

import androidx.paging.PagingSource
import tk.zedlabs.wallportal.data.JsonApi
import tk.zedlabs.wallportal.data.RetrofitService
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.util.Constants
import javax.inject.Inject

class PopularDataSource @Inject constructor(private val jsonApi: JsonApi) :
    PagingSource<Int, WallHavenResponse>() {


    //private var jsonApi: JsonApi = RetrofitService.createService(JsonApi::class.java)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallHavenResponse> {
        try {
            val nextPage = params.key ?: 1
            val response = jsonApi.getImageList(
                Constants.queryParamPopular,
                Constants.sortingPopular,
                nextPage
            )
            return LoadResult.Page(
                data = response.body()?.data ?: emptyList(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = response.body()?.meta?.currentPage!! + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}