package ru.tikodvlp.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.tikodvlp.criminalintent.Crime
import java.util.*

@Dao
interface CrimeDao {
    @Query("Select * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>

    @Query("Select * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime>?
}