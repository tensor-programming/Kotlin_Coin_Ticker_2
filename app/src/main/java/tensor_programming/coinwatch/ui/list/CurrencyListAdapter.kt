package tensor_programming.coinwatch.ui.list

import java.util.*
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import tensor_programming.coinwatch.R
import tensor_programming.coinwatch.databinding.CurrencyListItemBinding
import tensor_programming.coinwatch.ui.ItemClickListener
import tensor_programming.coinwatch.utils.CurrencyAppUtils
import tensor_programming.coinwatch.db.entity.Currency

/**
 * Created by Tensor on 2/6/2018.
 */
class CurrencyListAdapter(context: Context, listener: ItemClickListener):
        RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {

    var mContext: Context = context
    private var mCurrencyList: List<Currency>? = null
    //    private var mCurrencyIconMap: HashMap<String, String>? = null
    private val mItemClickListener: ItemClickListener = listener

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CurrencyViewHolder {
        val itemBinding: CurrencyListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.currency_list_item,
                parent, false)

        return CurrencyViewHolder(itemBinding, mItemClickListener)
    }

    override fun getItemId(position: Int): Long {
        val s = mCurrencyList!![position].id
        return UUID.nameUUIDFromBytes(s.toByteArray()).mostSignificantBits
    }

    override fun getItemCount(): Int {
        return mCurrencyList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder?, position: Int) {
        holder?.setCurrency(mCurrencyList!![position])
    }

    fun setCurrencyList(currencyList: List<tensor_programming.coinwatch.db.entity.Currency>?) {
        mCurrencyList = currencyList
        notifyDataSetChanged()
    }

//    fun setCurrencyIconMap(map: HashMap<String, String>) {
//        mCurrencyIconMap = map
//    }

    inner class CurrencyViewHolder(itemBinding: CurrencyListItemBinding, listener: ItemClickListener):
            RecyclerView.ViewHolder(itemBinding.root),
            View.OnClickListener,
            View.OnLongClickListener {

        private val mListener = listener
        private val mBinding = itemBinding

        fun setCurrency(currency: Currency) {
            mBinding.currency = currency
            Picasso.with(mContext)
                    .load(CurrencyAppUtils.getIconUrlForId(currency.id))
                    .into(mBinding.imCurrencyLogo)
//            val iconUrl = mCurrencyIconMap?.get(currency.symbol)
//            if (iconUrl != null) {
//                Picasso.with(mContext).load(iconUrl).into(mBinding.imCurrencyLogo)
//            }
        }

        override fun onClick(p0: View?) {
            mListener.onClick(mBinding.currency!!)
        }

        override fun onLongClick(p0: View?): Boolean {
            return mListener.onLongClick(mBinding.currency!!)
        }

        init {
            mBinding.currencyItem.setOnClickListener(this)
            mBinding.currencyItem.setOnLongClickListener(this)
        }
    }
}