package com.oostaoo.org.oostaoocodingadventure.ui.businessProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oostaoo.org.oostaoocodingadventure.R

class BusinessProfileFragment : Fragment() {

    private lateinit var businessProfileViewModel: BusinessProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        businessProfileViewModel =
                ViewModelProviders.of(this).get(BusinessProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_business_profile, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)
        businessProfileViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}