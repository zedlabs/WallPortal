package tk.zedlabs.wallportal.models
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WallHavenResponse {

    @SerializedName("id")
    @Expose
    val id: String? = null

    @SerializedName("url")
    @Expose
     val url: String? = null

    @SerializedName("short_url")
    @Expose
     val shortUrl: String? = null

    @SerializedName("views")
    @Expose
     val views: Int? = null

    @SerializedName("favorites")
    @Expose
     val favorites: Int? = null

    @SerializedName("source")
    @Expose
     val source: String? = null

    @SerializedName("purity")
    @Expose
     val purity: String? = null

    @SerializedName("category")
    @Expose
     val category: String? = null

    @SerializedName("dimension_x")
    @Expose
     val dimensionX: Int? = null

    @SerializedName("dimension_y")
    @Expose
     val dimensionY: Int? = null

    @SerializedName("resolution")
    @Expose
     val resolution: String? = null

    @SerializedName("ratio")
    @Expose
     val ratio: String? = null

    @SerializedName("file_size")
    @Expose
     val fileSize: Int? = null

    @SerializedName("file_type")
    @Expose
     val fileType: String? = null

    @SerializedName("created_at")
    @Expose
     val createdAt: String? = null

    @SerializedName("colors")
    @Expose
     val colors: List<String>? = null

    @SerializedName("path")
    @Expose
     val path: String? = null

    @SerializedName("thumbs")
    @Expose
     val thumbs: Thumbs? = null
}

class Thumbs {
    @SerializedName("large")
    @Expose
     val large: String? = null

    @SerializedName("original")
    @Expose
     val original: String? = null

    @SerializedName("small")
    @Expose
     val small: String? = null
}
