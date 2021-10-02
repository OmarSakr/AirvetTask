package com.codevalley.airvettask.utils

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.codevalley.airvettask.dao.UserDao
import com.codevalley.airvettask.models.Location
import com.codevalley.airvettask.models.Name
import com.codevalley.airvettask.models.Picture
import com.codevalley.airvettask.models.Result
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class UsersRoomDatabaseTest {

    private lateinit var db: UsersRoomDatabase
    private lateinit var dao: UserDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UsersRoomDatabase::class.java).build()
        dao = db.userDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun addAndReadUsers() = runBlocking {
        val location = Location("Cairo", "Egypt")
        val name = Name("Omar")
        val picture = Picture("")
        val user = Result(
            1, "+2001025646187",
            "omarnagah3@gmail.com", "male", location, name,
            "EG", "+201025646187", picture
        )

        val location1 = Location("Vienna", "Austria")
        val name1 = Name("israa")
        val picture1 = Picture("")

        val user1 = Result(
            1, "+2001025646155",
            "israa@gmail.com", "female", location1, name1,
            "AU", "+201025646155", picture1
        )
        dao.insertUser(user)
        dao.insertUser(user1)
        val users = dao.getUsers()
        assertNotNull(users)

    }

}