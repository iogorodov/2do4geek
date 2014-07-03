package org.iogorodov.todoforgeeks.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.iogorodov.todoforgeeks.utils.Logg;

import android.content.Context;

public class ModelStorage {
	private final static String FILE_NAME = "todo.txt";
	private final Context context;
	
	public ModelStorage(Context context) {
		this.context = context;
	}
	
	private static ArrayList<String> readLines(Context context) {
		final ArrayList<String> result = new ArrayList<String>();
		FileInputStream file = null;
		try {
			file = context.openFileInput(FILE_NAME);
	        final BufferedReader reader = new BufferedReader(new InputStreamReader(file));
	        String line = reader.readLine();
	        while (line != null) {
	        	result.add(line);
	        	line = reader.readLine();
	        }
	        
        } catch (IOException e) {
        	Logg.w(ModelStorage.class, e.getMessage());
        	return null;
        } finally {
            try {
            	if (file != null)
            		file.close();
            } catch (IOException e) {
            	Logg.w(ModelStorage.class, e.getMessage());
            }
        }
		
		return result;
	}
	
	private static boolean writeLines(Context context, List<String> lines) {
		final String endl = System.getProperty("line.separator");
		FileOutputStream file = null;
		try {
			file = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
	        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file));
        	for (String line : lines) {
        		writer.write(line);
        		writer.write(endl);
        	}
        		
        	writer.flush();
			file.flush();
		} catch (IOException e) {
        	Logg.w(ModelStorage.class, e.getMessage());
        	return false;
        } finally {
            try {
            	if (file != null)
            		file.close();
            } catch (IOException e) {
            	Logg.w(ModelStorage.class, e.getMessage());
            }
        }
	
		return true;
	}
	
	public boolean load(Model model) {
		final List<String> lines = readLines(context);
		if (lines == null)
			return false;
		
		model.load(lines);
		return true;
	}
	
	public boolean save(Model model) {
		return writeLines(context, model.store());
	}
}
