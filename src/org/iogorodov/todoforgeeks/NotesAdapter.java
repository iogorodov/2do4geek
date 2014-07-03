package org.iogorodov.todoforgeeks;

import org.iogorodov.todoforgeeks.logic.Notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NotesAdapter extends BaseAdapter implements Notes.OnChangedListener {
    private final Notes notes;
    private final LayoutInflater inflater;

    public NotesAdapter(Notes notes, LayoutInflater inflater) {
        this.notes = notes;
        this.inflater = inflater;
        this.notes.setOnChangedListener(this);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.note, parent, false);
            if (convertView == null)
                return null;
        }

        final boolean isLast = position == getCount() - 1;
        final View bottomDivider = convertView.findViewById(R.id.note_bottom_padding);
        if (bottomDivider != null)
            bottomDivider.setVisibility(isLast ? View.VISIBLE : View.GONE);

        final TextView text = (TextView)convertView.findViewById(R.id.note_text);
        if (text != null)
            text.setText(notes.get(position).text());

        return convertView;
    }

    @Override
    public void onChanged(Notes notes) {
        notifyDataSetChanged();
    }
}
