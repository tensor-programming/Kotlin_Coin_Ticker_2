package tensor_programming.coinwatch.db

/**
 * Created by Tensor on 2/6/2018.
 */
import android.os.AsyncTask
import tensor_programming.coinwatch.db.entity.Currency

class DbInsertTask(private val mDb: CurrencyDatabase):
        AsyncTask<List<Currency>, Unit, Unit>() {

    companion object {
        interface DbInsertTaskComplete {
            fun onDbInsertTaskComplete()
        }
    }

    private var mListener: DbInsertTaskComplete? = null

    fun setCallback(listener: DbInsertTaskComplete) {
        mListener = listener
    }

    override fun doInBackground(vararg currencies: List<Currency>?) {
        if (currencies.isNotEmpty()) {
            val currency = currencies[0]
            if (currency != null) {
                mDb.currencyDao().addCurrencies(currency)
            }
        }
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        mListener?.onDbInsertTaskComplete()
    }
}