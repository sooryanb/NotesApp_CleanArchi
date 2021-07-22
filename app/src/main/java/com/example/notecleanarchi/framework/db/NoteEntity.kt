package com.example.notecleanarchi.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.Note

@Entity(tableName = "note")
data class NoteEntity(

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "creation_date")
    var creationTime: Long,

    @ColumnInfo(name = "update_time")
    var updateTime: Long,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) {
    companion object {
        fun fromNote(note: Note) = NoteEntity(note.title, note.content, note.creationTime, note.updateTime, note.id)
    }

    fun toNote() = Note(title, content, creationTime, updateTime, id)
}