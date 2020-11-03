package com.example.cleannotes.business.interactors.notedetail

import com.example.cleannotes.business.interactors.common.DeleteNote
import com.example.cleannotes.framework.presentation.notedetail.state.NoteDetailViewState

// Use cases
class NoteDetailInteractors (
    val deleteNote: DeleteNote<NoteDetailViewState>,
    val updateNote: UpdateNote
)














