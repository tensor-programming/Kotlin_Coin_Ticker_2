package tensor_programming.coinwatch.net

/**
 * Created by Tensor on 2/6/2018.
 */
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import tensor_programming.coinwatch.db.entity.Currency
import java.lang.reflect.Type


class CurrencyGsonDeserializer: JsonDeserializer<Currency> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Currency {
        val ob = json as JsonObject

        return Currency(
                id = getString(ob, "id"),
                name = getString(ob, "name"),
                symbol = getString(ob, "symbol"),
                rank = getInt(ob, "rank"),
                priceBtc = getDouble(ob,"price_btc"),
                priceGbp = getDouble(ob,"price_gbp"),
                priceUsd = getDouble(ob,"price_usd"),
                volumeGbp = getDouble(ob,"24h_volume_gbp"),
                volumeUsd = getDouble(ob,"24h_volume_usd"),
                marketCapGbp = getDouble(ob,"market_cap_gbp"),
                marketCapUsd = getDouble(ob,"market_cap_usd"),
                availableSupply = getDouble(ob,"available_supply"),
                totalSupply = getDouble(ob,"total_supply"),
                maxSupply = getDouble(ob,"max_supply"),
                percentChange1h = getFloat(ob, "percent_change_1h"),
                percentChange24h = getFloat(ob, "percent_change_24h"),
                percentChange7d = getFloat(ob, "percent_change_7d"),
                lastUpdated = getInt(ob, "last_updated")
        )
    }

    private fun getDouble(ob:JsonObject, key: String): Double {
        if (ob.has(key)) {
            val value = ob.get(key)
            if (!value.isJsonNull) {
                return value.asDouble
            }
        }
        return 0.0
    }

    private fun getFloat(ob:JsonObject, key: String): Float {
        if (ob.has(key)) {
            val value = ob.get(key)
            if (!value.isJsonNull) {
                return value.asFloat
            }
        }
        return 0.0f
    }

    private fun getInt(ob:JsonObject, key: String): Int {
        if (ob.has(key)) {
            val value = ob.get(key)
            if (!value.isJsonNull) {
                return value.asInt
            }
        }
        return 0
    }

    private fun getString(ob:JsonObject, key: String): String {
        if (ob.has(key)) {
            val value = ob.get(key)
            if (!value.isJsonNull) {
                return value.asString
            }
        }
        return ""
    }
}