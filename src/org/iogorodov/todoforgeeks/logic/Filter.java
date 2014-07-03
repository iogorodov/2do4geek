package org.iogorodov.todoforgeeks.logic;

import android.annotation.SuppressLint;
import java.util.ArrayList;

public final class Filter extends Notes {
    private final Model model;
    private final ArrayList<Note> notes = new ArrayList<Note>();

    public Filter(Model model) {
        this.model = model;
    }

    @SuppressLint("DefaultLocale") 
    public void apply(String text) {
        notes.clear();
        final String lowerText = text.toLowerCase();
        for(Note it : model) {
            if (it.text().toLowerCase().contains(lowerText))
                notes.add(it);
        }
        notifyOnChanged();
    }

    public int size() {
        return notes.size();
    }

    public Notes.Note get(int index) {
        return notes.get(index);
    }
}
