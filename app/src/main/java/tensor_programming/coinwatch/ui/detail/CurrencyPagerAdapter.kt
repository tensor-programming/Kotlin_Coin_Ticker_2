package tensor_programming.coinwatch.ui.detail


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
/**
 * Created by Tensor on 2/6/2018.
 */
class CurrencyPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private var mCurrencyIdList: List<String>? = null

    fun setCurrencyIdList(currencyIdList: List<String>?) {
        if (currencyIdList != null) {
            mCurrencyIdList = currencyIdList
            notifyDataSetChanged()
        }
    }

    override fun getItem(position: Int): Fragment {
        val currencyId: String = mCurrencyIdList!![position]
        return CurrencyDetailFragment.newInstance(currencyId)
    }

    override fun getCount(): Int {
        return mCurrencyIdList?.size ?: 0
    }
}
