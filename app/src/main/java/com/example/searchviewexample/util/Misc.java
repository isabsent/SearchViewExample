package com.example.searchviewexample.util;

import java.util.ArrayList;
import java.util.List;

import com.example.searchviewexample.entity.Note;

public class Misc {

    public static List<Note> search(String query){
        List<Note> results = new ArrayList<>();
        results.add(new Note(1, "title_1", "subtitle_1"));
        results.add(new Note(2, "title_2", "subtitle_2"));
        results.add(new Note(3, "title_3", "subtitle_3"));
        return results;
    }
}
