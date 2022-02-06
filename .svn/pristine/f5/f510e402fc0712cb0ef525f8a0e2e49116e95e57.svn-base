package co.id.sofcograha.base.utils.threadlocals;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import co.id.sofcograha.base.utils.Message;

@Component
public class LocalNotices {

	private static ThreadLocal<ArrayList<Message>> noticesHolder = new ThreadLocal<ArrayList<Message>>();

	private static boolean hasNotices() {
		return noticesHolder.get() != null;
	}

	private static void createIfEmpty() {
		if (!hasNotices()) {
			noticesHolder.set(new ArrayList<Message>());
		}
	}

	public void addNotice(Message notice) {
		createIfEmpty();
		noticesHolder.get().add(notice);
	}

	public static ArrayList<Message> getNotices() {
		createIfEmpty();
		return noticesHolder.get();
	}

	public static void remove() {
		noticesHolder.remove();
	}
}

