package tk.zedlabs.wallportal.models
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainResponse {
    @SerializedName("data")
    @Expose
    val data: List<WallHavenResponse>? = null

    @SerializedName("meta")
    @Expose
    val meta: Meta? = null
}

class Meta {
    @SerializedName("current_page")
    @Expose
    val currentPage: Int? = null

    @SerializedName("last_page")
    @Expose
    val lastPage: Int? = null

    @SerializedName("per_page")
    @Expose
     val perPage: Int? = null

    @SerializedName("total")
    @Expose
     val total: Int? = null

    @SerializedName("query")
    @Expose
     val query: String? = null

    @SerializedName("seed")
    @Expose
     val seed: Any? = null
}
