package com.oostaoo.org.oostaoocodingadventure.ui.businessProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.entreprise.Entreprise
import kotlinx.android.synthetic.main.fragment_business_profile.*
import java.lang.StringBuilder

class BusinessProfileFragment : Fragment() {

    private lateinit var businessProfileViewModel: BusinessProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        businessProfileViewModel = ViewModelProvider(this).get(BusinessProfileViewModel::class.java)
        return inflater.inflate(R.layout.fragment_business_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val spinnerLanguagesAdapter: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listOf(
                "Albanais", "Allemand", "Arménien", "Biélorusse", "Bosniaque", "Bulgare", "Chinois",
                "Coréen", "Croate", "Danois", "Espagnol", "Estonien", "Finnois", "Français",
                "Georgien", "Grec", "Hongrois", "Indonésien", "Islandais", "Italien", "Japonais",
                "Lituanien", "Néerlandais", "Norvégien", "Polonais", "Portugais", "Roumain",
                "Russe", "Serbe", "Suédois", "Tchèque", "Turc", "Ukrainien", "Vietnamien"))
        spinnerLanguagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_business_language.adapter = spinnerLanguagesAdapter
        val spinnerIndustryAdapater: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listOf(
                "Communications", "Game publisher", "Industry", "Internet", "IT Services",
                "Recruiting Agency", "Security", "Software", "Startup", "Other"))
        spinnerIndustryAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_industry.adapter = spinnerIndustryAdapater
        val spinnerNbEmployeeAdapater: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listOf(
                "1-9", "10-49", "50-199", "+200"))
        spinnerNbEmployeeAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_nb_employee.adapter = spinnerNbEmployeeAdapater
        val spinnerNbDevelopersAdapater: ArrayAdapter<String> =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listOf(
                "1-9", "10-49", "50-199", "+200"))
        spinnerNbDevelopersAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_nb_developers.adapter = spinnerNbDevelopersAdapater
        businessProfileViewModel.getEntreprise().observe(viewLifecycleOwner, Observer { entreprise ->

            if(entreprise != null) {
                setPercentProgressBar(entreprise)
                spinner_business_language.setSelection(spinnerLanguagesAdapter.getPosition(entreprise.lang))
                et_business_name.setText(entreprise.nom)
                et_business_email.setText(entreprise.email)
                et_business_phone.setText(entreprise.tel)
                spinner_industry.setSelection(spinnerIndustryAdapater.getPosition(entreprise.industrie))
                spinner_nb_employee.setSelection(spinnerNbEmployeeAdapater.getPosition(entreprise.nb_employe))
                spinner_nb_developers.setSelection(spinnerNbDevelopersAdapater.getPosition(entreprise.nb_dev))
                et_link_video.setText(entreprise.lien_video)
                et_link_site.setText(entreprise.url_site)
                et_teaser.setText(entreprise.teaser)
                et_linkedin.setText(entreprise.linkedin)
                et_facebook.setText(entreprise.facebook)
                et_twitter.setText(entreprise.twitter)
            }
        })
    }

    private fun setPercentProgressBar(entreprise: Entreprise) {

        val totalValues = listOf(
            entreprise.lang, entreprise.lien_video, entreprise.url_site, entreprise.teaser,
            entreprise.nom, entreprise.email, entreprise.tel, entreprise.industrie,
            entreprise.nb_employe, entreprise.nb_dev, entreprise.linkedin, entreprise.facebook,
            entreprise.twitter)
        var nbElementsFilled = 0
        for (element in totalValues) {
            if (element != null && element != "") nbElementsFilled += 1
        }
        val percent = nbElementsFilled * 100 / totalValues.size
        progress_circular.progress = percent
        tv_percent.text = StringBuilder("$percent%")
    }
}