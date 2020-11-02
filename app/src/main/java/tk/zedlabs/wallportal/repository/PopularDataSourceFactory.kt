package tk.zedlabs.wallportal.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import tk.zedlabs.wallportal.models.WallHavenResponse

//class PopularDataSourceFactory(private val scope: CoroutineScope) : DataSource.Factory<Int, WallHavenResponse>() {
//
//    private val popularPostLiveDataSource : MutableLiveData<PageKeyedDataSource<Int, WallHavenResponse>> = MutableLiveData()
//
//    override fun create(): DataSource<Int, WallHavenResponse> {
//        val popularDataSource = PopularDataSource(scope)
//        popularPostLiveDataSource.postValue(popularDataSource)
//        return popularDataSource
//    }
//    fun getPopularLiveDataSource() : MutableLiveData<PageKeyedDataSource<Int, WallHavenResponse>> {
//        return popularPostLiveDataSource
//    }
//
//}