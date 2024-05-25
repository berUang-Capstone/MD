package com.example.subs_inter.customView

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.subs_inter.R

class NameCV : AppCompatEditText, View.OnFocusChangeListener {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        background = ContextCompat.getDrawable(context, R.drawable.button_view)
        inputType = InputType.TYPE_CLASS_TEXT
        onFocusChangeListener = this
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        // Do Nothing
    }
}