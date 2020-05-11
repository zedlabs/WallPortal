package tk.zedlabs.wallaperapp2019.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import tk.zedlabs.wallaperapp2019.models.WallHavenResponse
import tk.zedlabs.wallaperapp2019.repository.PopularDataSource
import tk.zedlabs.wallaperapp2019.repository.PopularDataSourceFactory
import tk.zedlabs.wallaperapp2019.repository.PostDataSource
import tk.zedlabs.wallaperapp2019.repository.PostDataSourceFactory

class PostViewModel : ViewModel() {

    var postPagedList: LiveData<PagedList<WallHavenResponse>>? = null
    private var postLiveDataSource: LiveData<PageKeyedDataSource<Int, WallHavenResponse>>? = null

    var popularPagedList: LiveData<PagedList<WallHavenResponse>>? = null
    private var popularLiveDataSource: LiveData<PageKeyedDataSource<Int, WallHavenResponse>>? = null

    init {
        val postDataSourceFactory = PostDataSourceFactory(viewModelScope)
        val popularDataSourceFactory = PopularDataSourceFactory(viewModelScope)

        postLiveDataSource = postDataSourceFactory.getPostLiveDataSource()
        popularLiveDataSource = popularDataSourceFactory.getPopularLiveDataSource()

        val config: PagedList.Config = (PagedList.Config.Builder())
            .setPageSize(PostDataSource(viewModelScope).PAGE_SIZE)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(24)
            .setPrefetchDistance(24)
            .build()

        val configPop: PagedList.Config = (PagedList.Config.Builder())
            .setEnablePlaceholders(true)
            .setPageSize(PopularDataSource(viewModelScope).PAGE_SIZE)
            .build()

        postPagedList = LivePagedListBuilder(postDataSourceFactory, config).build()
        popularPagedList = LivePagedListBuilder(popularDataSourceFactory, configPop).build()
    }
}