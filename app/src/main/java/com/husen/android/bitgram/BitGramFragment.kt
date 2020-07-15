package com.husen.android.bitgram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "BitgramFragment"

class BitGramFragment : Fragment() {

    private lateinit var bitGramViewModel: BitGramViewModel
    private lateinit var bitRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bitGramViewModel =
            ViewModelProviders.of(this).get(BitGramViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bitgram, container, false)

        bitRecyclerView = view.findViewById(R.id.bit_recycler_view)
        bitRecyclerView.layoutManager = GridLayoutManager(context, 3)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bitGramViewModel.gramItemLiveData.observe(
            viewLifecycleOwner,
            Observer { gramItem ->
                bitRecyclerView.adapter = BitAdapter(gramItem)
            })
    }

    private class BitHolder(itemTextView: TextView)
        : RecyclerView.ViewHolder(itemTextView) {

        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

    private class BitAdapter(private val gramItems: List<GramItem>)
        : RecyclerView.Adapter<BitHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitHolder {
            val textView = TextView(parent.context)
            return BitHolder(textView)
        }

        override fun getItemCount(): Int = gramItems.size

        override fun onBindViewHolder(holder: BitHolder, position: Int) {
            val gramItem = gramItems[position]
            holder.bindTitle(gramItem.symbol)
        }
    }

    companion object {
        fun newInstance() = BitGramFragment()
    }
}