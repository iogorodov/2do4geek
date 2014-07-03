package org.iogorodov.todoforgeeks.logic;

import java.util.ArrayList;
import java.util.List;

public final class Model extends Notes {
    private final class NoteImpl implements Notes.Note {
        private String text;
        private int id;

        public NoteImpl(int id, String text) {
            this.id = id;
            this.text = text;
        }

        @Override
        public String text() {
            return text;
        }

        @Override
        public int id() {
            return id;
        }

        public NoteImpl text(String text) {
            this.text = text;
            return this;
        }

        public NoteImpl id(int id) {
            this.id = id;
            return this;
        }
    }

    private final ArrayList<NoteImpl> notes = new ArrayList<NoteImpl>();
    
    void load(List<String> lines) {
    	notes.clear();
    	for (String line : lines)
    		notes.add(new NoteImpl(lines.size() - notes.size(), line));
    }
    
    ArrayList<String> store() {
    	final ArrayList<String> result = new ArrayList<String>();
    	for (NoteImpl note : notes)
    		result.add(note.text());
    	return result;
    }

    public void addNote(String text) {
        notes.add(0, new NoteImpl(notes.size(), text));
        notifyOnChanged();
    }

    public void updateNote(Notes.Note note, String text) {
        notes.get(notes.size() - note.id() - 1).text(text);
        notifyOnChanged();
    }

    public void removeNote(Notes.Note note) {
        notes.remove(notes.size() - note.id() - 1);
        final int count = notes.size();
        for (int i = 0; i < count - 1; ++i) {
            notes.get(i).id(count - i - 1);
        }
        notifyOnChanged();
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public Note get(int index) {
        return notes.get(index);
    }
}
