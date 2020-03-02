package com.oostaoo.org.oostaoocodingadventure.ui.testCandidats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import kotlinx.android.synthetic.main.fragment_test_candidats.*

class TestCandidatsFragment: Fragment() {

    private lateinit var testCandidatsViewModel: TestCandidatsViewModel
    private var campaign: Campaign? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val factory = TestCandidatsViewModelFactory(arguments!!.getInt("id"), activity!!.application)
        testCandidatsViewModel = ViewModelProvider(this, factory).get(TestCandidatsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_test_candidats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testCandidatsViewModel.getCampaign().observe(viewLifecycleOwner, Observer {
            campaign = it
            if (campaign != null) {
                var listCandidats = ""
                for (candidat in campaign!!.candidats!!) {
                    listCandidats += candidat.Nom + "\n"
                }
                tv_list_candidats.text = listCandidats
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }
}