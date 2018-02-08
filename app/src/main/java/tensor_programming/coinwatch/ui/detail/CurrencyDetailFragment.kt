package tensor_programming.coinwatch.ui.detail

/**
 * Created by Tensor on 2/6/2018.
 */
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import tensor_programming.coinwatch.R
import tensor_programming.coinwatch.databinding.FragmentCurrencyDetailBinding
import tensor_programming.coinwatch.utils.CurrencyAppUtils

class CurrencyDetailFragment: Fragment() {
    companion object {
        //        const val TAG = "CurrencyDetailFragment"
        const val KEY_CURRENCY_ID = "key::currency_id"

        fun newInstance(currencyId: String): CurrencyDetailFragment {
            val bundle = Bundle()
            bundle.putString(KEY_CURRENCY_ID, currencyId)
            val fragment = CurrencyDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mBinding: FragmentCurrencyDetailBinding? = null
    private var mViewModel: CurrencyDetailViewModel? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_currency_detail, container, false)
        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(CurrencyDetailViewModel::class.java)
        val currencyId = arguments.getString(KEY_CURRENCY_ID)
        if (currencyId != null) {
            mViewModel?.setCurrency(currencyId)
        }
        subscribeUi()
    }

    private fun subscribeUi() {
        mViewModel?.getCurrency()?.observe(this, Observer {
            mBinding?.currency = it
            val id = it?.id
            if (id != null) {
                Picasso.with(context)
                        .load(CurrencyAppUtils.getIconUrlForId(id))
                        .into(mBinding!!.imCurrencyLogo)
            }
        })
    }
}