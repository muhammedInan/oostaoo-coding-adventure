package com.oostaoo.org.oostaoocodingadventure.ui.facturation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oostaoo.org.oostaoocodingadventure.R

class FacturationFragment : Fragment() {

    private lateinit var facturationViewModel: FacturationViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        facturationViewModel =
                ViewModelProviders.of(this).get(FacturationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_facturation, container, false)
        val textView: TextView = root.findViewById(R.id.text_share)
        facturationViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}