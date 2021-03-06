package com.dataoracle.agrizen.ui.Sell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dataoracle.agrizen.R

class SellFragment : Fragment() {

    private lateinit var sellViewModel: SellViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        sellViewModel =
                ViewModelProviders.of(this).get(SellViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sell, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        sellViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}