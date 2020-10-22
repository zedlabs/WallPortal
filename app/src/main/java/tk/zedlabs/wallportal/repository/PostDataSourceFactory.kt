package tk.zedlabs.wallportal.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import tk.zedlabs.wallportal.models.WallHavenResponse
import javax.inject.Inject

class PostDataSourceFactory (
    private val scope: CoroutineScope
) : DataSource.Factory<Int, WallHavenResponse>() {

    private val postLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, WallHavenResponse>> =
        MutableLiveData()

    override fun create(): DataSource<Int, WallHavenResponse> {
        val postDataSource = PostDataSource(scope)
        postLiveDataSource.postValue(postDataSource)
        return postDataSource
    }

    fun getPostLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, WallHavenResponse>> {
        return postLiveDataSource
    }
}