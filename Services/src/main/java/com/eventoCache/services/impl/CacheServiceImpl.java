package com.eventoCache.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eventoCache.persistence.CacheRepository;
import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.User;
import com.eventoCache.services.CacheService;


@Service
public class CacheServiceImpl implements CacheService {
	
	@Autowired
	private CacheRepository rep;
	
    @Value(value = "${eventows.endpoint.uri}")
    private String eventoWSEndpointURI;
    
    RestTemplate restTemplate = new RestTemplate();
    
    private final Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);

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

	@Override
	public List<Event> listEventsFromAPI() {

		ResponseEntity<List<Event>> responseEntity = restTemplate.exchange(eventoWSEndpointURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<Event>>() { });
		List<Event> listOfEvents = null;
		
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			log.info("CacheServiceImpl:listEventsFromAPI() - EventoWS API responded the request successfully!");
			listOfEvents = responseEntity.getBody();
		} else {
			log.error("Error when request event's list from API!");
		}
		
		return listOfEvents;
	}
	
	

	@Override
	public Event searchEventFromAPI(long code) {
		ResponseEntity<Event> responseEntity = restTemplate.exchange(eventoWSEndpointURI, HttpMethod.GET, null, Event.class);
		Event event = null;
		
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			log.info("CacheServiceImpl:searchEventFromAPI() - EventoWS API responded the request successfully!");
			event = responseEntity.getBody();
		} else {
			log.error("Error when request event from API!");
		}
		
		return event;
	}

	@Override
	public List<Guest> listGuestsFromAPI(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveEvents(List<Event> list) {
		rep.saveEvents(list);
		log.error("CacheServiceImpl:saveEvents() - Events were persisted with success!");
	}

	@Override
	public void saveEvent(Event event) {
		rep.saveEvent(event);
		log.error("CacheServiceImpl:saveEvents() - The Event was persisted with success!");		
	}

	@Override
	public User seekUser(String login) {
		return rep.seekUser(login);
	}

	@Override
	public User seekUserFromAPI(String login) {
		
		String path = eventoWSEndpointURI + "/seekUser/" + login;

		ResponseEntity<User> responseEntity = restTemplate.exchange(path, HttpMethod.GET, null, User.class);
		User user = null;
		
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			log.info("CacheServiceImpl:seekUserFromAPI() - EventoWS API responded the request successfully!");
			user = responseEntity.getBody();
		} else {
			log.error("Error when request user from API!");
		}
		
		return user;
	}

	@Override
	public void saveUser(User user) {
		rep.saveUser(user);
		log.error("CacheServiceImpl:saveUser() - The User was persisted with success!");	
	}
}
