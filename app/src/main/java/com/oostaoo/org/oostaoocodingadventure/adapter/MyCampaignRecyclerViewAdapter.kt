package com.oostaoo.org.oostaoocodingadventure.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.ui.myTests.MyTestsFragment
import kotlinx.android.synthetic.main.fragment_campaign_card.view.*

class MyCampaignRecyclerViewAdapter(private val mValues: List<Campaign>,
                                    private val mListener: MyTestsFragment.OnCampaignListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyCampaignRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Campaign
            mListener?.onCampaignListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_campaign_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var txtInvite = "invité"
        var txtTermine = "terminé"

        if (mValues[position].candidats!!.size > 1 || mValues[position].candidats!!.isEmpty()) txtInvite += "s"
        if (mValues[position].NbCandidatFinish == null || mValues[position].NbCandidatFinish!! > 1) txtTermine += "s"

        holder.mName.text = mValues[position].Name
        holder.mNbInvite.text = StringBuilder(mValues[position].candidats!!.size.toString() + " $txtInvite")
        if(mValues[position].NbCandidatFinish != null) {
            holder.mNbTermine.text =
                StringBuilder(mValues[position].NbCandidatFinish.toString() + " $txtTermine")
        } else {
            holder.mNbTermine.text =
                StringBuilder("0 $txtTermine")
        }
        with(holder.mView) {
            tag = mValues[position]
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.count()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mName: TextView = mView.tv_name
        val mNbInvite: TextView = mView.tv_nb_invite
        val mNbTermine: TextView = mView.tv_nb_termine
    }
}