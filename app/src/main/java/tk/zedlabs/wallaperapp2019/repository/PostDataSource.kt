package tk.zedlabs.wallaperapp2019.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*
import tk.zedlabs.wallaperapp2019.data.JsonApi
import tk.zedlabs.wallaperapp2019.data.RetrofitService
import tk.zedlabs.wallaperapp2019.models.UnsplashImageDetails
import java.lang.Exception

class PostDataSource(private val scope:CoroutineScope) : PageKeyedDataSource<Int, UnsplashImageDetails>() {

     val PAGE_SIZE = 20
     val FIRST_PAGE = 1
     val accessKey = "e3bc7bf237473a863b587b27220ec9b4a0a6f25e8b1514053c91d212a312b777"
     var jsonApi : JsonApi

    init {
        jsonApi = RetrofitService.createService(JsonApi::class.java)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UnsplashImageDetails>) {

        scope.launch {
            try {
                val response = jsonApi.getImages(accessKey,FIRST_PAGE,PAGE_SIZE)
                when{
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!,null,FIRST_PAGE+1)
                    }
                }
            }catch (exception : Exception){
                Log.e("PostDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UnsplashImageDetails>) {

        scope.launch {
            try {
                val response = jsonApi.getImages(accessKey, params.key, PAGE_SIZE)

                when{
                    response.isSuccessful -> {
                        val key: Int?
                        if(response.body()?.isNotEmpty()!!) key = params.key+1
                        else key = null
                        callback.onResult(response.body()!!,key)
                    }
                }
            }catch (exception : Exception){
                Log.e("PostDataSource", "Failed to fetch data!(LoadAfter)")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UnsplashImageDetails>) {
        scope.launch {
            try {
                val response = jsonApi.getImages(accessKey, params.key, PAGE_SIZE)
                val key: Int?
                if(params.key > 1) key = params.key-1
                else key = null
                when{
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!,key)
                    }
                }
            }catch (exception : Exception){
                Log.e("PostDataSource", "Failed to fetch data!(LoadBefore)")
            }
        }
    }
    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}