package tk.zedlabs.wallportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.repository.PopularDataSource
import tk.zedlabs.wallportal.repository.PostDataSource

class PostViewModel : ViewModel() {

    val postList: Flow<PagingData<WallHavenResponse>> = Pager(PagingConfig(pageSize = 20)) {
        PopularDataSource()
    }.flow.cachedIn(viewModelScope)

    val postListNew: Flow<PagingData<WallHavenResponse>> = Pager(PagingConfig(pageSize = 20)) {
        PostDataSource()
    }.flow.cachedIn(viewModelScope)

}