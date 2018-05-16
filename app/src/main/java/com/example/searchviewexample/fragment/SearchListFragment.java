package com.example.searchviewexample.fragment;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import com.example.searchviewexample.MainActivity;
import com.example.searchviewexample.entity.Note;
import com.example.searchviewexample.R;
import com.example.searchviewexample.adapter.NoteListAdapter;
import com.example.searchviewexample.util.Misc;

public class SearchListFragment extends Fragment {
    private static final String STATE_POS = "pos";
    private static final String STATE_TOP = "top";

    protected String mQuery;
    protected NoteListAdapter mAdapter;
    private ListView lv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new NoteListAdapter(getActivity());
        mAdapter.setData(new ArrayList<Note>());

        lv = (ListView) view.findViewById(R.id.list);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                getActivity().startActivity(intent);
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    mAdapter.setScrolling(false);
                } else {
                    mAdapter.setScrolling(true);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        handleIntent();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (lv != null) {
            outState.putInt(STATE_POS, lv.getFirstVisiblePosition());
            if (lv.getChildCount() > 0)
                outState.putInt(STATE_TOP, lv.getChildAt(0).getTop());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_list, null);
    }

    private void handleIntent() {
        Intent intent = getActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            Misc.search(mQuery);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())){ // We're here because of a clicked suggestion
            browse(intent.getData());
            getActivity().finish();
        } else { // Intent contents error.
            getActivity().setTitle("Error!");
        }
    }

    private void browse(Uri uri) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setData(uri);
        startActivity(intent);
    }
}
