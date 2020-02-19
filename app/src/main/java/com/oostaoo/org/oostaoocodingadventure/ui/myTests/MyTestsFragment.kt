package com.oostaoo.org.oostaoocodingadventure.ui.myTests

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.oostaoo.org.oostaoocodingadventure.HomeActivity
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.adapter.MyCampaignRecyclerViewAdapter
import com.oostaoo.org.oostaoocodingadventure.model.Campaign
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_my_tests.*
import kotlinx.android.synthetic.main.nav_header_home.view.*

class MyTestsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var myTestsViewModel: MyTestsViewModel
    private val owner = this
    private var listCampaigns: List<Campaign>? = null
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myTestsViewModel = ViewModelProviders.of(this).get(MyTestsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_my_tests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_container_campaigns.setOnRefreshListener(this)
        swipe_container_campaigns.setProgressViewOffset(true, 0, 180)
        swipe_container_campaigns.isRefreshing = true

        myTestsViewModel.getCampaigns().observe(owner, Observer {
            listCampaigns = it
            if (listCampaigns != null) {
                updateAdapter()
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }
            swipe_container_campaigns.isRefreshing = false
        })
    }

    override fun onRefresh() {
        swipe_container_campaigns.isRefreshing = true
        myTestsViewModel.requestCampaigns().observe(owner, Observer {
            listCampaigns = it
            updateAdapter()
        })
        swipe_container_campaigns.isRefreshing = false
    }

    private fun updateAdapter() {

        if (rv_list_campaigns is RecyclerView) {
            with(rv_list_campaigns) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyCampaignRecyclerViewAdapter(listCampaigns!!, listener)
            }
        }
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)

        if (context is OnListFragmentInteractionListener) listener = context
        else throw RuntimeException("$context must implement OnListFragmentInteractionListener")
    }

    override fun onDetach() {

        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {

        fun onListFragmentInteraction(item: Campaign)
    }
}