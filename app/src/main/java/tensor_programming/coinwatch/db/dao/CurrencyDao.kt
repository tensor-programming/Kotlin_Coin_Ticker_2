package tensor_programming.coinwatch.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import tensor_programming.coinwatch.db.entity.Currency

/**
 * Created by Tensor on 2/6/2018.
 */
@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency ORDER BY rank ASC")
    fun getAllCurrencies(): LiveData<List<Currency>>

    @Query("SELECT id FROM currency ORDER BY rank ASC")
    fun getAllCurrencyIds(): LiveData<List<String>>

    @Query("SELECT * FROM currency WHERE name LIKE :name ORDER BY rank")
    fun filterCurrency(name: String): LiveData<List<Currency>>

    @Query("SELECT id FROM currency WHERE name LIKE :name ORDER BY rank")
    fun filterCurrencyIds(name: String): LiveData<List<String>>

    @Query("SELECT * FROM currency WHERE id = :id")
    fun getCurrency(id: String): LiveData<Currency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrency(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrencies(currency: List<Currency>)

    @Update
    fun updateCurrency(currency: Currency)

    @Delete
    fun deleteCurrency(currency: Currency)
}