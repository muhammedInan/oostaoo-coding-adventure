package com.oostaoo.org.oostaoocodingadventure.ui.facturation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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