package com.example.subs_inter.component.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.subs_inter.R
import java.text.NumberFormat
import java.util.Locale

class FinanceCV @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.light_grey)
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 50f
    }
    private val cornerRadius = 20f

    var title: String = "Salary"
    var amount: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    var customBackgroundColor: Int = ContextCompat.getColor(context, R.color.light_grey)
        set(value) {
            field = value
            backgroundPaint.color = value
            invalidate()
        }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.FinanceCV, 0, 0)
            title = typedArray.getString(R.styleable.FinanceCV_title) ?: title
            amount = typedArray.getInt(R.styleable.FinanceCV_amount, 0)
            customBackgroundColor = typedArray.getColor(R.styleable.FinanceCV_backgroundColor, customBackgroundColor)
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw rounded rectangle background
        val rect = canvas.clipBounds
        canvas.drawRoundRect(
            rect.left.toFloat(),
            rect.top.toFloat(),
            rect.right.toFloat(),
            rect.bottom.toFloat(),
            cornerRadius,
            cornerRadius,
            backgroundPaint
        )

        // Draw title text
        canvas.drawText(title, 50f, 100f, textPaint)

        // Draw amount text with "Rp" prefix
        val formattedAmount = "Rp " + NumberFormat.getNumberInstance(Locale("id", "ID")).format(amount)
        canvas.drawText(formattedAmount, 50f, 200f, textPaint)
    }
}
