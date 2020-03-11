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

        //PAYS
        val spinnerCountryAdapter: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, getCountries())
        spinnerCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_countries.adapter = spinnerCountryAdapter

        //LANGUE
        val spinnerLanguageAdapter: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listOf("Français", "Anglais"))
        spinnerLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_languages.adapter = spinnerLanguageAdapter

        //FONCTION
        val spinnerFunctionAdapter: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listOf("Commercial", "Direction",
                "Equipe technique", "Marketing/Communication", "Ressources humaines", "Autre"))
        spinnerFunctionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_function.adapter = spinnerFunctionAdapter

        userProfileViewModel.getUser().observe(viewLifecycleOwner, Observer { user ->

            if(user != null) {
                et_first_name.setText(user.prenom)
                et_name.setText(user.nom)
                spinner_countries.setSelection(spinnerCountryAdapter.getPosition(user.pays))
                spinner_languages.setSelection(spinnerLanguageAdapter.getPosition(user.langue))
                et_phone_number.setText(user.tel)
                et_mobile_number.setText(user.mobile)
                spinner_function.setSelection(spinnerFunctionAdapter.getPosition(user.function))
                et_signature.setText(user.signature)
                et_current_email.setText(user.email)
            }
        })
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