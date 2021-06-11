package com.eventoCache.services;

import java.util.List;

import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.User;

public interface CacheService {
	
	public List<Event> listEvents();
	public List<Event> listEventsFromAPI(String username);

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
