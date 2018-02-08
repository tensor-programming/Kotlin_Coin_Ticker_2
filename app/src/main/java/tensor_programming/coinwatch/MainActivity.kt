package tensor_programming.coinwatch

import android.app.Application
import android.arch.persistence.room.Room
import tensor_programming.coinwatch.db.CurrencyDatabase

class CurrencyApp: Application() {
    companion object {
        var DB_NAME = "currency_db"
        var db: CurrencyDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, CurrencyDatabase::class.java, DB_NAME).build()
    }
}