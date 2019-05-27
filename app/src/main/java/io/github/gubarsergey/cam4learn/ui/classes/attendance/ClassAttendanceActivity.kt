package io.github.gubarsergey.cam4learn.ui.classes.attendance

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.repository.attendance.AttendanceRepository
import io.github.gubarsergey.cam4learn.utility.extension.filledIntentFor
import io.github.gubarsergey.cam4learn.utility.helper.BitmapHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activty_class_attendance.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import timber.log.Timber


private const val REQUEST_IMAGE_CAPTURE = 1

class ClassAttendanceActivity : AppCompatActivity() {
    companion object {
        private const val CLASS_ID_EXTRA = "CLASS_ID_EXTRA"
        fun makeIntent(context: Context, classId: Int) = filledIntentFor<ClassAttendanceActivity>(context) {
            putExtra(CLASS_ID_EXTRA, classId)
        }
    }

    private val classId by lazy { intent.getIntExtra(CLASS_ID_EXTRA, 0) }
    private val attendanceRepository: AttendanceRepository by inject()
    private val adapter = ClassAttendanceAdapter(
        ::onSaveClicked,
        ::onIsPresentClicked
    )
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_class_attendance)
        loadAttendanceData()
        class_attendance_recycler.adapter = adapter
        class_attendance_recycler.layoutManager = LinearLayoutManager(this)
        class_attendance_add_photo_fab.setOnClickListener {
            takePhoto()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }

    private fun loadAttendanceData() {
        attendanceRepository.getAllAttendance(classId)
            .subscribeBy(
                onError = {
                    Timber.d("Error ${it.localizedMessage}")
                    toast("Error ${it.localizedMessage}")
                },
                onSuccess = {
                    it.fold(
                        { response ->
                            adapter.swapData(response)
                        },
                        {
                            Timber.d("Error ${it.localizedMessage}")
                            toast("Error ${it.localizedMessage}")
                        }
                    )
                }
            )
            .addTo(compositeDisposable)
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            data?.let {
                val imageBitmap = data.extras.get("data") as Bitmap
                val base64 = BitmapHelper.encodeToBase64(imageBitmap)
                attendanceRepository.recognizeAuditory(classId, base64)
                    .subscribeBy(
                        onSuccess = {
                            toast("Image was uploaded!")
                        },
                        onError = {
                            toast("Error loading images ${it.localizedMessage}")
                        }
                    ).addTo(compositeDisposable)
            } ?: toast("No image selected!")
        }
    }

    private fun onSaveClicked(id: Int, mark: Int) {
        attendanceRepository.putMark(classId, id, mark)
            .subscribeBy(
                onError = {
                    Timber.d("Error: ${it.localizedMessage}")
                    toast("Error: ${it.localizedMessage}")
                },
                onSuccess = {
                    Timber.d("Success: $it")
                    toast("Success!")
                }
            )
            .addTo(compositeDisposable)
    }

    private fun onIsPresentClicked(id: Int) {
        attendanceRepository.putPresent(classId, id)
            .subscribeBy(
                onError = {
                    Timber.d("Error: ${it.localizedMessage}")
                    toast("Error: ${it.localizedMessage}")
                },
                onSuccess = {
                    toast("Success!")
                    adapter.notifyItemPresentStateChanged(id, true)
                }
            )
            .addTo(compositeDisposable)
    }
}