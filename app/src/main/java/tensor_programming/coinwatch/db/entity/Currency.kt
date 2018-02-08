package tensor_programming.coinwatch.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Tensor on 2/6/2018.
 */
@Entity
data class Currency(
        @PrimaryKey
        var id: String = "",

        var name: String = "",
        var symbol: String = "",
        var rank: Int = 0,

        var priceUsd: Double = 0.0,
        var priceBtc: Double = 0.0,
        var priceGbp: Double = 0.0,

        @SerializedName("24h_volume_usd") var volumeUsd: Double = 0.0,
        @SerializedName("24h_volume_gbp") var volumeGbp: Double = 0.0,

        var marketCapUsd: Double = 0.0,
        var marketCapGbp: Double = 0.0,

        var availableSupply: Double = 0.0,
        var totalSupply: Double = 0.0,
        var maxSupply: Double = 0.0,

        var percentChange1h: Float = 0.0f,
        var percentChange7d: Float = 0.0f,
        var percentChange24h: Float = 0.0f,

        var lastUpdated: Int = 0
)