package co.id.sofcograha.base.utils.threadlocals;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import co.id.sofcograha.base.utils.Message;

@Component
public class LocalWarnings {

	private static ThreadLocal<ArrayList<Message>> warningsHolder = new ThreadLocal<ArrayList<Message>>();

	public static boolean hasWarnings() { 
		return warningsHolder.get() != null; 
	}

	private static void createIfEmpty() {
		if (!hasWarnings()) {
			warningsHolder.set(new ArrayList<Message>());
		}
	}

	public void addWarning(Message warning) {
		getWarnings().add(warning);
	}

	public static ArrayList<Message> getWarnings() {
		createIfEmpty();
		return warningsHolder.get();
	}

	public static void remove() {
		warningsHolder.remove();
	}
}

