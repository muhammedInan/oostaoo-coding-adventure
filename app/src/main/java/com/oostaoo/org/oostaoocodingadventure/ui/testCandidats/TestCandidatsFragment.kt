package com.oostaoo.org.oostaoocodingadventure.ui.testCandidats

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.adapter.MyCandidatRecyclerViewAdapter
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.candidat.Candidat
import kotlinx.android.synthetic.main.fragment_test_candidats.*

class TestCandidatsFragment: Fragment() {

    private var candidatsListListener: OnCandidatListFragmentInteractionListener? = null
    private var addCandidatListener: OnAddCandidatListener? = null
    private var bottomNavigationViewListener: OnBottomNavigationViewListener? = null
    private lateinit var testCandidatsViewModel: TestCandidatsViewModel
    private var campaign: Campaign? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory = TestCandidatsViewModelFactory(arguments!!.getInt("id"), activity!!.application)
        testCandidatsViewModel = ViewModelProvider(this, factory).get(TestCandidatsViewModel::class.java)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_test_candidats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        bottom_navigation_view.selectedItemId = R.id.action_candidats
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            updateMainFragment(it.itemId)
        }
        testCandidatsViewModel.getCampaign().observe(viewLifecycleOwner, Observer {
            campaign = it
            if (campaign != null && campaign!!.candidats != null) {
                updateAdapter(campaign!!.candidats!!)
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add_candidats, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_candidat) addCandidatListener?.onAddCandidatInteraction(campaign!!)
        return super.onOptionsItemSelected(item)
    }

    private fun updateAdapter(candidats: List<Candidat>) {

        if (rv_list_candidats is RecyclerView) {
            with(rv_list_candidats) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyCandidatRecyclerViewAdapter(candidats, candidatsListListener)
            }
        }
    }

    private fun updateMainFragment(integer: Int): Boolean {

        when (integer) {
            R.id.action_candidats -> bottomNavigationViewListener?.onBottomNavigationViewInteraction(0, campaign!!.id)
            R.id.action_questions -> bottomNavigationViewListener?.onBottomNavigationViewInteraction(1, campaign!!.id)
            R.id.action_parameters -> bottomNavigationViewListener?.onBottomNavigationViewInteraction(2, campaign!!.id)
        }
        return true
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        if (context is OnCandidatListFragmentInteractionListener) {
            candidatsListListener = context
        }
        else {
            throw RuntimeException("$context must implement onCandidatListFragmentInteractionListener")
        }
        if (context is OnAddCandidatListener) {
            addCandidatListener = context
        }
        else {
            throw RuntimeException("$context must implement onAddCandidatListener")
        }
        if (context is OnBottomNavigationViewListener) {
            bottomNavigationViewListener = context
        }
        else {
            throw RuntimeException("$context must implement onBottomNavigationViewListener")
        }
    }

    override fun onDetach() {

        super.onDetach()
        candidatsListListener = null
        addCandidatListener = null
        bottomNavigationViewListener = null
    }

    interface OnCandidatListFragmentInteractionListener {
        fun onCandidatListFragmentInteraction(item: Candidat)
    }

    interface OnAddCandidatListener {
        fun onAddCandidatInteraction(item: Campaign)
    }

    interface OnBottomNavigationViewListener {
        fun onBottomNavigationViewInteraction(item: Int, id: Int)
    }
}