package app.junhyounglee.android.funnybee.app.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Create: 9/03/19
 * Author: Jun Hyoung Lee
 * Email: niceguy0315@hotmail.com
 */
@Parcelize
@Entity(tableName = "Users")
data class User(
    @PrimaryKey var id: String,
    var password: String
) : Parcelable
