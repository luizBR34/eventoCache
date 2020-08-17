package com.eventoCache.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger log = LoggerFactory.getLogger(CacheRepositoryImpl.class);
	
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

	@Override
	public void saveEvents(List<Event> list) {
		ListOps.rightPushAll(REDIS_LIST_KEY, list);
	}

	@Override
	public void saveEvent(Event event) {
		ListOps.rightPush(REDIS_LIST_KEY, event);
	}
}
