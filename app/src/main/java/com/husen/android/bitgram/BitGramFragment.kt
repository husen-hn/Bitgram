package com.husen.android.bitgram

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.husen.android.bitgram.databinding.BitListCardViewBinding
import com.husen.android.bitgram.databinding.FragmentBitgramBinding
import kotlinx.android.synthetic.main.fragment_bitgram.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

private const val TAG = "BitgramFragment"

class BitGramFragment : Fragment() {

//    private lateinit var bitRecyclerView: RecyclerView
//    private var adapter: BitAdapter? = null
    private val bitGramViewModel: BitGramViewModel by lazy {
        ViewModelProvider(this).get(BitGramViewModel::class.java)
    }
    private lateinit var binding: FragmentBitgramBinding
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
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_bitgram, container, false)

        val view = binding.root

        binding.bitRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            bitGramViewModel.bitGramItemsLiveData.observe(
                viewLifecycleOwner,
                Observer { bitGramItem ->
                    adapter = BitAdapter(bitGramItem)
                })
            adapter?.notifyDataSetChanged()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        bitGramViewModel.bitGramItemsLiveData.observe(
//            viewLifecycleOwner,
//            Observer { bitGramItem ->
//                bitRecyclerView.adapter = BitAdapter(bitGramItem)
//            })
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(
            thumbnailDownloader
        )
    }

    private inner class BitHolder(private val binding: BitListCardViewBinding)
        : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = BitGramViewModelVM()
        }

        @SuppressLint("SetTextI18n")

        fun bind(bitGramItem: BitGramItem) {

            binding.apply {
                viewModel?.bitGramItem = bitGramItem
                executePendingBindings()
            }

        }
//        private fun changePercentColorAndIcon(
//            percent: String,
//            tvPercent: TextView,
//            ivPercent: ImageView
//        ) {
//            val percentInDouble = percent.toDouble()
//            when{
//                percentInDouble >= 0.0 -> {
//                    tvPercent.setTextColor(resources.getColor(R.color.Green))
//                    ivPercent.load(R.drawable.up)
//                }
//                percentInDouble < 0.0 -> {
//                    tvPercent.setTextColor(resources.getColor(R.color.darkerRed))
//                    ivPercent.load(R.drawable.down)
//                }
//            }
//        }
    }

    private inner class BitAdapter(
        private val bitGramItems: List<BitGramItem>
    )
        : RecyclerView.Adapter<BitHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitHolder {
            val binding = DataBindingUtil.inflate<BitListCardViewBinding>(
                layoutInflater,
                R.layout.bit_list_card_view,
                parent,
                false
            )

            return BitHolder(binding)
        }

        override fun getItemCount(): Int = bitGramItems.size

        override fun onBindViewHolder(holder: BitHolder, position: Int) {
            val bitGramItem = bitGramItems[position]

            //Invisible loading animation
            anim_recycler_loading.visibility = View.INVISIBLE
            // Visible recyclerview
            bit_recycler_view.visibility = View.VISIBLE
            holder.bind(bitGramItem)
//            thumbnailDownloader.queueThumbnail(holder, gramItem.symbol,
//                gramItem.changePrice,
//                gramItem.lastPrice)
        }
    }

    // update recycler view after searching
//    fun updateList(gramItems: List<BitGramItem>, dataSourceList: HashMap<String, DataSourceItem>) {
//        BitAdapter(gramItems, dataSourceList)
//    }

    companion object {
        fun newInstance() = BitGramFragment()
    }
}