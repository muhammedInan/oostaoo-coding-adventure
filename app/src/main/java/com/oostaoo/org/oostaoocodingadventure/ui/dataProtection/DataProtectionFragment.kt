package com.oostaoo.org.oostaoocodingadventure.ui.dataProtection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.R

class DataProtectionFragment : Fragment() {

    private lateinit var dataProtectionViewModel: DataProtectionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataProtectionViewModel = ViewModelProvider(this).get(DataProtectionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_data_protection, container, false)
        val textView: TextView = root.findViewById(R.id.text_send)
        dataProtectionViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}