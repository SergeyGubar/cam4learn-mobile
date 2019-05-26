package io.github.gubarsergey.cam4learn.ui.classes.attendance

import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.AttendanceResponseModel
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater
import io.github.gubarsergey.cam4learn.utility.extension.toBoolean
import kotlinx.android.synthetic.main.item_class_attendance.*
import timber.log.Timber

class ClassAttendanceAdapter(
    private val onSaveClicked: (id: Int, isPresent: Boolean, mark: Int) -> Unit
) : BaseAdapter<AttendanceResponseModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AttendanceResponseModel> =
        ClassAttendanceViewHolder(parent.context.inflater.inflate(R.layout.item_class_attendance, parent, false))

    inner class ClassAttendanceViewHolder(view: View) : BaseViewHolder<AttendanceResponseModel>(view) {
        override fun bind(item: AttendanceResponseModel) {
            with(item) {
                Timber.d("bind: $item")
                item_class_attendance_student_text.text = surname
                item_class_attendance_mark_input.setText(value.toString())
                item_class_attendance_is_present_checkbox.isChecked = isPresent.toBoolean()
                item_class_attendance_save_button.setOnClickListener {
                    onSaveClicked(
                        id,
                        item_class_attendance_is_present_checkbox.isChecked,
                        item_class_attendance_mark_input.text.toString().toInt()
                    )
                }
            }

        }
    }
}