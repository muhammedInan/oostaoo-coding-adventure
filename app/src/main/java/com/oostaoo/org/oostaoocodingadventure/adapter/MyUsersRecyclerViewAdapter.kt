package com.oostaoo.org.oostaoocodingadventure.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.user.User
import kotlinx.android.synthetic.main.fragment_users_card.view.*

class MyUsersRecyclerViewAdapter(private val mValues: List<User>) : RecyclerView.Adapter<MyUsersRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_users_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.mNom.text = mValues[position].nom
        holder.mEmail.text = mValues[position].email
        holder.mPrivilege.text = mValues[position].role!!.name
    }

    override fun getItemCount(): Int = mValues.count()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mNom: TextView = mView.tv_nom
        val mEmail: TextView = mView.tv_email
        val mPrivilege: TextView = mView.tv_privilege
        val mButtonModify: Button = mView.bt_modify
        val mButtonDelete: Button = mView.bt_delete
    }

}