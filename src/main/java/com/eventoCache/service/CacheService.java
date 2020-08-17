package com.eventoCache.service;

import java.util.List;

import com.eventoCache.model.Event;
import com.eventoCache.model.Guest;

public interface CacheService {
	
	public List<Event> listEvents();
	public List<Event> listEventsFromAPI();

	public Event searchEvent(long code);
	public Event searchEventFromAPI(long code);
	
	public List<Guest> listGuests(Event event);
	public List<Guest> listGuestsFromAPI(Event event);
	
	public void saveEvent(Event event);
	public void saveEvents(List<Event> list);
}
