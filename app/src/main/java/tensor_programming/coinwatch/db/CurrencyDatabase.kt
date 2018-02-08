package tensor_programming.coinwatch.db

/**
 * Created by Tensor on 2/6/2018.
 */
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import tensor_programming.coinwatch.db.dao.CurrencyDao
import tensor_programming.coinwatch.db.entity.Currency

@Database(entities = [Currency::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}