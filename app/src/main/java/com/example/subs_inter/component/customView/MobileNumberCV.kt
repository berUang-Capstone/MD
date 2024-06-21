package com.example.subs_inter.component.customView

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.subs_inter.R

class MobileNumberCV : AppCompatEditText, View.OnFocusChangeListener {

    private var isMobileValid = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    private fun init() {
        background = ContextCompat.getDrawable(context, R.drawable.button_view)
        inputType = InputType.TYPE_CLASS_PHONE
        onFocusChangeListener = this

        // Ensure the number starts with +62
        if (text.toString().isEmpty()) {
            setText("+62")
        }

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateMobile()
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            validateMobile()
        }
    }

    private fun validateMobile() {
        val mobilePattern = "^\\+62[0-9]{9,13}\$" // Regex for Indonesian mobile number
        isMobileValid = text.toString().trim().matches(mobilePattern.toRegex())
        error = if (!isMobileValid) {
            resources.getString(R.string.invalid_mobile_number)
        } else {
            null
        }
    }
}
