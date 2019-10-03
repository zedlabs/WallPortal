package tk.zedlabs.wallaperapp2019

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import tk.zedlabs.wallaperapp2019.models.UnsplashImageDetails
import tk.zedlabs.wallaperapp2019.repository.PopularDataSource
import tk.zedlabs.wallaperapp2019.repository.PopularDataSourceFactory
import tk.zedlabs.wallaperapp2019.repository.PostDataSource
import tk.zedlabs.wallaperapp2019.repository.PostDataSourceFactory

class PostViewModel : ViewModel() {

    var postPagedList: LiveData<PagedList<UnsplashImageDetails>>? = null
    private var postLiveDataSource: LiveData<PageKeyedDataSource<Int, UnsplashImageDetails>>? = null

    var popularPagedList: LiveData<PagedList<UnsplashImageDetails>>? = null
    private var popularLiveDataSource: LiveData<PageKeyedDataSource<Int, UnsplashImageDetails>>? = null

    init {
        val postDataSourceFactory = PostDataSourceFactory(viewModelScope)
        val popularDataSourceFactory = PopularDataSourceFactory(viewModelScope)

        postLiveDataSource = postDataSourceFactory.getPostLiveDataSource()
        popularLiveDataSource = popularDataSourceFactory.getPopularLiveDataSource()

        val config: PagedList.Config = (PagedList.Config.Builder()).setEnablePlaceholders(false)
            .setPageSize(PostDataSource(viewModelScope).PAGE_SIZE).build()
        val configPop: PagedList.Config = (PagedList.Config.Builder()).setEnablePlaceholders(false)
            .setPageSize(PopularDataSource(viewModelScope).PAGE_SIZE).build()

        postPagedList = LivePagedListBuilder(postDataSourceFactory, config).build()
        popularPagedList = LivePagedListBuilder(popularDataSourceFactory, configPop).build()
    }
}