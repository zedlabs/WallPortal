package tk.zedlabs.wallportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.repository.PopularDataSource
import tk.zedlabs.wallportal.repository.PostDataSource
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
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