package com.oostaoo.org.oostaoocodingadventure.ui.newTest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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

        /*val spinnerAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list)*/
        return root
    }
}