package tk.zedlabs.wallportal.repository

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.data.JsonApi
import tk.zedlabs.wallportal.data.RetrofitService
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.util.Constants

class PopularDataSource(private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, WallHavenResponse>() {


    private var jsonApi: JsonApi = RetrofitService.createService(JsonApi::class.java)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, WallHavenResponse>
    ) {

        scope.launch {
            try {
                val response = jsonApi.getImageList(
                    Constants.queryParamPopular,
                    Constants.sorting,
                    Constants.FIRST_PAGE
                )
                when {
                    response.isSuccessful -> {
                        callback.onResult(
                            response.body()?.data ?: emptyList(),
                            null,
                            Constants.FIRST_PAGE + 1
                        )
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, WallHavenResponse>
    ) {

        scope.launch {
            try {
                val response =
                    jsonApi.getImageList(Constants.queryParamPopular, Constants.sorting, params.key)
                when {
                    response.isSuccessful -> {
                        val key: Int? = if (response.body()?.data?.isNotEmpty() == true) params.key + 1
                        else null
                        callback.onResult(response.body()?.data ?: emptyList(), key)
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, WallHavenResponse>
    ) {

        scope.launch {
            try {
                val response = jsonApi.getImageList(Constants.queryParamPopular, Constants.sorting, params.key)
                val key: Int? = if (params.key > 1) params.key - 1
                else null
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()?.data ?: emptyList(), key)
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}