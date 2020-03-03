package com.oostaoo.org.oostaoocodingadventure.ui.newTest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.profile.Profile
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology
import kotlinx.android.synthetic.main.fragment_new_test.*


class NewTestFragment : Fragment() {

    private lateinit var newTestViewModel: NewTestViewModel
    var profiles = ArrayList<Profile>()
    private var listenerButtonListQuestions: OnButtonListQuestionsClickListener? = null
    private lateinit var sharedPreferences: SharedPreferences
    private var userId = 0
    private val listProfileTitle = ArrayList<String>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        newTestViewModel = ViewModelProvider(this).get(NewTestViewModel::class.java)

        return inflater.inflate(R.layout.fragment_new_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = context!!.getSharedPreferences("sharedpreferences", 0)
        userId = sharedPreferences.getInt("id", 0)

        newTestViewModel.getProfiles().observe(viewLifecycleOwner, Observer {profiles ->
            if (profiles != null) {
                this.profiles = profiles as ArrayList<Profile>
                listProfileTitle.clear()
                for (profile in profiles) {
                    listProfileTitle.add(profile.name!!)
                }
                if (listProfileTitle.indexOf("Personnalisé") >= 0) {
                    listProfileTitle.removeAt(listProfileTitle.indexOf("Personnalisé"))
                    listProfileTitle.add("Personnalisé")
                }
                val spinnerAdapter: ArrayAdapter<String> =
                    ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listProfileTitle)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_profiles.adapter = spinnerAdapter
            }
        })

        newTestViewModel.getTechnologies().observe(viewLifecycleOwner, Observer {technologies ->
            if (technologies != null) {
                val listItems = ArrayList<Item>()
                for (technology in technologies) {
                    val item = Item(technology.id, technology.name, false)
                    listItems.add(item)
                }
                spinner_technologies.setItems(listItems)
                spinner_technologies.setSelection(spinner_technologies.getSelectedItems()!!)
            }
        })

        spinner_profiles.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?,
                position: Int, id: Long) {
                profiles.forEach { profile ->
                    if (profile.name == spinner_profiles.getItemAtPosition(position) as String) {
                        val list = ArrayList<Item>()
                        for (technology in profile.technologies!!) {
                            val item = Item(technology.id, technology.name, false)
                            list.add(item)
                        }
                        spinner_technologies.setSelection(list)
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        bt_next_step_questions.setOnClickListener {
            val listTechnologiesName = ArrayList<String>()
            val listTechnologiesId = ArrayList<Int>()
            spinner_technologies.getSelectedItems()!!.forEach {
                listTechnologiesName.add(it.name!!)
                listTechnologiesId.add(it.id)
            }
            if (et_campaign_name.text.isNotEmpty() && rg_experience.checkedRadioButtonId > -1 &&
                    rg_language.checkedRadioButtonId > -1 && rg_copy_paste.checkedRadioButtonId > -1 &&
                    rg_send_report.checkedRadioButtonId > -1 && spinner_profiles.selectedItemPosition > -1 &&
                    listTechnologiesId.size > 0 && listTechnologiesName.size > 0) {
                listenerButtonListQuestions?.onButtonListQuestionsClick(
                    et_campaign_name.text.toString(),
                    (getView()!!.findViewById(rg_experience.checkedRadioButtonId) as RadioButton).text.toString(),
                    (getView()!!.findViewById(rg_language.checkedRadioButtonId) as RadioButton).text.toString(),
                    rg_copy_paste.getChildAt(0).isSelected,
                    rg_send_report.getChildAt(0).isSelected,
                    profiles[spinner_profiles.selectedItemPosition].id,
                    userId,
                    listTechnologiesId,
                    listTechnologiesName
                )
            } else {
                Toast.makeText(context, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonListQuestionsClickListener) {
            listenerButtonListQuestions = context
        } else {
            throw RuntimeException("$context must implement OnButtonListQuestionsListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerButtonListQuestions = null
    }

    interface OnButtonListQuestionsClickListener {
        fun onButtonListQuestionsClick(name: String, level: String, langs: String, copy_paste: Boolean,
                                       sent_report: Boolean, profile: Int, user: Int, technologiesId: ArrayList<Int>,
                                       technologiesName: ArrayList<String>)
    }
}