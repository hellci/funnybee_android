package app.junhyounglee.android.funnybee.app.domain.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Create: 9/03/19
 * Author: Jun Hyoung Lee
 * Email: niceguy0315@hotmail.com
 */
@Dao
interface PostDao {

    @Query("SELECT * FROM Posts")
    fun getAll(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdate(post: Post)

    @Query("DELETE FROM Posts WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM Posts")
    fun deleteAll()
}

fun PostDao.delete(post: Post) {
    delete(post.id)
}