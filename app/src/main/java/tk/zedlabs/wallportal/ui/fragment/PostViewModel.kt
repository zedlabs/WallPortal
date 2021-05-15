package tk.zedlabs.wallportal.ui.fragment

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.util.Constants.PAGE_SIZE
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: ImageDetailsRepository
) : ViewModel() {

    var newList = mutableStateOf<List<WallHavenResponse>>(listOf())
    val popList = mutableStateOf<List<WallHavenResponse>>(listOf())

    //current pagination page
    val pageNew  = mutableStateOf(1)
    val pagePopular  = mutableStateOf(1)

    val loadingNew = mutableStateOf(true)
    val loadingPop = mutableStateOf(true)

    private var postListScrollPosition = 0
    private var postListNewScrollPosition = 0

    init { loadInitData() }

    private fun loadInitData(){
        viewModelScope.launch {
            val newResult = repository.getNewList(1)
            newList.value = newResult
            loadingNew.value = false
            val popResult = repository.getPopularList(1)
            popList.value = popResult
            loadingPop.value = false
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

    fun nextPageNew(){
        viewModelScope.launch {
            if((postListNewScrollPosition + 1) >= pageNew.value * PAGE_SIZE){
                loadingNew.value = true
                //make loader appear
                incrementNewPage()
                Log.e("VM", "New: ${pageNew.value}")

                if(pageNew.value > 1){
                    val result = repository.getNewList(pageNew.value)
                    newList.value += result
                }
                loadingNew.value = false
            }
        }
    }
    fun nextPagePop(){
        viewModelScope.launch {
            if((postListScrollPosition + 1) >= pagePopular.value * PAGE_SIZE){
                loadingPop.value = true
                incrementPopularPage()
                Log.e("VM", "Pop: ${pagePopular.value}")

                if(pagePopular.value > 1){
                    val result = repository.getNewList(pagePopular.value)
                    popList.value += result
                }
                loadingPop.value = false
            }
        }
    }

}