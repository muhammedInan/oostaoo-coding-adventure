package com.oostaoo.org.oostaoocodingadventure.ui.testCandidats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import kotlinx.android.synthetic.main.fragment_test_candidats.*

class TestCandidatsFragment: Fragment() {

    private lateinit var testCandidatsViewModel: TestCandidatsViewModel
    private val owner = this
    private var campaign: Campaign? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val factory = TestCandidatsViewModelFactory(arguments!!.getInt("id"))
        testCandidatsViewModel = ViewModelProviders.of(this, factory).get(TestCandidatsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_test_candidats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = activity!!.findViewById(R.id.toolbar)
        toolbar.title = "Candidats"

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