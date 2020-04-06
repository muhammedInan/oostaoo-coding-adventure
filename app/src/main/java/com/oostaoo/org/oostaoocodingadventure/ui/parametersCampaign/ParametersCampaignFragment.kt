package com.oostaoo.org.oostaoocodingadventure.ui.parametersCampaign

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.ui.testCandidats.TestCandidatsFragment
import kotlinx.android.synthetic.main.fragment_parameters_campaign.*

class ParametersCampaignFragment : Fragment() {

    private lateinit var parametersCampaignViewModel: ParametersCampaignViewModel
    private var bottomNavigationViewListener: TestCandidatsFragment.OnBottomNavigationViewListener? = null
    private var mCampaign: Campaign? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val factory =
            ParametersCampaignViewModelFactory(arguments!!.getInt("id"), activity!!.application)
        parametersCampaignViewModel = ViewModelProvider(this, factory).get(ParametersCampaignViewModel::class.java)
        return inflater.inflate(R.layout.fragment_parameters_campaign, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bottom_navigation_view.selectedItemId = R.id.action_parameters
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            updateMainFragment(it.itemId)
        }

        parametersCampaignViewModel.getCampaign().observe(viewLifecycleOwner, Observer { campaign ->
            mCampaign = campaign
            if (campaign != null) {
                et_campaign_name.setText(campaign.Name)
                when (campaign.langs) {
                    "FR" -> rg_language.check(R.id.rb_fr)
                    "EN" -> rg_language.check(R.id.rb_en)
                    "ES" -> rg_language.check(R.id.rb_es)
                    "JP" -> rg_language.check(R.id.rb_jp)
                }
                when (campaign.copy_paste) {
                    true -> rg_copy_paste.check(R.id.rb_yes_copy_paste)
                    false -> rg_copy_paste.check(R.id.rb_no_copy_paste)
                }
                when (campaign.sent_report) {
                    true -> rg_send_report.check(R.id.rb_yes_send_report)
                    false -> rg_send_report.check(R.id.rb_no_send_report)
                }
            }
        })
    }

    private fun updateMainFragment(integer: Int): Boolean {
        when (integer) {
            R.id.action_candidats -> bottomNavigationViewListener?.onBottomNavigationViewInteraction(0, mCampaign!!.id)
            R.id.action_questions -> bottomNavigationViewListener?.onBottomNavigationViewInteraction(1, mCampaign!!.id)
            R.id.action_parameters -> bottomNavigationViewListener?.onBottomNavigationViewInteraction(2, mCampaign!!.id)
        }
        return true
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)

        if (context is TestCandidatsFragment.OnBottomNavigationViewListener) bottomNavigationViewListener = context
        else throw RuntimeException("$context must implement onBottomNavigationViewListener")
    }

    override fun onDetach() {

        super.onDetach()
        bottomNavigationViewListener = null
    }
}
