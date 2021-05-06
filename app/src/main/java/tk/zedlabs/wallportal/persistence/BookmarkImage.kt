package tk.zedlabs.wallportal.persistence

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class BookmarkImage(
    @PrimaryKey val imageName: String,
    @ColumnInfo val imageUrlFull: String?,
    @ColumnInfo val imageUrlRegular: String?
) : Parcelable