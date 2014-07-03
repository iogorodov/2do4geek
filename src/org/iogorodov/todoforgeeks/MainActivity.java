package org.iogorodov.todoforgeeks;

import org.iogorodov.todoforgeeks.logic.Filter;
import org.iogorodov.todoforgeeks.logic.Model;
import org.iogorodov.todoforgeeks.logic.ModelStorage;
import org.iogorodov.todoforgeeks.logic.Notes;
import org.iogorodov.todoforgeeks.utils.Logg;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final Model model = new Model();
    private Filter filter = null;

    private TextView counterText;
    private TextView titleText;
    private NotesAdapter adapter;

    private final DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            MainActivity.this.onNotesChanged();
        }
    };

    private void addText(String text) {
        model.addNote(text);
    }

    private void onNotesChanged() {
        counterText.setText(Integer.toString(adapter.getCount()));
    }

    private void setNotes(Notes notes, String title) {
        if (adapter != null) {
            adapter.unregisterDataSetObserver(observer);
        }
        
        titleText.setText(title);

        final ListView list = (ListView)findViewById(R.id.main_notes);
        adapter = new NotesAdapter(notes, getLayoutInflater());
        list.setAdapter(adapter);
        adapter.registerDataSetObserver(observer);
        onNotesChanged();
    }
    
    private Filter setFilter(Filter filter) {
    	if (filter == null) {
    		setNotes(model, getString(R.string.main_all_notes));
    		return null;
    	}
    	
    	setNotes(filter, getString(R.string.main_find_results));
    	return filter;
    }
    
    private void onTextChanged(Editable s) {
    	Logg.d(this, s.toString());
    	if (s.length() == 0) {
    		filter = setFilter(null);
    	} else {
    		if (filter == null)
        		filter = setFilter(new Filter(model));
    		filter.apply(s.toString());
    	}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final ModelStorage storage = new ModelStorage(this);
        storage.load(model);

        counterText = (TextView)findViewById(R.id.main_counter);
        titleText = (TextView)findViewById(R.id.main_title);

        final EditText inputText = (EditText)findViewById(R.id.main_input_text);
        inputText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				MainActivity.this.onTextChanged(s);
			}
		});
        findViewById(R.id.main_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputText.getText() == null)
                    return;
                final String text = inputText.getText().toString().trim();
                inputText.setText("");
                if (text.isEmpty())
                    return;

                MainActivity.this.addText(text);
            }
        });

        setFilter(null);
    }
    
    @Override
    protected void onPause() {
    	final ModelStorage storage = new ModelStorage(this);
        storage.save(model);
    	
    	super.onPause();
    }
}
