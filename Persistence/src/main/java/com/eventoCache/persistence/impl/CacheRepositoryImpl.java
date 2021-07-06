package com.eventoCache.persistence.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.eventoCache.persistence.CacheRepository;
import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.User;

@Repository
public class CacheRepositoryImpl implements CacheRepository {
	
	//public static final String REDIS_EVENT_LIST_KEY = "ListaEventos";
	//public static final String REDIS_USER_KEY = "Usuario";
	
	private final Logger log = LoggerFactory.getLogger(CacheRepositoryImpl.class);
	
	@Autowired
	@Qualifier("listOperations")
	private ListOperations<String, Event> ListOps;
	
	@Autowired
	@Qualifier("eventOperations")
	private ValueOperations<Long, Event> EventOps;
	
	@Autowired
	@Qualifier("hashUserOperations")
	private HashOperations<String, Integer, com.eventoApp.models.User> UserOps;
	

	@Override
	public List<Event> listEvents(String username) {
		return ListOps.range(username + "List", 0, -1);
	}

	@Override
	public Event searchEvent(long code) {
		return EventOps.get(code);
	}

	@Override
	public List<Guest> listGuests(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveEvents(String username, List<Event> list) {
		ListOps.rightPushAll(username + "List", list);
	}

	@Override
	public void saveEvent(Event event) {
		ListOps.rightPush(event.getUser().getUserName() + "List", event);
	}
	
	@Override
	public void updateEvent(long code, Event event) {
		ListOps.set(event.getUser().getUserName() + "List", code, event);
	}
	

	@Override
	public User seekUser(String login) {
		return UserOps.get(login, login.hashCode());
	}

	@Override
	public void saveUser(User user) {
		UserOps.put(user.getUserName(), user.getUserName().hashCode(), user);
	}
}
