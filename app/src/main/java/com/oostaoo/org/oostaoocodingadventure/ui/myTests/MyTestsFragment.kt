package com.oostaoo.org.oostaoocodingadventure.ui.myTests

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.adapter.MyCampaignRecyclerViewAdapter
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import kotlinx.android.synthetic.main.fragment_my_tests.*

class MyTestsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var myTestsViewModel: MyTestsViewModel
    private val owner = this
    private var listener: OnCampaignListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myTestsViewModel = ViewModelProvider(this).get(MyTestsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_my_tests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_container_campaigns.setOnRefreshListener(this)
        swipe_container_campaigns.setProgressViewOffset(true, 0, 180)
        swipe_container_campaigns.isRefreshing = true

        myTestsViewModel.getCampaigns().observe(viewLifecycleOwner, Observer {campaigns ->
            if (campaigns.isNotEmpty()) {
                updateAdapter(campaigns)
            }
            swipe_container_campaigns.isRefreshing = false
        })
    }

    override fun onRefresh() {
        swipe_container_campaigns.isRefreshing = true
        myTestsViewModel.requestCampaigns()
        myTestsViewModel.getCampaigns().observe(owner, Observer { campaigns ->
            updateAdapter(campaigns)
        })
        swipe_container_campaigns.isRefreshing = false
    }

    private fun updateAdapter(campaigns: List<Campaign>) {

        if (rv_list_campaigns is RecyclerView) {
            with(rv_list_campaigns) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyCampaignRecyclerViewAdapter(campaigns, listener)
            }
        }
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)

        if (context is OnCampaignListFragmentInteractionListener) listener = context
        else throw RuntimeException("$context must implement OnListFragmentInteractionListener")
    }

    override fun onDetach() {

        super.onDetach()
        listener = null
    }

    interface OnCampaignListFragmentInteractionListener {

        fun onCampaignListFragmentInteraction(item: Campaign)
    }
}