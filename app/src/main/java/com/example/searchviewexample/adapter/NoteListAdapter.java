package com.example.searchviewexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.searchviewexample.entity.Note;
import com.example.searchviewexample.R;

//https://tausiq.wordpress.com/2012/12/12/android-custom-adapter-listview-with-listfragment-and-loadermanager-inside-fragmentactivity/
public class NoteListAdapter extends ArrayAdapter<Note> {
    private boolean isScrolling = false;
    private Context context;

    public NoteListAdapter(Context context){
        super(context, R.layout.list_item_note);
        this.context = context;
    }

    public void setData(List<Note> data) {
        clear();
        if (data != null)
            addAll(data);
    }

    @Override
    public boolean hasStableIds() {
        return true; //Essential for showing new data when data set is changed!!!
    }

    private View newView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_note, null);

        TextView title = (TextView) view.findViewById(R.id.note_item_title);
        TextView noteContent = (TextView) view.findViewById(R.id.note_item_content);

        ViewHolder holder = new ViewHolder(title, noteContent);
        view.setTag(holder);
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);
        if (convertView == null)
            convertView = newView(parent.getContext());

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.title.setText(note.getTitle());
        holder.subtitle.setText(note.getSubtitle()); //Show path to file in the container.
        return convertView;
    }

    /**
     * Inform this adapter about scrolling state of list so that lists don't lag due to cache ops.
     * @param isScrolling True if the ListView is still scrolling.
     */
    public void setScrolling(boolean isScrolling){
        this.isScrolling = isScrolling;
        if(!isScrolling)
            notifyDataSetChanged();
    }
}