package com.husen.android.bitgram

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.android.synthetic.main.fragment_bitgram.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

private const val TAG = "BitgramFragment"

class BitGramFragment : Fragment() {

    private lateinit var bitRecyclerView: RecyclerView
    private var adapter: BitAdapter? = null
    private val bitGramViewModel: BitGramViewModel by lazy {
        ViewModelProvider(this).get(BitGramViewModel::class.java)
    }
    private lateinit var thumbnailDownloader: ThumbnailDownloader<BitHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

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

        bitGramViewModel.bitGramItemsLiveData.observe(
            viewLifecycleOwner,
            Observer { gramItem ->
//                bitRecyclerView.adapter = BitAdapter(gramItem)
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

        private lateinit var gramItem: KucoinItem
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
        fun bind(gramItem: BitGramItem) {

            CoroutineScope(Main).launch {

                bitIcon.load(gramItem.bitLogoURL)

                bitName.text = gramItem.bitName
                bitSymbol.text = gramItem.bitSymbol
                bitFaName.text = gramItem.bitFaName

                usaPrice.text = "${gramItem.usaPrice}$"
                usaPercent.text = "${gramItem.usaPercent}%"
                changePercentColorAndIcon((gramItem.usaPercent).toDouble(), usaPercent, usaPercentIcon)
            }
        }
        private fun changePercentColorAndIcon(
            percent: Double, tvPercent: TextView,
            ivPercent: ImageView
        ) {
            if (percent >= 0) {
                tvPercent.setTextColor(resources.getColor(R.color.Green))
                ivPercent.load(R.drawable.up)
            } else if( percent < 0) {
                tvPercent.setTextColor(resources.getColor(R.color.darkerRed))
                ivPercent.load(R.drawable.down)
            }
        }
    }

    private inner class BitAdapter(
        private val gramItems: List<BitGramItem>
    )
        : RecyclerView.Adapter<BitHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitHolder {
            val view = layoutInflater.inflate(R.layout.bit_list_card_view, parent, false)
            return BitHolder(view)
        }

        override fun getItemCount(): Int = gramItems.size

        override fun onBindViewHolder(holder: BitHolder, position: Int) {
            val gramItem = gramItems[position]

            //Invisible loading animation
            anim_recycler_loading.visibility = View.INVISIBLE
            // Visible recyclerview
            bit_recycler_view.visibility = View.VISIBLE
            Log.e(TAG, "Adapter onBindViewHolder: ${gramItems.size}")
            holder.bind(gramItem)
//            thumbnailDownloader.queueThumbnail(holder, gramItem.symbol,
//                gramItem.changePrice,
//                gramItem.lastPrice)
        }
    }

    // update recycler view after searching
//    fun updateList(gramItems: List<GramItem>, dataSourceList: HashMap<String, DataSourceItem>) {
//        BitAdapter(gramItems, dataSourceList)
//    }

    companion object {
        fun newInstance() = BitGramFragment()
    }
}