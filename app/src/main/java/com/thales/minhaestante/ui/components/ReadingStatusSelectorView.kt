package com.thales.minhaestante.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.thales.minhaestante.R
import com.thales.minhaestante.data.model.ReadingStatus
import com.thales.minhaestante.databinding.ViewReadingStatusSelectorBinding

class ReadingStatusSelectorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewReadingStatusSelectorBinding.inflate(LayoutInflater.from(context), this)

    var onStatusChanged: ((ReadingStatus) -> Unit)? = null

    var status: ReadingStatus? = null
        private set

    init {
        orientation = VERTICAL
        binding.statusToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            val selected = statusForButton(checkedId) ?: return@addOnButtonCheckedListener
            if (selected == status) return@addOnButtonCheckedListener

            status = selected
            updateHint(selected)
            onStatusChanged?.invoke(selected)
        }
    }

    fun setStatus(newStatus: ReadingStatus) {
        status = newStatus
        binding.statusToggleGroup.check(buttonIdFor(newStatus))
        updateHint(newStatus)
    }

    private fun updateHint(status: ReadingStatus) {
        binding.statusHint.setText(
            when (status) {
                ReadingStatus.WANT_TO_READ -> R.string.status_hint_want_to_read
                ReadingStatus.READING -> R.string.status_hint_reading
                ReadingStatus.READ -> R.string.status_hint_read
            }
        )
    }

    private fun buttonIdFor(status: ReadingStatus): Int = when (status) {
        ReadingStatus.WANT_TO_READ -> R.id.buttonWantToRead
        ReadingStatus.READING -> R.id.buttonReading
        ReadingStatus.READ -> R.id.buttonRead
    }

    private fun statusForButton(buttonId: Int): ReadingStatus? = when (buttonId) {
        R.id.buttonWantToRead -> ReadingStatus.WANT_TO_READ
        R.id.buttonReading -> ReadingStatus.READING
        R.id.buttonRead -> ReadingStatus.READ
        else -> null
    }
}
