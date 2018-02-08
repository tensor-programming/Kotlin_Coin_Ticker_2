package tensor_programming.coinwatch.ui

import tensor_programming.coinwatch.db.entity.Currency

/**
 * Created by Tensor on 2/6/2018.
 */
interface ItemClickListener {
    fun onClick(currency: Currency)
    fun onLongClick(currency: Currency): Boolean
}