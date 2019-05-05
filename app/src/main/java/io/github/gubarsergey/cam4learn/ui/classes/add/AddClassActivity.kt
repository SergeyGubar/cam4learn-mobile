package io.github.gubarsergey.cam4learn.ui.classes.add

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.TextView
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.entity.response.GroupResponseModel
import io.github.gubarsergey.cam4learn.network.entity.response.SubjectResponseModel
import io.github.gubarsergey.cam4learn.network.repository.group.GroupsRepository
import io.github.gubarsergey.cam4learn.network.repository.room.RoomRepository
import io.github.gubarsergey.cam4learn.network.repository.subject.SubjectsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_add_class.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AddClassActivity : AppCompatActivity() {

    companion object {
        fun makeIntent(context: Context) = Intent(context, AddClassActivity::class.java)
    }

    private val compositeDisposable = CompositeDisposable()
    private val groupsRepository: GroupsRepository by inject()
    private val subjectsRepository: SubjectsRepository by inject()
    private val roomRepository: RoomRepository by inject()
    private val groups: MutableList<GroupResponseModel> = mutableListOf()
    private val subjects: MutableList<SubjectResponseModel> = mutableListOf()
    private val groupsAdapter = GroupsAdapter()
    private val roomsAdapter = FreeRoomsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_class)
        groups_recycler.layoutManager = LinearLayoutManager(this)
        groups_recycler.adapter = groupsAdapter
        rooms_recycler.layoutManager = LinearLayoutManager(this)
        rooms_recycler.adapter = roomsAdapter
        setSupportActionBar(add_class_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        loadGroups()
        loadSubjects()
        val startCalendar = Calendar.getInstance()
        val onDateSetStartListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            with(startCalendar) {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            updateDateLabel(class_date_text_view, startCalendar.time)
            loadAvailableRoomsInfo()
        }
        class_date_text_view.setOnClickListener {
            DatePickerDialog(
                this,
                onDateSetStartListener,
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun loadAvailableRoomsInfo() {
        roomRepository.getFreeRooms(
            class_number_edit_text.text.toString().toInt(),
            class_date_text_view.text.toString()
        )
            .subscribeBy(onError = {
                toast(it.localizedMessage)
            },
                onSuccess = {
                    it.fold(
                        {
                            Timber.d("got free rooms: $it")
                            roomsAdapter.updateCheckedStates(it.map { false })
                            roomsAdapter.swapData(it)
                        },
                        {
                            toast(it.localizedMessage)
                        }
                    )
                })
            .addTo(compositeDisposable)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_save -> {
                saveResult()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }

    private fun loadGroups() {
        groupsRepository.getAllGroups()
            .subscribeBy(
                onError = {
                    toast("error ${it.localizedMessage}")
                },
                onSuccess = {
                    it.fold(
                        { response ->
                            groupsAdapter.swapData(response)
                            groups.clear()
                            groups.addAll(response)
                        },
                        {
                            toast("error ${it.localizedMessage}")
                        }
                    )
                }
            ).addTo(compositeDisposable)
    }

    private fun loadSubjects() {
        subjectsRepository.getAllSubjects()
            .subscribeBy(
                onError = {
                    toast("error ${it.localizedMessage}")
                },
                onSuccess = {
                    when (it) {
                        is Result.Success -> {
                            subjects.addAll(it.value!!)
                            val adapter = ArrayAdapter<String>(
                                this,
                                android.R.layout.simple_spinner_item,
                                it.value.map { it.name })
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subjects_spinner.adapter = adapter
                        }
                        is Result.Error -> {
                            toast("error $it")
                        }
                    }
                }
            ).addTo(compositeDisposable)
    }

    private fun saveResult() {

    }

    private fun updateDateLabel(textView: TextView, date: Date) {
        textView.text = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date)
    }
}