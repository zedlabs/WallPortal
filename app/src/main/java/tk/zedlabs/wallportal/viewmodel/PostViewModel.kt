package tk.zedlabs.wallportal.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.repository.PopularDataSource
import tk.zedlabs.wallportal.repository.PostDataSource
import tk.zedlabs.wallportal.util.Constants.PAGE_SIZE
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val popularDataSource: PopularDataSource,
    private val postDataSource: PostDataSource,
    private val repository: ImageDetailsRepository
) : ViewModel() {

    var newList = mutableStateOf<List<WallHavenResponse>>(listOf())
    val popList : MutableState<List<WallHavenResponse>> = mutableStateOf(ArrayList())

    //current pagination page
    val pageNew  = mutableStateOf(1)
    val pagePopular  = mutableStateOf(1)

    val loading = mutableStateOf(false)

    private var postListScrollPosition = 0
    private var postListNewScrollPosition = 0

    init {
        //create the loading state
        loadInitData()
    }

    fun loadInitData(){
        viewModelScope.launch {
            loading.value = true
            val newResult = repository.getNewList(1)
            newList.value = newResult
            val popResult = repository.getPopularList(1)
            popList.value = popResult
            loading.value = false
        }
    }

    private fun incrementNewPage(){
        pageNew.value = pageNew.value + 1
    }
    private fun incrementPopularPage(){
        pagePopular.value = pagePopular.value + 1
    }

    fun onChangeNewScrollPosition(position: Int){
        postListNewScrollPosition = position
    }
    fun onChangePopularScrollPosition(position: Int){
        postListScrollPosition = position
    }

    fun addNew(newData: List<WallHavenResponse>){
        newList.value += newData
    }
    fun addPopular(newData: List<WallHavenResponse>){
        val current = ArrayList(this.popList.value)
        current.addAll(newData)
        this.popList.value = current
        //current.clear()
    }

    fun nextPageNew(){
        viewModelScope.launch {
            if((postListNewScrollPosition + 1) >= pageNew.value * PAGE_SIZE){
                loading.value = true
                //make loader appear
                incrementNewPage()
                Log.e("VM", "nextpageNew: ${pageNew.value}")

                if(pageNew.value > 1){
                    val result = repository.getNewList(pageNew.value)
                    Log.e("CPR", "nextpageNew: adding new data ")
                    addNew(result)
                }
                loading.value = false
            }
        }
    }
    fun nextpagePop(){
        viewModelScope.launch {
            if((postListScrollPosition + 1) >= pagePopular.value * PAGE_SIZE){
                loading.value = true
                //make loader appear
                incrementPopularPage()
                Log.e("VM", "nextpagePop: ${pagePopular.value}")

                if(pagePopular.value > 1){
                    val result = repository.getNewList(pagePopular.value)
                    Log.e("CPR", "nextpagePop: $result ")
                    addNew(result)
                }
                loading.value = false
            }
        }
    }

    val postList: Flow<PagingData<WallHavenResponse>> = Pager(PagingConfig(pageSize = 20)) {
        popularDataSource
    }.flow.cachedIn(viewModelScope)

    val postListNew: Flow<PagingData<WallHavenResponse>> = Pager(PagingConfig(pageSize = 20)) {
        postDataSource
    }.flow.cachedIn(viewModelScope)

}