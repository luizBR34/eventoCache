package com.eventoCache.web;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		List<Event> eventList = service.listEvents(username);
		
		//Send request to EventoWS
		if (isNull(eventList) || eventList.isEmpty()) {
			eventList = service.listEventsFromAPI(username);
			
			//Save data in the Redis for next requests.
			if (nonNull(eventList) & (!eventList.isEmpty())) {
				service.saveEvents(username, eventList);
			}
		}
		
		
		return eventList;
	}
	
	
	@GetMapping(value="/seekEvent", produces="application/json")
	public @ResponseBody Event seekEvent(@RequestParam("username") String username, @RequestParam("code") long code) {
		Event event = service.searchEvent(username, code);
		
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
	
	
	@PostMapping(value="/saveGuest", produces="application/json")
	public void saveGuest(@RequestParam("username") String username, 
						  @RequestParam("eventCode") long eventCode, 
						  @RequestBody @Valid Guest guest) {
		
		service.saveGuest(username, eventCode, guest);
		service.saveGuestIntoAPI(eventCode, guest);
	}



	@DeleteMapping("/deleteEvent")
	public void deleteEvent(@RequestParam("username") String username,
							@RequestParam("code") long code) {

		service.deleteEvent(username, code);
		service.deleteEventFromAPI(code);
	}


	
	@PostMapping(value="/saveSession", produces="application/json")
	public void saveSession(@RequestBody @Valid User user) {
		session.setAttribute("loggedUser", user);
		sessionData.setLoggedUser(user);
	}
	
	
	@GetMapping(value="/getSession", produces="application/json")
	public @ResponseBody User getSession() {

		User user = sessionData.getLoggedUser();
		
		if (nonNull(user)) {
			
			user = service.seekUser(user.getUserName());
			
			if (isNull(user)) {
				user = service.seekUserFromAPI(user.getUserName());
			} 
		}
		
		if (isNull(user)) {
			user = User.builder().firstName("Visitor").build();
		}
		
		return user;
	}


	@DeleteMapping("/deleteSession")
	public void invalidateSession() {

		this.session.invalidate();
		this.sessionData.setLoggedUser(null);
	}
}
