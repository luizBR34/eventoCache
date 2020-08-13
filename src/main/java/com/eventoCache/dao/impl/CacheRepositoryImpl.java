package com.eventoCache.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.eventoCache.dao.CacheRepository;
import com.eventoCache.model.Event;
import com.eventoCache.model.Guest;

@Repository
public class CacheRepositoryImpl implements CacheRepository {
	
	public static final String REDIS_LIST_KEY = "ListaEventos";
	
	@Autowired
	@Qualifier("listOperations")
	private ListOperations<String, Event> ListOps;
	
	@Autowired
	@Qualifier("stringOperations")
	private ValueOperations<String, Event> ValueOps;
	

	@Override
	public List<Event> listEvents() {
		return ListOps.range(REDIS_LIST_KEY, 0, -1);
	}

	@Override
	public Event searchEvent(long code) {
		return ValueOps.get(code);
	}

	@Override
	public List<Guest> listGuests(Event event) {
		// TODO Auto-generated method stub
		return null;
	}
}
