package com.eventoCache.dao;

import java.util.List;

import com.eventoCache.model.Event;
import com.eventoCache.model.Guest;
import com.eventoCache.model.User;

public interface CacheRepository {
	
	public List<Event> listEvents();

	public Event searchEvent(long code);
	
	public User seekUser(String login);
	
	public List<Guest> listGuests(Event event);
	
	public void saveEvent(Event event);
	public void saveEvents(List<Event> list);

	public void saveUser(User user);
}
