package com.oostaoo.org.oostaoocodingadventure.ui.listQuestions

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.View.*
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.campaign.SendCampaign
import com.oostaoo.org.oostaoocodingadventure.database.question.Question
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology
import kotlinx.android.synthetic.main.fragment_list_questions.*
import java.util.*
import kotlin.collections.ArrayList

class ListQuestionsFragment: Fragment() {

    enum class Level {
        FACILE {
            override fun getLevel(): Int {
                return 1
            }
        },
        MOYEN {
            override fun getLevel(): Int {
                return 2
            }
        },
        EXPERT {
            override fun getLevel(): Int {
                return 3
            }
        };

        abstract fun getLevel(): Int
    }

    private lateinit var listQuestionsViewModel: ListQuestionsViewModel
    private var listenerOnPostCampaignClickListener: OnPostCampaignClickListener? = null

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val factory = ListQuestionsViewModelFactory(
            arguments!!.getString("Name")!!,
            arguments!!.getString("level")!!,
            arguments!!.getString("langs")!!,
            arguments!!.getBoolean("copy_paste"),
            arguments!!.getBoolean("sent_report"),
            arguments!!.getInt("profile"),
            arguments!!.getInt("user"),
            arguments!!.getIntegerArrayList("technologiesId")!!,
            arguments!!.getStringArrayList("technologiesName")!!, activity!!.application)
        listQuestionsViewModel = ViewModelProvider(this, factory).get(ListQuestionsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_list_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        top.setOnDragListener(MyDragListener())
        bottom.setOnDragListener(MyDragListener())

        listQuestionsViewModel.getTechnologies().observe(viewLifecycleOwner, Observer { technologies ->
            val listQuestions = ArrayList<Question>()
            val selectedTechnologies = ArrayList<Technology>()
            val technologiesName = ArrayList<String>()
            val questionsSizeByTechnology = ArrayList<Int>()

            for (technology in technologies) {
                technologiesName.add(technology.name!!)
            }
            for (technologyName in listQuestionsViewModel.getSelectedTechnologies()) {
                val index = technologiesName.indexOf(technologyName)
                val technology = technologies[index]
                selectedTechnologies.add(technology)
            }

            for (technology in selectedTechnologies) {
                if (questionsSizeByTechnology.isEmpty()) {
                    questionsSizeByTechnology.add(technology.questions!!.size)
                } else {
                    questionsSizeByTechnology.add(questionsSizeByTechnology.last() + technology.questions!!.size)
                }
                for (question in technology.questions) {
                    listQuestions.add(question)
                }
            }
            for (question in listQuestions) {
                val layout = ConstraintLayout(context)
                layout.id = question.id

                layout.layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    400
                )
                layout.setBackgroundResource(R.drawable.background_item_drag_and_drop)
                layout.setOnLongClickListener {
                    val data = ClipData.newPlainText("", "")
                    val shadowBuilder = DragShadowBuilder(it)
                    it.startDrag(data, shadowBuilder, it, 0)
                    it.visibility = INVISIBLE
                    true
                }

                val typeface = ResourcesCompat.getFont(context!!, R.font.poppins_regular)
                val constraintSet = ConstraintSet()

                //TECHNOLOGY
                val tvTechnology = TextView(context)
                tvTechnology.id = generateViewId()
                val paramsTechnology = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                tvTechnology.layoutParams = paramsTechnology

                tvTechnology.setTextColor(ContextCompat.getColor(context!!, R.color.orange_next_step))
                tvTechnology.typeface = typeface
                /*var questionTechnology = ""
                for (i in 0 until questionsSizeByTechnology.size) {
                    if (listQuestions.indexOf(question) < questionsSizeByTechnology[i]) {
                        questionTechnology = selectedTechnologies[i].name!!
                        break
                    }
                }*/
                tvTechnology.text = question.technologies!!.name //questionTechnology
                layout.addView(tvTechnology)
                constraintSet.clone(layout)
                constraintSet.connect(
                    tvTechnology.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    10
                )
                constraintSet.connect(
                    tvTechnology.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP,
                    10
                )
                constraintSet.applyTo(layout)

                //POINTS
                val tvPoints = TextView(context)
                tvPoints.id = generateViewId()
                val paramsPoints = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                tvPoints.layoutParams = paramsPoints
                tvPoints.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                tvPoints.typeface = typeface
                tvPoints.text = StringBuilder(question.points.toString() + " points")
                layout.addView(tvPoints)
                constraintSet.clone(layout)
                constraintSet.connect(
                    tvPoints.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START
                )
                constraintSet.connect(
                    tvPoints.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    tvPoints.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP,
                    10
                )
                constraintSet.applyTo(layout)

                //BUTTON
                val buttonChoose = Button(context)
                buttonChoose.id = question.id
                val params = ConstraintLayout.LayoutParams(235, 90)
                buttonChoose.layoutParams = params
                buttonChoose.setBackgroundResource(R.color.colorPrimary)
                buttonChoose.gravity = Gravity.CENTER
                buttonChoose.text = getString(R.string.choose)
                buttonChoose.setPadding(2,2,2,2)
                buttonChoose.isAllCaps = false
                buttonChoose.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                buttonChoose.typeface = ResourcesCompat.getFont(context!!, R.font.poppins_regular)
                buttonChoose.transformationMethod = null
                buttonChoose.setOnClickListener(MyButtonClickListener())
                layout.addView(buttonChoose)
                constraintSet.clone(layout)
                constraintSet.connect(
                    buttonChoose.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    20
                )
                constraintSet.connect(
                    buttonChoose.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP,
                    20
                )
                constraintSet.applyTo(layout)

                //QUESTION
                val tvQuestion = TextView(context)
                tvQuestion.id = generateViewId()
                val paramsQuestion = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                tvQuestion.layoutParams = paramsQuestion
                tvQuestion.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                tvQuestion.typeface = typeface
                tvQuestion.maxLines = 3
                tvQuestion.ellipsize = TextUtils.TruncateAt.END
                tvQuestion.text = question.name
                layout.addView(tvQuestion)
                constraintSet.clone(layout)
                constraintSet.connect(
                    tvQuestion.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    10
                )
                constraintSet.connect(
                    tvQuestion.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    265
                )
                constraintSet.connect(
                    tvQuestion.id,
                    ConstraintSet.TOP,
                    tvTechnology.id,
                    ConstraintSet.BOTTOM,
                    10
                )
                constraintSet.applyTo(layout)

                //LEVEL
                val level = Level.valueOf(question.level!!.toUpperCase(Locale.getDefault())).getLevel()

                val ivBtRadioLevel1 = ImageView(context)
                ivBtRadioLevel1.id = generateViewId()
                val paramsLevel1 = ConstraintLayout.LayoutParams(42, 42)
                ivBtRadioLevel1.layoutParams = paramsLevel1
                if (level >= 1) {
                    ivBtRadioLevel1.setImageResource(R.drawable.ic_bt_radio_enable)
                } else {
                    ivBtRadioLevel1.setImageResource(R.drawable.ic_bt_radio_disable)
                }
                layout.addView(ivBtRadioLevel1)
                constraintSet.clone(layout)
                constraintSet.connect(
                    ivBtRadioLevel1.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    10
                )
                constraintSet.connect(
                    ivBtRadioLevel1.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    20
                )
                constraintSet.applyTo(layout)

                val ivBtRadioLevel2 = ImageView(context)
                ivBtRadioLevel2.id = generateViewId()
                val paramsLevel2 = ConstraintLayout.LayoutParams(42, 42)
                ivBtRadioLevel2.layoutParams = paramsLevel2
                if (level >= 2) {
                    ivBtRadioLevel2.setImageResource(R.drawable.ic_bt_radio_enable)
                } else {
                    ivBtRadioLevel2.setImageResource(R.drawable.ic_bt_radio_disable)
                }
                layout.addView(ivBtRadioLevel2)
                constraintSet.clone(layout)
                constraintSet.connect(
                    ivBtRadioLevel2.id,
                    ConstraintSet.START,
                    ivBtRadioLevel1.id,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    ivBtRadioLevel2.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    20
                )
                constraintSet.applyTo(layout)

                val ivBtRadioLevel3 = ImageView(context)
                ivBtRadioLevel3.id = generateViewId()
                val paramsLevel3 = ConstraintLayout.LayoutParams(42, 42)
                ivBtRadioLevel3.layoutParams = paramsLevel3
                if (level >= 3) {
                    ivBtRadioLevel3.setImageResource(R.drawable.ic_bt_radio_enable)
                } else {
                    ivBtRadioLevel3.setImageResource(R.drawable.ic_bt_radio_disable)
                }
                layout.addView(ivBtRadioLevel3)
                constraintSet.clone(layout)
                constraintSet.connect(
                    ivBtRadioLevel3.id,
                    ConstraintSet.START,
                    ivBtRadioLevel2.id,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    ivBtRadioLevel3.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    20
                )
                constraintSet.applyTo(layout)

                //TIME
                val tvTime = TextView(context)
                tvTime.id = generateViewId()
                val paramsTime = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                tvTime.layoutParams = paramsTime
                tvTime.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                tvTime.typeface = typeface
                tvTime.text = secondsToString(question.time!!)
                layout.addView(tvTime)
                constraintSet.clone(layout)
                constraintSet.connect(
                    tvTime.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START
                )
                constraintSet.connect(
                    tvTime.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    tvTime.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    10
                )
                constraintSet.applyTo(layout)

                top.addView(layout)
            }
        })
        bt_send_test.setOnClickListener {
            if (bottom.childCount > 1) {
                val listQuestionsSelected = ArrayList<Int>()
                for (i in 1 until bottom.childCount) {
                    listQuestionsSelected.add(bottom.getChildAt(i).id)
                }
                val sendCampaign = SendCampaign(
                    listQuestionsViewModel.getName(),
                    listQuestionsViewModel.getLevel(),
                    listQuestionsViewModel.getLangs(),
                    listQuestionsViewModel.getCopyPaste(),
                    listQuestionsViewModel.getSentReport(),
                    listQuestionsViewModel.getProfile(),
                    listQuestionsViewModel.getUser(),
                    listQuestionsViewModel.getTechnologiesId(),
                    listQuestionsSelected)
                listQuestionsViewModel.postCampaign(sendCampaign)
                listenerOnPostCampaignClickListener?.onPostCampaignClickListener()
            }
        }
    }

    inner class MyDragListener : OnDragListener {

        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                }

                DragEvent.ACTION_DROP -> {
                    // Dropped, reassign View to ViewGroup
                    val view = event.localState as View
                    val owner = view.parent as ViewGroup
                    owner.removeView(view)
                    val container = v as LinearLayout
                    if (container == bottom) {
                        tv_drag_drop.visibility = GONE
                        (((view as ConstraintLayout) as ViewGroup).getChildAt(2) as Button).text = getString(R.string.delete)
                    } else if (container == top) {
                        if (bottom.childCount == 0) {
                            tv_drag_drop.visibility = VISIBLE
                        }
                        (((view as ConstraintLayout) as ViewGroup).getChildAt(2) as Button).text = getString(R.string.choose)
                    }
                    container.addView(view)
                    view.visibility = VISIBLE
                }
                else -> {
                }
            }
            return true
        }
    }

    private fun secondsToString(pTime: Int): String? {
        val hours = (pTime / 60 / 60)
        val minutes = (pTime / 60) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, pTime % 60)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPostCampaignClickListener) {
            listenerOnPostCampaignClickListener = context
        } else {
            throw RuntimeException("$context must implement OnPostCampaignClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerOnPostCampaignClickListener = null
    }

    inner class MyButtonClickListener : OnClickListener {

        override fun onClick(view: View?) {
            val owner = view!!.parent.parent as ViewGroup
            owner.removeView(view.parent as View)
            val container: LinearLayout
            container = if (owner.id == top.id) {
                bottom
            } else {
                top
            }
            if (container == bottom) {
                tv_drag_drop.visibility = GONE
                (view as Button).text = getString(R.string.delete)
            } else {
                if (bottom.childCount == 0) {
                    tv_drag_drop.visibility = VISIBLE
                }
                (view as Button).text = getString(R.string.choose)
            }
            container.addView(view.parent as View)
            view.visibility = VISIBLE
        }
    }

    interface OnPostCampaignClickListener {
        fun onPostCampaignClickListener()
    }
}