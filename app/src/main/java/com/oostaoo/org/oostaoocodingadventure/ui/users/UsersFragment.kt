package com.oostaoo.org.oostaoocodingadventure.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.adapter.MyUsersRecyclerViewAdapter
import com.oostaoo.org.oostaoocodingadventure.database.user.User
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : Fragment() {

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersViewModel.getUsers().observe(viewLifecycleOwner, Observer { users ->
            if (users.isNotEmpty()) {
                updateAdapter(users)
            }
        })
    }

    private fun updateAdapter(users: List<User>) {

        if (rv_list_users is RecyclerView) {
            with(rv_list_users) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyUsersRecyclerViewAdapter(users)
            }
        }
    }
}