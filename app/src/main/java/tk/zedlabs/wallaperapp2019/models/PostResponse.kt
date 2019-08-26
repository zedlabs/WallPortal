package tk.zedlabs.wallaperapp2019.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostResponse {

    @SerializedName("posts")
    @Expose
    var postObjects : List<UnsplashImageDetails> ?= null
}