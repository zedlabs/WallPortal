package tk.zedlabs.wallportal.ui.fragment

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.util.Constants
import tk.zedlabs.wallportal.util.Constants.PAGE_SIZE
import tk.zedlabs.wallportal.util.Resource
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: ImageDetailsRepository
) : ViewModel() {

    var newList = mutableStateOf<List<WallHavenResponse>>(listOf())
    val popList = mutableStateOf<List<WallHavenResponse>>(listOf())

    var searchQuery = mutableStateOf(Constants.queryParamNew)
    var searchProgress = mutableStateOf(false)

    //current pagination page
    val pageNew = mutableStateOf(1)
    val pagePopular = mutableStateOf(1)

    val loadingNew = mutableStateOf(true)
    val loadingPop = mutableStateOf(true)

    private var postListScrollPosition = 0
    private var postListNewScrollPosition = 0

    init {
        loadInitData()
    }

    fun loadInitData() {
        searchProgress.value = true
        newList.value = emptyList()
        pageNew.value = 1
        viewModelScope.launch {
            val newResult = repository.getNewList(pageNew.value, searchQuery.value)
            if (newResult is Resource.Success) {
                newList.value = newResult.data as List<WallHavenResponse>
            }
            loadingNew.value = false
            searchProgress.value = false

            val popResult = repository.getPopularList(1)
            if (popResult is Resource.Success) {
                popList.value += popResult.data as List<WallHavenResponse>
            }
            loadingPop.value = false
        }
    }

    fun updateSearch(newQuery: String) {
        searchQuery.value = newQuery
    }

    private fun incrementNewPage() {
        pageNew.value = pageNew.value + 1
    }

    private fun incrementPopularPage() {
        pagePopular.value = pagePopular.value + 1
    }

    fun onChangeNewScrollPosition(position: Int) {
        postListNewScrollPosition = position
    }

    fun onChangePopularScrollPosition(position: Int) {
        postListScrollPosition = position
    }

    fun nextPageNew() {
        viewModelScope.launch {
            if ((postListNewScrollPosition + 1) >= pageNew.value * PAGE_SIZE) {
                loadingNew.value = true
                incrementNewPage()
                Log.e("VM", "New: ${pageNew.value}")

                if (pageNew.value > 1) {
                    val result = repository.getNewList(pageNew.value, searchQuery.value)
                    if (result is Resource.Success) {
                        newList.value += result.data as List<WallHavenResponse>
                    }
                }
                loadingNew.value = false
            }
        }
    }

    fun nextPagePop() {
        viewModelScope.launch {
            if ((postListScrollPosition + 1) >= pagePopular.value * PAGE_SIZE) {
                loadingPop.value = true
                incrementPopularPage()
                Log.e("VM", "Pop: ${pagePopular.value}")

                if (pagePopular.value > 1) {
                    val result = repository.getPopularList(pagePopular.value)
                    if (result is Resource.Success) {
                        popList.value += result.data as List<WallHavenResponse>
                    }
                }
                loadingPop.value = false
            }
        }
    }

}