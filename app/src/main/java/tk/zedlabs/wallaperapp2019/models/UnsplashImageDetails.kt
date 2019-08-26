package tk.zedlabs.wallaperapp2019.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UnsplashImageDetails {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
    @SerializedName("width")
    @Expose
    var width: Int? = null
    @SerializedName("height")
    @Expose
    var height: Int? = null
    @SerializedName("color")
    @Expose
    var color: String? = null
    @SerializedName("likes")
    @Expose
    var likes: Int? = null
    @SerializedName("liked_by_user")
    @Expose
    var likedByUser: Boolean? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("urls")
    @Expose
    var urls: Urls? = null
//    @SerializedName("user")
//    @Expose
//    var user: User? = null

//    @SerializedName("links")
//    @Expose
//    var links: Links_? = null

}