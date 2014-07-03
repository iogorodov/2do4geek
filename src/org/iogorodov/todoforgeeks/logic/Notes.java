package org.iogorodov.todoforgeeks.logic;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class Notes implements Iterable<Notes.Note> {
    public interface Note {
        int id();
        String text();
    }

    public interface OnChangedListener {
        void onChanged(Notes notes);
    }

    private final static class NotesIterator implements Iterator<Note> {
        private final Notes notes;
        private int index = 0;

        public NotesIterator(Notes notes) {
            this.notes = notes;
        }

        @Override
        public boolean hasNext() {
            return index < notes.size();
        }

        @Override
        public Note next() {
            if (index >= notes.size())
                throw new NoSuchElementException(String.format("Index %d out of bounds %s", index, notes.size()));

            return notes.get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private OnChangedListener listener;

    protected void notifyOnChanged() {
        if (listener != null)
            listener.onChanged(this);
    }

    public void setOnChangedListener(OnChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public Iterator<Note> iterator() {
        return new NotesIterator(this);
    }

    public abstract int size();
    public abstract Note get(int index);
}
