package com.ensibuuko.android_dev_coding_assigment.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class WalletDaoTest {

    private lateinit var database: PostsDatabase
    private lateinit var dao: WalletDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PostsDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.walletDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert() = runBlocking {
        val walletItem = Wallet(id = 1, 1, "100")
        dao.insert(walletItem)

        val currentWallet = dao.getWallet()
        assertThat(currentWallet).isEqualTo(walletItem)
    }

    @Test
    fun update() = runBlocking {
        val walletItem = Wallet(id = 1, 1, "100")
        dao.insert(walletItem)

        val updateWalletItem = Wallet(id = 1, 1, "200")
        dao.update(updateWalletItem)

        val updatedWallet = dao.getWallet()

        assertThat(updatedWallet).isEqualTo(updateWalletItem)
    }
}