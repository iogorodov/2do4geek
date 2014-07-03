package org.iogorodov.todoforgeeks.utils;

import android.util.Log;

public class Logg {
	private static String getClassName(Object owner) {
		if (owner == null) {
			return null;
		} else {
			return owner.getClass().getSimpleName();
		}
	}
	
	public static void d(Class<?> owner, String message) {
		Log.d(owner.getSimpleName(), message);
	}
	
	public static void d(Class<?> owner, String message, Object... args) {
		d(owner, String.format(message, args));
	}
	
	public static void d(Object owner, String message) {
		Log.d(getClassName(owner), message);
	}
	
	public static void d(Object owner, String message, Object... args) {
		d(owner, String.format(message, args));
	}

	public static void w(Class<?> owner, String message) {
		Log.w(owner.getSimpleName(), message);
	}

	public static void w(Class<?> owner, String message, Object... args) {
		w(owner, String.format(message, args));
	}

	public static void w(Object owner, String message) {
		Log.w(getClassName(owner), message);
	}

	public static void w(Object owner, String message, Object... args) {
		w(owner, String.format(message, args));
	}

	public static void e(Class<?> owner, String message) {
		Log.e(owner.getSimpleName(), message);
	}

	public static void e(Class<?> owner, String message, Object... args) {
		e(owner, String.format(message, args));
	}

	public static void e(Object owner, String message) {
		Log.e(getClassName(owner), message);
	}

	public static void e(Object owner, String message, Object... args) {
		e(owner, String.format(message, args));
	}
}
