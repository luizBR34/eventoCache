package com.eventoCache.persistence;

import java.util.List;

import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.User;

public interface CacheRepository {
	
	public List<Event> listEvents(String username);

	public Event searchEvent(String username, long code);
	
	public User seekUser(String login);
	
	public void saveEvent(Event event);

	public void saveEvents(String username, List<Event> list);

	public void deleteEvent(Event event);
	
	public void updateEvent(Event event);

	public void saveUser(User user);
}
