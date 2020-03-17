package com.oostaoo.org.oostaoocodingadventure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.candidat.Candidat
import com.oostaoo.org.oostaoocodingadventure.ui.testCandidats.TestCandidatsFragment
import kotlinx.android.synthetic.main.fragment_candidats_card.view.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*


class MyCandidatRecyclerViewAdapter(private val mValues: List<Candidat>,
                                    private val mContext: Context,
                                    private val mListener: TestCandidatsFragment.OnCandidatListFragmentInteractionListener?
)
    : RecyclerView.Adapter<MyCandidatRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Candidat
            mListener?.onCandidatListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_candidats_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.mNom.text = StringBuilder("Nom : " + mValues[position].Nom)
        holder.mEmail.text = StringBuilder("Email : " + mValues[position].email)
        val timestamp = Instant.parse(mValues[position].test_terminer)
        val sdf = SimpleDateFormat("dd/MM/yyyy à HH:mm:ss")
        val netDate = Date(timestamp.epochSecond * 1000)
        val myDate: String = sdf.format(netDate)
        holder.mTestTerminer.text = StringBuilder("Dernière activité : $myDate")
        if (mValues[position].points_candidat == null || mValues[position].points_candidat!![5].PourcentTest == null) {
            holder.mPoint.text = "Score :  0 %"
        } else {
            holder.mPoint.text =
                StringBuilder("Score : " + mValues[position].points_candidat!![5].PourcentTest.toString() + "%")
        }

        if (mValues[position].duree != null) {
            holder.mDuree.text = StringBuilder("Durée : " + mValues[position].duree.toString() + " secondes")
        } else {
            holder.mDuree.text = "Durée : 0 secondes"
        }

        with(holder.mView) {
            tag = mValues[position]
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.count()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNom: TextView = mView.tv_nom
        val mEmail: TextView = mView.tv_email
        val mPoint: TextView = mView.tv_point
        val mDuree: TextView = mView.tv_duree
        val mTestTerminer: TextView = mView.tv_test_terminer
    }
}