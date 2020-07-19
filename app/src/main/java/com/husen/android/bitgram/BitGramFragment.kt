package com.husen.android.bitgram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "BitgramFragment"

class BitGramFragment : Fragment() {

    private lateinit var bitRecyclerView: RecyclerView
    private var adapter: BitAdapter? = null

    private val bitGramViewModel: BitGramViewModel by lazy {
        ViewModelProviders.of(this).get(BitGramViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bitGramViewModel.listDataSource()
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

    private inner class BitHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        private lateinit var item: GramItem

        // Coin Icon & Name & Symbol
        private val bitIcon: ImageView = itemView.findViewById(R.id.iv_bit_list)
        private val bitName: TextView = itemView.findViewById(R.id.tv_bit_name_list)
        private val bitSymbol: TextView = itemView.findViewById(R.id.tv_bit_symbol_list)

        // Coin Price & Price Percent & Icon in $
        private val usaPrice: TextView = itemView.findViewById(R.id.tv_usa_price)
        private val usaPercent: TextView = itemView.findViewById(R.id.tv_usa_percent)
        private val usaPercentIcon: ImageView = itemView.findViewById(R.id.iv_usa_arrow)

        // Coin Price & Price Percent & Icon in Toman
        private val irPrice: TextView = itemView.findViewById(R.id.tv_ir_price)
        private val irPercent: TextView = itemView.findViewById(R.id.tv_ir_percent)
        private val irPercentIcon: ImageView = itemView.findViewById(R.id.iv_ir_arrow)

        fun bind(item: GramItem, dataSource: DataSourceItem?) {
            this.item = item

            bitName.text = this.item.symbol
            usaPrice.text = this.item.lastPrice

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

        override fun getItemCount(): Int = dataSourceList.size

        override fun onBindViewHolder(holder: BitHolder, position: Int) {
            val gramItem = gramItems[position]
            val dataSource = getSymbolFromDataSource(dataSourceList, gramItem.symbol)
            holder.bind(gramItem, dataSource)
        }
    }
    fun getSymbolFromDataSource(dataSource: ArrayList<DataSourceItem?>, bitSymbolId: String): DataSourceItem? {
        var item: DataSourceItem? = null
        for (index in dataSource.indices) {
            if (dataSource[index]?.bitIdSymbol == bitSymbolId) {
                item = dataSource[index]
            }
        }
        return item
    }

    companion object {
        fun newInstance() = BitGramFragment()
    }
}