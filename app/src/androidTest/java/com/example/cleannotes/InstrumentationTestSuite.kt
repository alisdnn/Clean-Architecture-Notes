package com.example.cleannotes

import com.example.cleannotes.framework.datasource.cache.NoteDaoServiceTests
import com.example.cleannotes.framework.datasource.network.NoteFirestoreServiceTests
import com.example.cleannotes.framework.presentation.end_to_end.NotesFeatureTest
import com.example.cleannotes.framework.presentation.notedetail.NoteDetailFragmentTests
import com.example.cleannotes.framework.presentation.notelist.NoteListFragmentTests
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite


@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    NoteDaoServiceTests::class,
    NoteFirestoreServiceTests::class,
    NoteDetailFragmentTests::class,
    NoteListFragmentTests::class,
    NotesFeatureTest::class
)
class InstrumentationTestSuite

























