package com.oostaoo.org.oostaoocodingadventure.ui.addCandidat

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import kotlinx.android.synthetic.main.fragment_add_candidat.*

class AddCandidatFragment : Fragment() {

    private lateinit var addCandidatViewModel: AddCandidatViewModel
    private var listenerOnButtonConfigEmailClickListener: OnButtonConfigEmailClickListener? = null
    private var campaign: Campaign? = null
    private val listName = ArrayList<String>()
    private val listEmail = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val factory = AddCandidatViewModelFactory(arguments!!.getInt("id"), activity!!.application)
        addCandidatViewModel =
            ViewModelProvider(this, factory).get(AddCandidatViewModel::class.java)
        return inflater.inflate(R.layout.fragment_add_candidat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addCandidatViewModel.getCampaign().observe(viewLifecycleOwner, Observer {
            campaign = it
            if (campaign != null && campaign!!.candidats != null) {
                tv_campaign_name.text = StringBuilder("Campagne: " + campaign!!.Name)
            }
        })

        addCandidat()

        bt_add_candidat.setOnClickListener {
            addCandidat()
        }

        bt_config_email.setOnClickListener {
            val group = ll_add_candidat as ViewGroup
            for (i in 0 until group.childCount) {
                val layout = group.getChildAt(i) as ViewGroup
                for (j in 0 until layout.childCount) {
                    val view = layout.getChildAt(j)
                    if (view is EditText) {
                        if (view.text.isNotEmpty())
                            if (view.inputType == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                                listEmail.add(view.text.toString())
                            else listName.add(view.text.toString())
                    }
                }
            }
            listenerOnButtonConfigEmailClickListener?.onButtonConfigEmailClickListener(campaign!!, listName, listEmail)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonConfigEmailClickListener) {
            listenerOnButtonConfigEmailClickListener = context
        } else {
            throw RuntimeException("$context must implement OnPostCampaignClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerOnButtonConfigEmailClickListener = null
    }

    private fun addCandidat() {
        val layout = ConstraintLayout(context)
        layout.id = generateViewId()
        layout.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        val typeface = ResourcesCompat.getFont(context!!, R.font.poppins_regular)
        val constraintSet = ConstraintSet()

        //GUIDELINE
        val gl = Guideline(context)
        val gllp = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        gllp.guidePercent = 0.5f
        gllp.orientation = LinearLayout.VERTICAL
        gl.layoutParams = gllp
        gl.id = generateViewId()
        layout.addView(gl)

        //TV_NAME
        val tvName = TextView(context)
        tvName.id = generateViewId()
        val paramsTvName = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        tvName.layoutParams = paramsTvName
        tvName.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        tvName.textSize = 17f
        tvName.typeface = typeface
        tvName.text = getString(R.string.name)
        layout.addView(tvName)
        constraintSet.clone(layout)
        constraintSet.connect(
            tvName.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            tvName.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            tvName.id,
            ConstraintSet.END,
            gl.id,
            ConstraintSet.END
        )
        constraintSet.applyTo(layout)

        //ET_NAME
        val etName = EditText(context)
        etName.id = generateViewId()
        val paramsEtName = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        etName.layoutParams = paramsEtName
        etName.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(etName)
        constraintSet.clone(layout)
        constraintSet.connect(
            etName.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            5
        )
        constraintSet.connect(
            etName.id,
            ConstraintSet.TOP,
            tvName.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            etName.id,
            ConstraintSet.END,
            gl.id,
            ConstraintSet.END,
            5
        )
        constraintSet.applyTo(layout)

        //TV_EMAIL
        val tvEmail = TextView(context)
        tvEmail.id = generateViewId()
        val paramsTvEmail = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        tvEmail.layoutParams = paramsTvEmail
        tvEmail.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        tvEmail.textSize = 17f
        tvEmail.typeface = typeface
        tvEmail.text = getString(R.string.email_star)
        layout.addView(tvEmail)
        constraintSet.clone(layout)
        constraintSet.connect(
            tvEmail.id,
            ConstraintSet.START,
            gl.id,
            ConstraintSet.START
        )
        constraintSet.connect(
            tvEmail.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            tvEmail.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.applyTo(layout)

        //ET_EMAIL
        val etEmail = EditText(context)
        etEmail.id = generateViewId()
        val paramsEtEmail = ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        etEmail.layoutParams = paramsEtEmail
        etEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        layout.addView(etEmail)
        constraintSet.clone(layout)
        constraintSet.connect(
            etEmail.id,
            ConstraintSet.START,
            gl.id,
            ConstraintSet.START,
            5
        )
        constraintSet.connect(
            etEmail.id,
            ConstraintSet.TOP,
            tvEmail.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            etEmail.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            5
        )
        constraintSet.applyTo(layout)

        ll_add_candidat.addView(layout)
    }

    interface OnButtonConfigEmailClickListener {
        fun onButtonConfigEmailClickListener(campaign: Campaign, names: ArrayList<String>, emails: ArrayList<String>)
    }

}
