package com.example.searchviewexample.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import java.util.List;

import com.example.searchviewexample.entity.Note;
import com.example.searchviewexample.util.Misc;

public class SearchSuggestionsProvider extends ContentProvider {
    private static final String
            SEARCH_SUGGEST_MIMETYPE = "vnd.android.cursor.item/vnd.example.search_suggestion",
            PROVIDER_NAME = "com.example.searchviewexample.search.suggest";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME);

    /**
     * Always clears all suggestions. Parameters other than uri are ignored.
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return SEARCH_SUGGEST_MIMETYPE;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Actual search happens here.
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(new String[]{
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_TEXT_2,
                BaseColumns._ID});

        String query = uri.getLastPathSegment();
        List<Note> results = Misc.search(query);

        for (Note note : results)
            cursor.newRow()
                    .add(note.getTitle())
                    .add(note.getSubtitle())
                    .add(note.getId());
        return cursor;
    }
}