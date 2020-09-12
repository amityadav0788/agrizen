package com.dataoracle.agrizen.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dataoracle.agrizen.ItemDetailsActivity
import com.dataoracle.agrizen.R
import com.dataoracle.agrizen.VerifyPhoneActivity
import com.dataoracle.agrizen.helper.constants
import com.dataoracle.agrizen.ui.adapter.HomeRecyclerViewAdapter
import com.dataoracle.agrizen.ui.datamodel.HomeListItem

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var layoutManager: RecyclerView.LayoutManager?= null
    private var adapter: RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>?= null
    private var homeItemList: ArrayList<HomeListItem> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val locationText: TextView = root.findViewById(R.id.id_location_text)
        val id_homeRecyclerView: RecyclerView = root.findViewById(R.id.id_homeRecyclerView)
        val sharedPref = this?.activity?.getPreferences(Context.MODE_PRIVATE)
        initHomeItemList()
        if (sharedPref != null) {
            locationText.text = sharedPref.getString(getString(R.string.user_location), "")
        }
        layoutManager = GridLayoutManager(this?.activity, 2)
        id_homeRecyclerView.layoutManager = layoutManager

        adapter = this?.activity?.let { HomeRecyclerViewAdapter(homeItemList, it, onClickListener = {view, HomeListItem->openItemDetails()}) }
        id_homeRecyclerView.adapter = adapter
        return root
    }

    fun initHomeItemList() {
        //TODO hit the backend service and get the list data
        for(i in 1..10) {
            homeItemList.add(HomeListItem("test$i",constants.CLOUDINARY_SAMPLE,"sample$i"))
        }
    }

    fun openItemDetails() {
        val intent = Intent(this?.activity, ItemDetailsActivity::class.java).apply {
            putExtra("itemId", "")
        }
        startActivityForResult(intent, constants.LAUNCH_DETAILS_CODE)
    }
}