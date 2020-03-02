package com.oostaoo.org.oostaoocodingadventure.ui.newTest

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import kotlinx.android.synthetic.main.fragment_new_test.*
import kotlinx.android.synthetic.main.fragment_new_test.view.*
import java.util.*
import kotlin.collections.ArrayList

class MultiSelectionSpinner : AppCompatSpinner, OnMultiChoiceClickListener {
    private var itemList: ArrayList<Item>? = null
    private var selection: BooleanArray? = null
    private var adapter: ArrayAdapter<String?>

    constructor(context: Context?) : super(context) {
        adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item)
        super.setAdapter(adapter)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item)
        super.setAdapter(adapter)
    }

    override fun onClick(dialog: DialogInterface?, idx: Int, isChecked: Boolean) {

        if (selection != null && idx < selection!!.size) {
            selection!![idx] = isChecked

            adapter.clear()
            adapter.add(buildSelectedItemString())
        } else {
            throw IllegalArgumentException("'idx' is out of bounds.")
        }
    }

    override fun performClick(): Boolean {
        super.performClick()

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val itemNames = arrayOfNulls<String>(itemList!!.size)

        for (i in itemList!!.indices) {
            itemNames[i] = itemList!![i].name
        }

        builder.setMultiChoiceItems(itemNames, selection, this)

        builder.setPositiveButton("OK") { _, _ -> }

        builder.show()

        return true
    }

    fun setItems(items: ArrayList<Item>?) {
        this.itemList = items
        selection = BooleanArray(this.itemList!!.size)
        adapter.clear()
        adapter.add("")
        Arrays.fill(selection, false)
    }

    fun setSelection(selection: ArrayList<Item>) {
        for (i in this.selection!!.indices) {
            this.selection!![i] = false
        }
        for (sel in selection) {
            for (j in 0 until itemList!!.size) {
                if (itemList!![j].name!! == sel.name) {
                    this.selection!![j] = true
                }
            }
        }
        adapter.clear()
        adapter.add(buildSelectedItemString())
    }

    private fun buildSelectedItemString(): String? {
        val sb = StringBuilder()
        var foundOne = false
        for (i in itemList!!.indices) {
            if (selection!![i]) {
                if (foundOne) {
                    sb.append(", ")
                }
                foundOne = true
                sb.append(itemList!![i].name)
            }
        }
        return sb.toString()
    }

    fun getSelectedItems(): ArrayList<Item>? {
        val selectedItems: ArrayList<Item> =
            ArrayList()
        for (i in 0 until itemList!!.size) {
            if (selection!![i]) {
                selectedItems.add(itemList!![i])
            }
        }
        return selectedItems
    }
}