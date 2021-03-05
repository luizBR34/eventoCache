package com.eventoCache.service;

import java.util.List;

import com.eventoCache.model.Event;
import com.eventoCache.model.Guest;
import com.eventoCache.model.User;

public interface CacheService {
	
	public List<Event> listEvents();
	public List<Event> listEventsFromAPI();

	public Event searchEvent(long code);
	public Event searchEventFromAPI(long code);
	
	public User seekUser(String login);
	public User seekUserFromAPI(String login);
	
	public List<Guest> listGuests(Event event);
	public List<Guest> listGuestsFromAPI(Event event);
	
	public void saveEvent(Event event);
	public void saveEvents(List<Event> list);
	
	public void saveUser(User user);
}
