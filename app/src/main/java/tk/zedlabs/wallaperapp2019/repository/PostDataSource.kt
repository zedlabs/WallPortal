package tk.zedlabs.wallaperapp2019.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import retrofit2.Call
import retrofit2.Response
import tk.zedlabs.wallaperapp2019.data.JsonApi
import tk.zedlabs.wallaperapp2019.data.RetrofitService
import tk.zedlabs.wallaperapp2019.models.UnsplashImageDetails

class PostDataSource : PageKeyedDataSource<Int, UnsplashImageDetails>() {

     val PAGE_SIZE = 20
     val FIRST_PAGE = 1
     val accessKey = "e3bc7bf237473a863b587b27220ec9b4a0a6f25e8b1514053c91d212a312b777"
     var  jsonApi : JsonApi

    init {
        jsonApi = RetrofitService.createService(JsonApi::class.java)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UnsplashImageDetails>) {

        val call = jsonApi.getImages(accessKey,FIRST_PAGE,PAGE_SIZE)

        call.enqueue(object : retrofit2.Callback<List<UnsplashImageDetails>> {

            override fun onResponse(call: Call<List<UnsplashImageDetails>>,
                                    response: Response<List<UnsplashImageDetails>>
            ) {
                if (response.isSuccessful){
                    callback.onResult(response.body()!!,null,FIRST_PAGE+1)
                }
            }
            override fun onFailure(call: Call<List<UnsplashImageDetails>>, t: Throwable) {
                Log.e("repository->Posts",""+t.message)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UnsplashImageDetails>) {
        val call = jsonApi.getImages(accessKey,params.key,PAGE_SIZE)

        call.enqueue(object : retrofit2.Callback<List<UnsplashImageDetails>> {

            override fun onResponse(call: Call<List<UnsplashImageDetails>>,
                                    response: Response<List<UnsplashImageDetails>>
            ) {
                if (response.isSuccessful){
                    val key: Int?
                    if(response.body()?.isNotEmpty()!!) key = params.key+1
                    else key = null
                    callback.onResult(response.body()!!,key)
                }
            }
            override fun onFailure(call: Call<List<UnsplashImageDetails>>, t: Throwable) {
                Log.e("repository->Posts",""+t.message)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UnsplashImageDetails>) {
        val call = jsonApi.getImages(accessKey,params.key,PAGE_SIZE)

        call.enqueue(object : retrofit2.Callback<List<UnsplashImageDetails>> {

            override fun onResponse(call: Call<List<UnsplashImageDetails>>,
                                    response: Response<List<UnsplashImageDetails>>
            ) {
                val key: Int?
                if(params.key > 1) key = params.key-1
                else key = null

                if (response.isSuccessful){
                    callback.onResult(response.body()!!,key)
                }
            }
            override fun onFailure(call: Call<List<UnsplashImageDetails>>, t: Throwable) {
                Log.e("repository->Posts",""+t.message)
            }
        })
    }

}