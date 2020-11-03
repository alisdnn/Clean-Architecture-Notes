package com.example.cleannotes.framework.presentation.end_to_end

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.cleannotes.BaseTest
import com.example.cleannotes.R
import com.example.cleannotes.business.data.network.abstraction.NoteNetworkDataSource
import com.example.cleannotes.di.TestAppComponent
import com.example.cleannotes.framework.datasource.cache.database.NoteDao
import com.example.cleannotes.framework.datasource.cache.mappers.CacheMapper
import com.example.cleannotes.framework.datasource.cache.model.NoteCacheEntity
import com.example.cleannotes.framework.datasource.data.NoteDataFactory
import com.example.cleannotes.framework.presentation.MainActivity
import com.example.cleannotes.framework.presentation.notelist.NoteListAdapter.*
import com.example.cleannotes.util.EspressoIdlingResourceRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/*

    --Test cases:
    1. start SplashFragment, confirm logo is visible
    2. Navigate NoteListFragment, confirm list is visible
    3. Select a note from list, confirm correct title and body is visible
    4. Navigate BACK, confirm NoteListFragment in view


 */
@ExperimentalCoroutinesApi
@FlowPreview
@RunWith(AndroidJUnit4ClassRunner::class)
class NotesFeatureTest: BaseTest() {

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Inject
    lateinit var cacheMapper: CacheMapper

    @Inject
    lateinit var noteDataFactory: NoteDataFactory

    @Inject
    lateinit var dao: NoteDao

    @Inject
    lateinit var noteNetworkDataSource: NoteNetworkDataSource

    private val testEntityList: List<NoteCacheEntity>

    init {
        injectTest()
        testEntityList = cacheMapper.noteListToEntityList(
            noteDataFactory.produceListOfNotes()
        )
        prepareDataSet(testEntityList)
    }

    // ** Must clear network and cache so there is no previous state issues **
    private fun prepareDataSet(testData: List<NoteCacheEntity>) = runBlocking{
        // clear any existing data so recyclerview isn't overwhelmed
        dao.deleteAllNotes()
        noteNetworkDataSource.deleteAllNotes()
        dao.insertNotes(testData)
    }

    @Test
    fun generalEndToEndTest(){

        val scenario = launchActivity<MainActivity>()

        // Wait for NoteListFragment to come into view
        waitViewShown(withId(R.id.recycler_view))

        val recyclerView = onView(withId(R.id.recycler_view))

        // confirm NoteListFragment is in view
        recyclerView.check(matches(isDisplayed()))

        // Select a note from the list
        recyclerView.perform(
            actionOnItemAtPosition<NoteViewHolder>(1, click())
        )

        // Wait for NoteDetailFragment to come into view
        waitViewShown(withId(R.id.note_scroll_view))

        // Confirm NoteDetailFragment is in view
        onView(withId(R.id.note_scroll_view)).check(matches(isDisplayed()))
        onView(withId(R.id.note_title)).check(matches(not(withText(""))))
        onView(withId(R.id.note_body)).check(matches(not(withText(""))))

        // press back arrow in toolbar
        onView(withId(R.id.toolbar_primary_icon)).perform(click())

        // confirm NoteListFragment is in view
        recyclerView.check(matches(isDisplayed()))
    }

    override fun injectTest() {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }


}



























