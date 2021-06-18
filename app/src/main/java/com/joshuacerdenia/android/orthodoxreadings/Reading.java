package com.joshuacerdenia.android.orthodoxreadings;

import java.util.Date;

public class Reading {
    String title;
    String content;
    Date date;

    public Reading(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
}