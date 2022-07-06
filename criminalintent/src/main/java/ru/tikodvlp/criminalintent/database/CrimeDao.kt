package ru.tikodvlp.criminalintent.database

import Crime
import androidx.room.Dao
import androidx.room.Query
import java.util.*

@Dao
interface CrimeDao {
@Query("Select * FROM crime")
fun getCrimes(): List<Crime>

@Query("Select * FROM crime WHERE id=(:id)")
fun getCrime(id: UUID): Crime?
}