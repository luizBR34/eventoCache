package com.eventoCache.services;

import java.util.List;

import javax.validation.Valid;

import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.User;

public interface CacheService {
	
	public List<Event> listEvents(String username);
	public List<Event> listEventsFromAPI(String username);

	public Event searchEvent(String username, long code);
	public Event searchEventFromAPI(long code);
	
	public User seekUser(String login);
	public User seekUserFromAPI(String login);
	
	public List<Guest> listGuests(Event event);
	public List<Guest> listGuestsFromAPI(Event event);
	
	public void saveEvent(Event event);
	public void saveEventIntoAPI(Event event);
	public void saveEvents(String username, List<Event> list);

	public void deleteEvent(String username, long code);
	public void deleteEventFromAPI(long code);
	
	public void saveUser(User user);
	
	public void saveGuest(String username, long eventCode, Guest guest);
	public void saveGuestIntoAPI(long eventCode, @Valid Guest guest);
	
}
