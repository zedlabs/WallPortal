package tk.zedlabs.wallportal.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.repository.PopularDataSource
import tk.zedlabs.wallportal.repository.PostDataSource

class PostViewModel @ViewModelInject constructor(
    private val popularDataSource: PopularDataSource,
    private val postDataSource: PostDataSource
) : ViewModel() {

    val postList: Flow<PagingData<WallHavenResponse>> = Pager(PagingConfig(pageSize = 20)) {
        popularDataSource
    }.flow.cachedIn(viewModelScope)

    val postListNew: Flow<PagingData<WallHavenResponse>> = Pager(PagingConfig(pageSize = 20)) {
        postDataSource
    }.flow.cachedIn(viewModelScope)

}