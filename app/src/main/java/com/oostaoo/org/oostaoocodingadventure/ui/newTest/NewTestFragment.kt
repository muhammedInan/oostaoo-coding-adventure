package com.oostaoo.org.oostaoocodingadventure.ui.newTest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.oostaoo.org.oostaoocodingadventure.R

class NewTestFragment : Fragment() {

    private lateinit var newTestViewModel: NewTestViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        newTestViewModel =
                ViewModelProviders.of(this).get(NewTestViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new_test, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        newTestViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}