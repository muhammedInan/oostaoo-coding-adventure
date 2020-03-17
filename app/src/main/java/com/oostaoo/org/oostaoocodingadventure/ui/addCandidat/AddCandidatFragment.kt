package com.oostaoo.org.oostaoocodingadventure.ui.addCandidat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.oostaoo.org.oostaoocodingadventure.R

class AddCandidatFragment : Fragment() {

    companion object {
        fun newInstance() = AddCandidatFragment()
    }

    private lateinit var viewModel: AddCandidatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_candidat_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddCandidatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
