package com.oostaoo.org.oostaoocodingadventure.ui.businessProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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