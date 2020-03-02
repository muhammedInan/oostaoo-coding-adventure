package com.oostaoo.org.oostaoocodingadventure.ui.listQuestions

import android.content.ClipData
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val factory = ListQuestionsViewModelFactory(arguments!!.getStringArrayList("listTechnologiesName")!!, activity!!.application)
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
                val tv_technology = TextView(context)
                tv_technology.id = generateViewId()
                val paramsTechnology = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                tv_technology.layoutParams = paramsTechnology

                tv_technology.setTextColor(ContextCompat.getColor(context!!, R.color.orange_next_step))
                tv_technology.typeface = typeface
                var questionTechnology = ""
                for (i in 0 until questionsSizeByTechnology.size) {
                    if (listQuestions.indexOf(question) < questionsSizeByTechnology[i]) {
                        questionTechnology = selectedTechnologies[i].name!!
                        break
                    }
                }
                tv_technology.text = questionTechnology
                layout.addView(tv_technology)
                constraintSet.clone(layout)
                constraintSet.connect(
                    tv_technology.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    10
                )
                constraintSet.connect(
                    tv_technology.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP,
                    10
                )
                constraintSet.applyTo(layout)

                //POINTS
                val tv_points = TextView(context)
                tv_points.id = generateViewId()
                val paramsPoints = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                tv_points.layoutParams = paramsPoints
                tv_points.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                tv_points.typeface = typeface
                tv_points.text = StringBuilder(question.points.toString() + " points")
                layout.addView(tv_points)
                constraintSet.clone(layout)
                constraintSet.connect(
                    tv_points.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START
                )
                constraintSet.connect(
                    tv_points.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    tv_points.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP,
                    10
                )
                constraintSet.applyTo(layout)

                //BUTTON
                val buttonChoose = Button(context)
                buttonChoose.id = question.id
                val params = ConstraintLayout.LayoutParams(225, 90)
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
                val tv_question = TextView(context)
                tv_question.id = generateViewId()
                val paramsQuestion = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                tv_question.layoutParams = paramsQuestion
                tv_question.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                tv_question.typeface = typeface
                tv_question.maxLines = 3
                tv_question.ellipsize = TextUtils.TruncateAt.END
                tv_question.text = question.name
                layout.addView(tv_question)
                constraintSet.clone(layout)
                constraintSet.connect(
                    tv_question.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    10
                )
                constraintSet.connect(
                    tv_question.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    265
                )
                constraintSet.connect(
                    tv_question.id,
                    ConstraintSet.TOP,
                    tv_technology.id,
                    ConstraintSet.BOTTOM,
                    10
                )
                constraintSet.applyTo(layout)

                //LEVEL
                val level = Level.valueOf(question.level!!.toUpperCase(Locale.getDefault())).getLevel()

                val iv_bt_radio_level1 = ImageView(context)
                iv_bt_radio_level1.id = generateViewId()
                val paramsLevel1 = ConstraintLayout.LayoutParams(42, 42)
                iv_bt_radio_level1.layoutParams = paramsLevel1
                if (level >= 1) {
                    iv_bt_radio_level1.setImageResource(R.drawable.ic_bt_radio_enable)
                } else {
                    iv_bt_radio_level1.setImageResource(R.drawable.ic_bt_radio_disable)
                }
                layout.addView(iv_bt_radio_level1)
                constraintSet.clone(layout)
                constraintSet.connect(
                    iv_bt_radio_level1.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    10
                )
                constraintSet.connect(
                    iv_bt_radio_level1.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    20
                )
                constraintSet.applyTo(layout)

                val iv_bt_radio_level2 = ImageView(context)
                iv_bt_radio_level2.id = generateViewId()
                val paramsLevel2 = ConstraintLayout.LayoutParams(42, 42)
                iv_bt_radio_level2.layoutParams = paramsLevel2
                if (level >= 2) {
                    iv_bt_radio_level2.setImageResource(R.drawable.ic_bt_radio_enable)
                } else {
                    iv_bt_radio_level2.setImageResource(R.drawable.ic_bt_radio_disable)
                }
                layout.addView(iv_bt_radio_level2)
                constraintSet.clone(layout)
                constraintSet.connect(
                    iv_bt_radio_level2.id,
                    ConstraintSet.START,
                    iv_bt_radio_level1.id,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    iv_bt_radio_level2.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    20
                )
                constraintSet.applyTo(layout)

                val iv_bt_radio_level3 = ImageView(context)
                iv_bt_radio_level3.id = generateViewId()
                val paramsLevel3 = ConstraintLayout.LayoutParams(42, 42)
                iv_bt_radio_level3.layoutParams = paramsLevel3
                if (level >= 3) {
                    iv_bt_radio_level3.setImageResource(R.drawable.ic_bt_radio_enable)
                } else {
                    iv_bt_radio_level3.setImageResource(R.drawable.ic_bt_radio_disable)
                }
                layout.addView(iv_bt_radio_level3)
                constraintSet.clone(layout)
                constraintSet.connect(
                    iv_bt_radio_level3.id,
                    ConstraintSet.START,
                    iv_bt_radio_level2.id,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    iv_bt_radio_level3.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    20
                )
                constraintSet.applyTo(layout)

                //TIME
                val tv_time = TextView(context)
                tv_time.id = generateViewId()
                val paramsTime = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                tv_time.layoutParams = paramsTime
                tv_time.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                tv_time.typeface = typeface
                tv_time.text = secondsToString(question.time!!)
                layout.addView(tv_time)
                constraintSet.clone(layout)
                constraintSet.connect(
                    tv_time.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START
                )
                constraintSet.connect(
                    tv_time.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    tv_time.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    10
                )
                constraintSet.applyTo(layout)

                top.addView(layout)
            }
        })
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
            container.addView(view.parent as View)
            view.visibility = VISIBLE
        }
    }
}