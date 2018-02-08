package tensor_programming.coinwatch.ui.list

/**
 * Created by Tensor on 2/6/2018.
 */

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tensor_programming.coinwatch.CurrencyApp
import tensor_programming.coinwatch.db.CurrencyDatabase
import tensor_programming.coinwatch.db.DbInsertTask
import tensor_programming.coinwatch.db.entity.Currency
import tensor_programming.coinwatch.net.CoinMarketCapService
import tensor_programming.coinwatch.net.CoinMarketCapServiceBuilder

class CurrencyListViewModel(app: Application): AndroidViewModel(app) {
    companion object {
        const val TARGET_CURRENCY = "GBP"
    }

    private val mApi: CoinMarketCapService = CoinMarketCapServiceBuilder().getCoinMarketCapService()
    private val mDb: CurrencyDatabase = CurrencyApp.db!!
    private var mHaveUpdated: Boolean = false
    private val mUpdating: MutableLiveData<Boolean> = MutableLiveData()

    private val mNameFilter: MutableLiveData<String> = MutableLiveData()
    private val mCurrencyResult: LiveData<List<Currency>> = Transformations
            .switchMap(mNameFilter, { name -> loadCurrencyData("$name%") })

//    private val mImageUrls: HashMap<String,String>

    init {
        mNameFilter.value = ""
//        mImageUrls = CurrencyAppUtils.loadIconUrlsFromFile(app, "icons.json")
//        println("DEBUG-" + mImageUrls)
    }

    private fun loadCurrencyData(name: String): LiveData<List<Currency>> {
        return if (name.isEmpty()) {
            mDb.currencyDao().getAllCurrencies()
        } else {
            mDb.currencyDao().filterCurrency(name)
        }
    }

    fun getCurrencyList(): LiveData<List<Currency>> {
        if (!mHaveUpdated) {
            updateCurrencies()
            mHaveUpdated = true
        }
        return mCurrencyResult
    }

//    fun getCurrencyImageMap(): HashMap<String, String> {
//        return mImageUrls
//    }

    fun getFilter(): String? {
        return mNameFilter.value
    }

    fun setFilter(name: String) {
        mNameFilter.value = name
    }

    fun isUpdating(): LiveData<Boolean> {
        return mUpdating
    }

    fun updateCurrencies() {
        mUpdating.value = true
        val call = mApi.getConvertedCurrencies(TARGET_CURRENCY)
        call.enqueue(object: Callback<List<Currency>> {
            override fun onFailure(call: Call<List<Currency>>?, t: Throwable?) {
                mUpdating.value = false
            }

            override fun onResponse(call: Call<List<Currency>>?, response: Response<List<Currency>>?) {
                val dbTask = DbInsertTask(mDb)
                dbTask.setCallback(object : DbInsertTask.Companion.DbInsertTaskComplete {
                    override fun onDbInsertTaskComplete() {
                        mUpdating.value = false
                    }
                })
                dbTask.execute(response?.body())
            }
        })
    }
}