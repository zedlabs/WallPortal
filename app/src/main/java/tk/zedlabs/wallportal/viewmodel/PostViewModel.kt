package tk.zedlabs.wallportal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.repository.PopularDataSourceFactory
import tk.zedlabs.wallportal.repository.PostDataSource.Companion.PAGE_SIZE
import tk.zedlabs.wallportal.repository.PostDataSourceFactory

class PostViewModel : ViewModel() {

    var postPagedList: LiveData<PagedList<WallHavenResponse>>? = null
    private var postLiveDataSource: LiveData<PageKeyedDataSource<Int, WallHavenResponse>>? = null

    var popularPagedList: LiveData<PagedList<WallHavenResponse>>? = null
    private var popularLiveDataSource: LiveData<PageKeyedDataSource<Int, WallHavenResponse>>? = null


    private val config: PagedList.Config = (PagedList.Config.Builder())
        .setPageSize(PAGE_SIZE)
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(24)
        .setPrefetchDistance(24)
        .build()

    init {
        val postDataSourceFactory = PostDataSourceFactory(viewModelScope)
        val popularDataSourceFactory = PopularDataSourceFactory(viewModelScope)

        postLiveDataSource = postDataSourceFactory.getPostLiveDataSource()
        popularLiveDataSource = popularDataSourceFactory.getPopularLiveDataSource()

        postPagedList = LivePagedListBuilder(postDataSourceFactory, config).build()
        popularPagedList = LivePagedListBuilder(popularDataSourceFactory, config).build()
    }
}