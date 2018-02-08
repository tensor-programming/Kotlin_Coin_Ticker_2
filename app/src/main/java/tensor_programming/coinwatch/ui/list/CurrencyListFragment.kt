package tensor_programming.coinwatch.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tensor_programming.coinwatch.R
import tensor_programming.coinwatch.db.entity.Currency
import tensor_programming.coinwatch.ui.ItemClickListener
import tensor_programming.coinwatch.databinding.FragmentCurrencyListBinding

/**
 * Created by Tensor on 2/6/2018.
 */
class CurrencyListFragment:
        Fragment(),
        ItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TAG = "CurrencyListFragment"
        fun newInstance(): CurrencyListFragment {
            return CurrencyListFragment()
        }

        interface CurrencyListFragmentActions {
            fun onCurrencyItemClick(currency: Currency)
            fun onCurrencyItemLongClick(currency: Currency): Boolean
        }
    }

    private var mAdapter: CurrencyListAdapter? = null
    private var mBinding: FragmentCurrencyListBinding? = null
    private var mViewModel: CurrencyListViewModel? = null
    private var mListener: CurrencyListFragmentActions? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as CurrencyListFragmentActions
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mAdapter = CurrencyListAdapter(context,this)
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_currency_list, container, false)
        mBinding?.isLoading = true
        mBinding?.swipeRefresh?.isRefreshing = false
        mBinding?.swipeRefresh?.setOnRefreshListener(this)
        mBinding?.swipeRefresh?.setColorSchemeResources(
                R.color.colorAccent
        )
        mBinding?.currencyList?.layoutManager = LinearLayoutManager(context)
        mBinding?.currencyList?.adapter = mAdapter
        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity).get(CurrencyListViewModel::class.java)
//        mAdapter?.setCurrencyIconMap(mViewModel!!.getCurrencyImageMap())
        subscribeUi()
    }

    private fun subscribeUi() {
        mViewModel?.getCurrencyList()?.observe(this, Observer<List<Currency>> {
            mAdapter?.setCurrencyList(it)
            mBinding?.isLoading = false
        })

        mViewModel?.isUpdating()?.observe(this, Observer<Boolean> {
            mBinding?.swipeRefresh?.isRefreshing = it ?: false
        })
    }

    fun setFilter(name: String) {
        mViewModel?.setFilter(name)
    }

    override fun onClick(currency: Currency) {
        mListener?.onCurrencyItemClick(currency)
    }

    override fun onLongClick(currency: Currency): Boolean {
        return mListener?.onCurrencyItemLongClick(currency) == true
    }

    override fun onRefresh() {
        mViewModel?.updateCurrencies()
    }
}