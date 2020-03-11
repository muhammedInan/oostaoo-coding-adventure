package com.oostaoo.org.oostaoocodingadventure.ui.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.oostaoo.org.oostaoocodingadventure.R
import kotlinx.android.synthetic.main.fragment_user_profile.*
import java.util.*
import kotlin.collections.ArrayList

class UserProfileFragment : Fragment() {

    private lateinit var userProfileViewModel: UserProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userProfileViewModel =
                ViewModelProvider(this).get(UserProfileViewModel::class.java)
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProfileViewModel.getUser().observe(viewLifecycleOwner, Observer { user ->

            if(user != null) {
                et_first_name.setText(user.prenom, TextView.BufferType.EDITABLE)
                et_name.setText(user.nom, TextView.BufferType.EDITABLE)
                et_phone_number.setText(user.tel, TextView.BufferType.EDITABLE)
                et_mobile_number.setText(user.mobile, TextView.BufferType.EDITABLE)
                et_signature.setText(user.signature, TextView.BufferType.EDITABLE)
                et_current_email.setText(user.email, TextView.BufferType.EDITABLE)

            }
        })

        //PAYS
        val spinnerCountryAdapter: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, getCountries())
        spinnerCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_countries.adapter = spinnerCountryAdapter

        //LANGUE
        val languagesList = ArrayList<String>()
        languagesList.add("Français")
        languagesList.add("Anglais")
        val spinnerLanguageAdapter: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, languagesList)
        spinnerLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_languages.adapter = spinnerLanguageAdapter

        //FONCTION
        val functionsList = ArrayList<String>()
        functionsList.add("Commercial")
        functionsList.add("Direction")
        functionsList.add("Equipe technique")
        functionsList.add("Marketing/Communication")
        functionsList.add("Ressources humaines")
        functionsList.add("Autre")
        val spinnerFunctionAdapter: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, functionsList)
        spinnerFunctionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_function.adapter = spinnerFunctionAdapter
    }

    private fun getCountries() : ArrayList<String> {
        val locales: Array<Locale> = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country: String = locale.getDisplayCountry()
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()
        return countries
    }
}