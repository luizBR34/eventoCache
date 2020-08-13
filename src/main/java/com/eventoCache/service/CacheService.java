package com.eventoCache.service;

import java.util.List;

import com.eventoCache.model.Event;
import com.eventoCache.model.Guest;

public interface CacheService {
	
	public List<Event> listEvents();

	public Event searchEvent(long code);
	
	public List<Guest> listGuests(Event event);

}
