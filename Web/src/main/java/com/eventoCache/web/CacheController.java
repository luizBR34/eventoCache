package com.eventoCache.web;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.User;
import com.eventoCache.configs.SessionData;
import com.eventoCache.services.CacheService;

@RestController
@RequestMapping("/eventoCache")
public class CacheController {
	
	@Autowired
	private CacheService service;
	
	@Autowired 
	private HttpSession session;
	
	@Autowired
	private SessionData sessionData;
	

	
	
	@GetMapping(value="/eventList/{username}", produces="application/json")
	public @ResponseBody List<Event> listEvents(@PathVariable("username") String username) {
		List<Event> eventList = service.listEvents();
		
		//Send request to EventoWS
		if (isNull(eventList) || eventList.isEmpty()) {
			eventList = service.listEventsFromAPI(username);
			
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
	
	
	@PostMapping(value="/saveEvent", produces="application/json")
	public void saveEvent(@RequestBody @Valid Event event) {
		
		service.saveEvent(event);
		service.saveEventIntoAPI(event);
	}
	
	
	@PostMapping(value="/saveGuest/{eventCode}", produces="application/json")
	public void saveGuest(@PathVariable("eventCode") long eventCode, @RequestBody @Valid Guest guest) {
		
		service.saveGuest(eventCode, guest);
		service.saveGuestIntoAPI(eventCode, guest);
	}
	
	
	@PostMapping(value="/saveSession", produces="application/json")
	public void saveSession(@RequestBody @Valid User user) {
		session.setAttribute("loggedUser", user.getUserName());
		sessionData.setUserName(user.getUserName());
	}
	
	
	@GetMapping(value="/getSession", produces="application/json")
	public @ResponseBody User getSession() {
		
		User user = null;
		String userName = sessionData.getUserName();
		
		if (nonNull(userName)) {
			
			user = service.seekUser(userName);
			
			if (isNull(user)) {
				user = service.seekUserFromAPI(userName);
			} 
		}
		
		if (isNull(user)) {
			user = User.builder().firstName("Visitor").build();
		}
		
		return user;
	}
	
}
