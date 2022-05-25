package com.onopry.budgetapp.views.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.onopry.budgetapp.R

/**
 * TODO: document your custom view class.
 */
class AmountByCategoryItem: ConstraintLayout{

    constructor(context: Context) : super(context)
    constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet)
    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int) : super(context, attrSet, defStyleAttr)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_amount_by_category, this, false)
        val set = ConstraintSet()
        addView(view)

        set.clone(this)
        set.match(view, this)
    }
}

fun ConstraintSet.match(view: View, parentView: View) {
    this.connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
    this.connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
    this.connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
    this.connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
}