package tensor_programming.coinwatch.ui.detail

/**
 * Created by Tensor on 2/6/2018.
 */
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import tensor_programming.coinwatch.CurrencyApp
import tensor_programming.coinwatch.db.CurrencyDatabase
import tensor_programming.coinwatch.db.entity.Currency

class CurrencyDetailViewModel: ViewModel() {
    private val mDb: CurrencyDatabase = CurrencyApp.db!!
    private val mCurrencyId: MutableLiveData<String> = MutableLiveData()
    private val mCurrency: LiveData<Currency> =
            Transformations.switchMap(mCurrencyId,
                    { id -> mDb.currencyDao().getCurrency(id) })

    fun setCurrency(id: String) {
        mCurrencyId.value = id
    }

    fun getCurrency(): LiveData<Currency> {
        return mCurrency
    }
}