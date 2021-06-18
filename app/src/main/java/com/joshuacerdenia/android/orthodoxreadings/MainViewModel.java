package com.joshuacerdenia.android.orthodoxreadings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Reading> readingsLive = null;

    public MutableLiveData<Reading> getReading() {
        if (readingsLive == null) {
            readingsLive = new MutableLiveData<>();
        }
        return readingsLive;
    }

    public void requestReadings() {
        Parser parser = new Parser.Builder().build();
        parser.execute("https://www.nycathedral.org/rss2-readings.php");

        parser.onFinish(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(@NotNull Channel channel) {
                Article firstArticle = channel.getArticles().get(0);
                Reading reading = new Reading(
                        firstArticle.getTitle(),
                        firstArticle.getDescription(),
                        parseDateFrom(firstArticle.getTitle())
                );
                readingsLive.postValue(reading);
            }

            @Override
            public void onError(@NotNull Exception e) {
                readingsLive.postValue(new Reading("Error", "Error", null));
            }
        });
    }

    private Date parseDateFrom(String stringTitle) {
        if (stringTitle == null) return null;
        String stringDate = stringTitle.replace("Readings for ", "");
        DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
    }
}