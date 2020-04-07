package com.oostaoo.org.oostaoocodingadventure.ui.configEmail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import kotlinx.android.synthetic.main.fragment_config_email.*

class ConfigEmailFragment : Fragment() {

    private lateinit var configEmailViewModel: ConfigEmailViewModel
    private var campaign: Campaign? = null
    private var listenerOnButtonBackCandidatsClickListener: OnButtonBackCandidatsClickListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory = ConfigEmailViewModelFactory(arguments!!.getInt("id"),
            arguments!!.getStringArrayList("names")!!,
            arguments!!.getStringArrayList("emails")!!,
            activity!!.application)
        configEmailViewModel = ViewModelProvider(this, factory).get(ConfigEmailViewModel::class.java)
        return inflater.inflate(R.layout.fragment_config_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        configEmailViewModel.getCampaign().observe(viewLifecycleOwner, Observer {
            campaign = it
            if (campaign != null && campaign!!.candidats != null) {
                tv_campaign_name.text = StringBuilder("Campagne: " + campaign!!.Name)
            }
        })
        tv_nb_receiver.text = java.lang.StringBuilder("Vous avez sélectionné " + configEmailViewModel.getNames().size + " destinataires")
        var names = ""
        for (name in configEmailViewModel.getNames()) {
            names += name
            if (name != configEmailViewModel.getNames().last()) names += ", "
        }
        val emailContent = StringBuilder("Bonjour $names, \n\n\n" +
                "Votre candidature a retenu notre attention.\n\nDans le cadre de notre processus " +
                "de recrutement,nous avons le plaisir de vous inviter à passer une évaluation technique.\n\n" +
                "Vous pourrez choisir le moment le plus approprié pour vous pour passer ce test.\n\n" +
                "Quand vous serez prêt(e), cliquez sur le lien ci-dessous pour accéder à la page d’accueil de votre session :\n\n" +
                "http://localhost:4200/evaluate/... \n\n\nBonne chance !\n\nCordialement")
        tv_email_content.text = emailContent
        bt_back_candidats.setOnClickListener { listenerOnButtonBackCandidatsClickListener?.onButtonBackCandidatsClickListener() }
        bt_send_email.setOnClickListener {
            var emails = ""
            for (email in configEmailViewModel.getEmails()) {
                emails += email
                if(email != configEmailViewModel.getEmails().last()) emails += ", "
            }
            val i = Intent(Intent.ACTION_SEND)
            i.data = Uri.parse("mailto:")
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(emails))
            i.putExtra(Intent.EXTRA_SUBJECT, tv_campaign_name.text)
            i.putExtra(Intent.EXTRA_TEXT, emailContent.toString())
            try {
                startActivity(Intent.createChooser(i, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        if (context is OnButtonBackCandidatsClickListener) {
            listenerOnButtonBackCandidatsClickListener = context
        } else {
            throw RuntimeException("$context must implement OnButtonBackCandidatsClickListener")
        }
    }

    override fun onDetach() {

        super.onDetach()
        listenerOnButtonBackCandidatsClickListener = null
    }

    interface OnButtonBackCandidatsClickListener {
        fun onButtonBackCandidatsClickListener()
    }
}
