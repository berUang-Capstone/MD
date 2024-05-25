package com.example.subs_inter.customView

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.subs_inter.R

class PasswordCV : AppCompatEditText, View.OnTouchListener {

    var isPasswordValid: Boolean = false
    private lateinit var visibilityToggleIcon: Drawable
    private var isPasswordVisible = false

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
        // Set background with border
        background = ContextCompat.getDrawable(context, R.drawable.button_view)
        transformationMethod = PasswordTransformationMethod.getInstance()

        // Set text change listener
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword()
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        setOnTouchListener(this)
        updateVisibilityToggleIcon()
    }

    private fun updateVisibilityToggleIcon() {
        val visibilityIconRes = if (isPasswordVisible) {
            R.drawable.ic_visibility_off
        } else {
            R.drawable.ic_visibility
        }
        visibilityToggleIcon = ContextCompat.getDrawable(context, visibilityIconRes)!!
        visibilityToggleIcon.setBounds(
            0,
            0,
            visibilityToggleIcon.intrinsicWidth,
            visibilityToggleIcon.intrinsicHeight
        )
        setCompoundDrawables(null, null, visibilityToggleIcon, null)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (right - compoundPaddingRight)) {
                isPasswordVisible = !isPasswordVisible
                transformationMethod = if (isPasswordVisible) {
                    HideReturnsTransformationMethod.getInstance()
                } else {
                    PasswordTransformationMethod.getInstance()
                }
                updateVisibilityToggleIcon()
                setSelection(text?.length ?: 0) // Move cursor to the end
                return true
            }
        }
        return false
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) {
            validatePassword()
        }
    }

    private fun validatePassword() {
        isPasswordValid = (text?.length ?: 0) >= 8
        error = if (!isPasswordValid) {
            resources.getString(R.string.password_length_error)
        } else {
            null
        }
    }
}