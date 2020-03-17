package com.oostaoo.org.oostaoocodingadventure.ui.testInfoCandidat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.R

class TestInfoCandidatFragment: Fragment() {

    private lateinit var testInfoCandidatViewModel: TestInfoCandidatViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_info_candidat, container, false)
    }
}