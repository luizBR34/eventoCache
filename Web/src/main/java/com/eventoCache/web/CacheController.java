package com.eventoCache.web;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventoApp.models.Event;
import com.eventoApp.models.User;
import com.eventoCache.services.CacheService;

@RestController
@RequestMapping("/eventoCache")
public class CacheController {
	
	@Autowired
	private CacheService service;
	
	
	@GetMapping(produces="application/json")
	public @ResponseBody List<Event> listEvents() {
		List<Event> eventList = service.listEvents();
		
		//Send request to EventoWS
		if (isNull(eventList) || eventList.isEmpty()) {
			eventList = service.listEventsFromAPI();
			
			//Save data in the Redis for next requests.
			if (nonNull(eventList) & (!eventList.isEmpty())) {
				service.saveEvents(eventList);
			}
		}
		
		
		return eventList;
	}
	
	
	@GetMapping(value="/{code}", produces="application/json")
	public @ResponseBody Event searchEvent(@PathVariable("code") long code) {
		Event event = service.searchEvent(code);
		
		//Send request to EventoWS
		if (isNull(event)) {
			event = service.searchEventFromAPI(code);
			
			//Save data in the Redis for next requests.
			if (nonNull(event)) {
				service.saveEvent(event);
			}
		}
		
		return event;
	}
	
	
	@GetMapping(value="/seekUser/{login}", produces="application/json")
	public @ResponseBody User seekUser(@PathVariable("login") String login) {
		User user  = service.seekUser(login);
		
		//Send request to EventoWS
		if (isNull(user)) {
			user = service.seekUserFromAPI(login);
			
			//Save data in the Redis for next requests.
			if (nonNull(user)) {
				service.saveUser(user);
			}
		}
		
		return user;
	}

}
