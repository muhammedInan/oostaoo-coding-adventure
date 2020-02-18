package com.oostaoo.org.oostaoocodingadventure.ui.dataProtection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.oostaoo.org.oostaoocodingadventure.R

class DataProtectionFragment : Fragment() {

    private lateinit var dataProtectionViewModel: DataProtectionViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dataProtectionViewModel =
                ViewModelProviders.of(this).get(DataProtectionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_data_protection, container, false)
        val textView: TextView = root.findViewById(R.id.text_send)
        dataProtectionViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}