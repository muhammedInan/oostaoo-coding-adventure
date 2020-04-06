package com.oostaoo.org.oostaoocodingadventure.ui.testQuestions

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.View.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.R
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.question.Question
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology
import com.oostaoo.org.oostaoocodingadventure.ui.listQuestions.ListQuestionsFragment
import com.oostaoo.org.oostaoocodingadventure.ui.testCandidats.TestCandidatsFragment
import com.oostaoo.org.oostaoocodingadventure.utils.secondsToString
import kotlinx.android.synthetic.main.fragment_test_questions.*
import kotlinx.android.synthetic.main.fragment_test_questions.bottom_navigation_view
import java.util.*
import kotlin.collections.ArrayList

class TestQuestionsFragment : Fragment() {

    private lateinit var testQuestionsViewModel: TestQuestionsViewModel
    private var bottomNavigationViewListener: TestCandidatsFragment.OnBottomNavigationViewListener? = null
    private var mCampaign: Campaign? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val factory =
            TestQuestionsViewModelFactory(arguments!!.getInt("id"), activity!!.application)
        testQuestionsViewModel =
            ViewModelProvider(this, factory).get(TestQuestionsViewModel::class.java)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_test_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottom_navigation_view.selectedItemId = R.id.action_questions
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            updateMainFragment(it.itemId)
        }

        top.setOnDragListener(MyDragListener())
        bottom.setOnDragListener(MyDragListener())

        val listQuestions = ArrayList<Question>()
        val selectedTechnologies = ArrayList<Technology>()
        val possibleQuestions = ArrayList<Question>()
        val selectedQuestions = ArrayList<Question>()

        testQuestionsViewModel.getQuestions().observe(viewLifecycleOwner, Observer {
            listQuestions.addAll(it)
        })

        testQuestionsViewModel.getCampaign()
            .observe(viewLifecycleOwner, Observer { campaign ->
                mCampaign = campaign
                if (listQuestions.size > 0 && campaign?.technologies != null && campaign.technologies.isNotEmpty()) {
                    selectedTechnologies.addAll(campaign.technologies)
                    for (i in 0 until listQuestions.size) {
                        for (j in 0 until selectedTechnologies.size) {
                            if (listQuestions[i].technologies!!.id == selectedTechnologies[j].id) {
                                possibleQuestions.add(listQuestions[i])
                            }
                        }
                    }
                    val listIndexQuestionToDelete = ArrayList<Int>()
                    for (i in 0 until possibleQuestions.size) {
                        for (element in possibleQuestions[i].campaigns!!) {
                            if (element.id == campaign.id) {
                                selectedQuestions.add(possibleQuestions[i])
                                listIndexQuestionToDelete.add(i)
                            }
                        }
                    }
                    for ((countIndexDeleted, index) in listIndexQuestionToDelete.withIndex()) {
                        possibleQuestions.removeAt(index - countIndexDeleted)
                    }
                    for (question in possibleQuestions) {
                        val layout = setQuestionView(question)
                        top.addView(layout)
                    }
                    for (question in selectedQuestions) {
                        val layout = setQuestionView(question)
                        bottom.addView(layout)
                    }
                    if (bottom.childCount > 1) {
                        tv_drag_drop.visibility = GONE
                    }
                }
            })
    }

    private fun setQuestionView(question: Question) : View {
        val layout = ConstraintLayout(context)
        layout.id = question.id

        layout.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            400
        )
        layout.setBackgroundResource(R.drawable.background_item_drag_and_drop)
        layout.setOnLongClickListener { view ->
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = DragShadowBuilder(view)
            view.startDrag(data, shadowBuilder, view, 0)
            view.visibility = INVISIBLE
            true
        }

        val typeface =
            ResourcesCompat.getFont(context!!, R.font.poppins_regular)
        val constraintSet = ConstraintSet()

        //TECHNOLOGY
        val tvTechnology = TextView(context)
        tvTechnology.id = generateViewId()
        val paramsTechnology = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        tvTechnology.layoutParams = paramsTechnology

        tvTechnology.setTextColor(
            ContextCompat.getColor(
                context!!,
                R.color.orange_next_step
            )
        )
        tvTechnology.typeface = typeface
        tvTechnology.text = question.technologies!!.name
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
        val paramsPoints = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        tvPoints.layoutParams = paramsPoints
        tvPoints.setTextColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorPrimary
            )
        )
        tvPoints.typeface = typeface
        tvPoints.text =
            StringBuilder(question.points.toString() + " points")
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
        buttonChoose.setPadding(2, 2, 2, 2)
        buttonChoose.isAllCaps = false
        buttonChoose.setTextColor(
            ContextCompat.getColor(
                context!!,
                R.color.white
            )
        )
        buttonChoose.typeface =
            ResourcesCompat.getFont(context!!, R.font.poppins_regular)
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
        val paramsQuestion = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        tvQuestion.layoutParams = paramsQuestion
        tvQuestion.setTextColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorPrimary
            )
        )
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
        val level = ListQuestionsFragment.Level.valueOf(
            question.level!!.toUpperCase(
                Locale.getDefault()
            )
        ).getLevel()

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
        val paramsTime = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        tvTime.layoutParams = paramsTime
        tvTime.setTextColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorPrimary
            )
        )
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
        return layout
    }

    private fun updateMainFragment(integer: Int): Boolean {
        when (integer) {
            R.id.action_candidats -> bottomNavigationViewListener?.onBottomNavigationViewInteraction(0, mCampaign!!.id)
            R.id.action_questions -> bottomNavigationViewListener?.onBottomNavigationViewInteraction(1, mCampaign!!.id)
            R.id.action_parameters -> bottomNavigationViewListener?.onBottomNavigationViewInteraction(2, mCampaign!!.id)
        }
        return true
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)

        if (context is TestCandidatsFragment.OnBottomNavigationViewListener) bottomNavigationViewListener = context
        else throw RuntimeException("$context must implement onBottomNavigationViewListener")
    }

    override fun onDetach() {

        super.onDetach()
        bottomNavigationViewListener = null
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

    inner class MyButtonClickListener : View.OnClickListener {

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
}
