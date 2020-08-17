package com.eventoCache.dao;

import java.util.List;

import com.eventoCache.model.Event;
import com.eventoCache.model.Guest;

public interface CacheRepository {
	
	public List<Event> listEvents();

	public Event searchEvent(long code);
	
	public List<Guest> listGuests(Event event);
	
	public void saveEvent(Event event);
	public void saveEvents(List<Event> list);
}
