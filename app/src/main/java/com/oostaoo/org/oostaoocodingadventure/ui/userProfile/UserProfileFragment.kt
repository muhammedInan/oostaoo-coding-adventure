package com.oostaoo.org.oostaoocodingadventure.ui.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oostaoo.org.oostaoocodingadventure.R

class UserProfileFragment : Fragment() {

    private lateinit var userProfileViewModel: UserProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        userProfileViewModel =
                ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }
}