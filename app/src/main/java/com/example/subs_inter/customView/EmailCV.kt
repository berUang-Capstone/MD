package com.example.subs_inter.customView

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.subs_inter.R

class EmailCV : AppCompatEditText, View.OnFocusChangeListener {

    var isEmailValid = false
    private var isEmailHasTaken = false
    private var emailSame: String? = null

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
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        onFocusChangeListener = this

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail()
                if (isEmailHasTaken) {
                    validateEmailHasTaken()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            validateEmail()
            if (isEmailHasTaken) {
                validateEmailHasTaken()
            }
        }
    }

    private fun validateEmail() {
        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(text.toString().trim()).matches()
        error = if (!isEmailValid) {
            resources.getString(R.string.emailFormatWrong)
        } else {
            null
        }
    }

    private fun validateEmailHasTaken() {
        error = if (isEmailHasTaken && text.toString().trim() == emailSame) {
            resources.getString(R.string.emailTaken)
        } else {
            null
        }
    }
}