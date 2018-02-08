package tensor_programming.coinwatch.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import tensor_programming.coinwatch.CurrencyApp
import tensor_programming.coinwatch.db.CurrencyDatabase

/**
 * Created by Tensor on 2/6/2018.
 */
class CurrencyPagerViewModel: ViewModel() {

    private val mDb: CurrencyDatabase = CurrencyApp.db!!

    private var mCurrentPosition: Int = -1
    private val mFilter: MutableLiveData<String> = MutableLiveData()
    private val mCurrencyIdsList: LiveData<List<String>> = Transformations
            .switchMap(mFilter, { name ->
                if (name.isNullOrEmpty()) {
                    mDb.currencyDao().getAllCurrencyIds()
                } else {
                    mDb.currencyDao().filterCurrencyIds("$name%")
                }
            })

    fun getCurrencyIdList(): LiveData<List<String>> {
        return mCurrencyIdsList
    }

    fun setFilter(name: String?) {
        mFilter.value = name
    }

    fun getPosition(): Int {
        return mCurrentPosition
    }

    fun setPosition(position: Int) {
        mCurrentPosition = position
    }
}