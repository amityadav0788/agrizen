package com.dataoracle.agrizen.ui.Profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dataoracle.agrizen.R

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val userId: TextView = root.findViewById(R.id.id_profile_id)
        val sharedPref = this?.activity?.getPreferences(Context.MODE_PRIVATE)

        if (sharedPref != null) {
            userId.text = sharedPref.getString(getString(R.string.user_id), "")
        }

        return root
    }
}