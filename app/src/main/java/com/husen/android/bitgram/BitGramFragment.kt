package com.husen.android.bitgram

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import java.math.RoundingMode
import java.text.DecimalFormat

private const val TAG = "BitgramFragment"

class BitGramFragment : Fragment() {

    private lateinit var bitRecyclerView: RecyclerView
    private var adapter: BitAdapter? = null
    private val bitGramViewModel: BitGramViewModel by lazy {
        ViewModelProviders.of(this).get(BitGramViewModel::class.java)
    }
    private lateinit var thumbnailDownloader: ThumbnailDownloader<BitHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
        bitGramViewModel.listDataSource()

        thumbnailDownloader = ThumbnailDownloader()
        lifecycle.addObserver(thumbnailDownloader)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bitgram, container, false)

        bitRecyclerView = view.findViewById(R.id.bit_recycler_view) as RecyclerView
        bitRecyclerView.layoutManager = LinearLayoutManager(context)
        bitRecyclerView.adapter = adapter
        adapter?.notifyDataSetChanged()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSourceList = bitGramViewModel.dataSourceList

        bitGramViewModel.gramItemLiveData.observe(
            viewLifecycleOwner,
            Observer { gramItem ->
                bitRecyclerView.adapter = BitAdapter(gramItem, dataSourceList)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(
            thumbnailDownloader
        )
    }

    private inner class BitHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        private lateinit var gramItem: GramItem
        private var dataSourceList: ArrayList<DataSourceItem?>? = null
        private var dataSourceItem: DataSourceItem? = null

        // Coin Icon & Name & Symbol
        private val bitIcon: ImageView = itemView.findViewById(R.id.iv_bit_list)
        private val bitName: TextView = itemView.findViewById(R.id.tv_bit_name_list)
        private val bitFaName: TextView = itemView.findViewById(R.id.tv_bit_fa_name_list)
        private val bitSymbol: TextView = itemView.findViewById(R.id.tv_bit_symbol_list)

        // Coin Price & Price Percent & Icon in $
        private val usaPrice: TextView = itemView.findViewById(R.id.tv_usa_price)
        private val usaPercent: TextView = itemView.findViewById(R.id.tv_usa_percent)
        private val usaPercentIcon: ImageView = itemView.findViewById(R.id.iv_usa_arrow)

        // Coin Price & Price Percent & Icon in Toman
        private val irPrice: TextView = itemView.findViewById(R.id.tv_ir_price)
        private val irPercent: TextView = itemView.findViewById(R.id.tv_ir_percent)
        private val irPercentIcon: ImageView = itemView.findViewById(R.id.iv_ir_arrow)

        @SuppressLint("SetTextI18n")
        fun bind(gramItem: GramItem, dataSourceList: ArrayList<DataSourceItem?>?) {
            this.gramItem = gramItem
            this.dataSourceList = dataSourceList
            val gramItemSymbol = gramItem.symbol
            dataSourceItem = findBitInDataSource(gramItemSymbol, this.dataSourceList)

            bitIcon.load(dataSourceItem?.bitIconUrl)

            bitName.text = this.dataSourceItem?.bitName
            bitSymbol.text = this.dataSourceItem?.bitSymbol
            bitFaName.text = this.dataSourceItem?.bitFaName

            usaPrice.text = "${this.gramItem.lastPrice}$"
            usaPercent.text = "${(calPercent(
                this.gramItem.changePrice,
                this.gramItem.lastPrice,
                usaPercent,
                usaPercentIcon))}%"
        }
        private fun findBitInDataSource(bitSymbol: String, dataSourceList: ArrayList<DataSourceItem?>?)
                : DataSourceItem? {

            var dataSourceItem: DataSourceItem? = null

            for (item in dataSourceList!!) {
                    if (item?.bitIdSymbol == bitSymbol) {
                        dataSourceItem = item
                }
            }
            return dataSourceItem

        }
        private fun calPercent(changePrice: String, lastPrice: String,
                               tvPercent: TextView, ivPercent: ImageView): String {
            val changePrice = changePrice.toDouble()
            val lastPrice = lastPrice.toDouble()
            val initialNum = lastPrice - changePrice

            val percent = ((changePrice)/(initialNum))*100
            //Round number to 0.01
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            val RoundPercent = df.format(percent)

            changePercentColorAndIcon(percent, tvPercent, ivPercent)

            return RoundPercent
        }
        private fun changePercentColorAndIcon(percent: Double, tvPercent: TextView,
                                              ivPercent: ImageView) {
            if (percent >= 0) {
                tvPercent.setTextColor(resources.getColor(R.color.Green))
                ivPercent.load(R.drawable.up)
            } else if( percent < 0) {
                tvPercent.setTextColor(resources.getColor(R.color.darkerRed))
                ivPercent.load(R.drawable.down)
            }
        }
    }

    private inner class BitAdapter(private val gramItems: List<GramItem>,
                                   var dataSourceList: ArrayList<DataSourceItem?>)
        : RecyclerView.Adapter<BitHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitHolder {
            val view
                    = layoutInflater.inflate(R.layout.bit_list_card_view, parent, false)
            return BitHolder(view)
        }

        override fun getItemCount(): Int = gramItems.size

        override fun onBindViewHolder(holder: BitHolder, position: Int) {
            val gramItem = gramItems[position]
            holder.bind(gramItem, dataSourceList)
//            thumbnailDownloader.queueThumbnail(holder, gramItem.symbol,
//                gramItem.changePrice,
//                gramItem.lastPrice)
        }
    }

    companion object {
        fun newInstance() = BitGramFragment()
    }
}