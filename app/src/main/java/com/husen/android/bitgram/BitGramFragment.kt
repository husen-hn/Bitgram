package com.husen.android.bitgram

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.husen.android.bitgram.databinding.BitListCardViewBinding
import com.husen.android.bitgram.databinding.FragmentBitgramBinding
import kotlinx.android.synthetic.main.fragment_bitgram.*


private const val TAG = "BitgramFragment"

class BitGramFragment : Fragment(), View.OnClickListener {

    private val bitGramViewModel: BitGramViewModel by lazy {
        ViewModelProvider(this).get(BitGramViewModel::class.java)
    }
    private lateinit var bitGramItemList: List<BitGramItem>
    private lateinit var bitgramAdapter: BitGramFragment.BitAdapter
    lateinit var navController: NavController
    private lateinit var binding: FragmentBitgramBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

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
                    // used in searching
                    bitGramItemList = bitGramItem
                    //used in filter list to update list
                    bitgramAdapter = BitAdapter(bitGramItem)
                    // display all items list
                    adapter = bitgramAdapter
                })
            adapter?.notifyDataSetChanged()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        iv_home.setOnClickListener(this)
        iv_settings.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.iv_settings -> {
                navController.navigate(R.id.action_nav_main_fragment_to_nav_settings_fragment)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        //display search view with animation
        val slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)

        btn_fab.setOnClickListener {
            cv_search.startAnimation(slideDown)
            cv_search.visibility = View.VISIBLE
            //open keyboard
            et_search.requestFocus()
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(et_search, InputMethodManager.SHOW_IMPLICIT)
        }
        cv_sv_back.setOnClickListener {
            cv_search.startAnimation(slideUp)
            cv_search.visibility = View.GONE
            //hide keyboard
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(et_search.windowToken, InputMethodManager.SHOW_IMPLICIT)
            //delete searched text
            et_search.setText("")
            //display all coins normally when u back
            bitgramAdapter.updateList(emptyList())
        }

        // searched item text
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                filterList(p0.toString().toLowerCase().trim())
            }
        })
    }

    // filter list with searched item
    private fun filterList(searchedText: String) {

        val list = bitGramItemList.filter {
            it.bitSymbol.toLowerCase().contains(searchedText) ||
            it.bitName.toLowerCase().contains(searchedText) ||
            it.bitFaName.contains(searchedText)
        }

        bitgramAdapter.updateList(list)
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
    }

    private inner class BitAdapter(
        private var bitGramItems: List<BitGramItem>
    )
        : RecyclerView.Adapter<BitHolder>() {
        var itemsList = bitGramItems
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitHolder {
            val binding = DataBindingUtil.inflate<BitListCardViewBinding>(
                layoutInflater,
                R.layout.bit_list_card_view,
                parent,
                false
            )

            return BitHolder(binding)
        }

        override fun getItemCount(): Int = itemsList.size

        override fun onBindViewHolder(holder: BitHolder, position: Int) {
            val bitGramItem = itemsList[position]

            //Invisible loading animation
            anim_recycler_loading.visibility = View.INVISIBLE
            // Visible recyclerview and search et
            bit_recycler_view.visibility = View.VISIBLE
            holder.bind(bitGramItem)
        }

        fun updateList(list: List<BitGramItem>) {

            // if searched item empty or not in list display all items
            itemsList = if (list.isEmpty()){
                bitGramItems
            }else {
                list
            }
            notifyDataSetChanged()
        }

    }
}