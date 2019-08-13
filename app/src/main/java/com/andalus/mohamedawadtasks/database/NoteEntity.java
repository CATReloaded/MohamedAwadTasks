package com.andalus.mohamedawadtasks.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.andalus.mohamedawadtasks.Repositories.ConstantsRepository;


@Entity(tableName = ConstantsRepository.DATABASE_TABLE_NAME)
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = ConstantsRepository.HEAD_COLUMN_NAME)
    private String head;

    @ColumnInfo(name = ConstantsRepository.SUBJECT_COLUMN_NAME)
    private String subject;

    @ColumnInfo(name = ConstantsRepository.PRIORITY_COLUMN_NAME)
    private String priority;

    public NoteEntity(String head, String subject, String priority) {
        this.head = head;
        this.subject = subject;
        this.priority = priority;
    }

    @Ignore
    public NoteEntity(int id, String head, String subject, String priority) {
        this.id = id;
        this.head = head;
        this.subject = subject;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
