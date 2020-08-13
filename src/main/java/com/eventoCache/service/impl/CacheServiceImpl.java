package com.eventoCache.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventoCache.dao.CacheRepository;
import com.eventoCache.model.Event;
import com.eventoCache.model.Guest;
import com.eventoCache.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService {
	
	private CacheRepository rep;

	@Override
	public List<Event> listEvents() {
		return rep.listEvents();
	}

	@Override
	public Event searchEvent(long code) {
		return rep.searchEvent(code);
	}

	@Override
	public List<Guest> listGuests(Event event) {
		// TODO Auto-generated method stub
		return null;
	}
}
