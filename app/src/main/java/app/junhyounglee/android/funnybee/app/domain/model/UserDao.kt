package app.junhyounglee.android.funnybee.app.domain.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Create: 9/03/19
 * Author: Jun Hyoung Lee
 * Email: niceguy0315@hotmail.com
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM Users")
    fun getAll(): List<User>

    @Query("SELECT * FROM Users WHERE id = :id")
    fun get(id: String): User?

    @Insert
    fun addOrUpdate(user: User)

    /*
     * reviewer comment
     * - harjot:
     *      no need to delete the user
     */
    @Query("DELETE FROM Users WHERE id = :id")
    fun delete(id: String)

    @Query("DELETE FROM Users")
    fun deleteAll()
}

fun UserDao.delete(user: User) {
    delete(user.id)
}