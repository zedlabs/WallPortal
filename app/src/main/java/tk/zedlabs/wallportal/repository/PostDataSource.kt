package tk.zedlabs.wallportal.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.zedlabs.wallportal.data.JsonApi
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.util.Constants
import javax.inject.Inject

class PostDataSource @Inject constructor(
    private val jsonApi: JsonApi
) : PagingSource<Int, WallHavenResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallHavenResponse> {
        try {
            val nextPage = params.key ?: 1
            val response = jsonApi.getImageList(
                Constants.queryParamNew,
                Constants.sortingNew,
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

    override fun getRefreshKey(state: PagingState<Int, WallHavenResponse>): Int? {
        return state.anchorPosition
    }

}