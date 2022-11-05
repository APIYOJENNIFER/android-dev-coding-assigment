package com.ensibuuko.android_dev_coding_assigment.data

import androidx.room.*

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallet_table")
    suspend fun getWallet(): Wallet

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wallet: Wallet)

    @Update
    suspend fun update(wallet: Wallet)
}