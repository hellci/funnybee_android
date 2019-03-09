package app.junhyounglee.android.funnybee.app.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Create: 9/03/19
 * Author: Jun Hyoung Lee
 * Email: niceguy0315@hotmail.com
 */
@Entity(
    tableName = "Posts",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class Post(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val userId: Int,
    var title: String?,
    var comment: String?
)