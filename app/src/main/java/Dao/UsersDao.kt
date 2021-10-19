package Dao

import Entity.User
import androidx.room.*

@Dao
interface UsersDao {

    @Query("select*from user")
    fun getAllUser(): List<User>

    @Insert
    fun addUser(person: User)

    @Delete
    fun deleteUser(person: User)

    @Update
    fun editUser(person: User)


}