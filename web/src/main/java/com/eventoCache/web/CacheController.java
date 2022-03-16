package com.eventoCache.web;

import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.User;
import com.eventoCache.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/eventoCache")
public class CacheController {
	
	@Autowired
	private CacheService service;
	
	@Autowired 
	private HttpSession session;


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
	

	@DeleteMapping("/deleteEvent")
	public void deleteEvent(@RequestParam("username") String username,
							@RequestParam("code") long code) {

		service.deleteEvent(username, code);
		service.deleteEventFromAPI(code);
	}




	@GetMapping(value="/guestList", produces="application/json")
	public @ResponseBody List<Guest> guestList(@RequestParam("username") String username,
											   @RequestParam("eventCode") long eventCode) {

		List<Guest> guestList = service.guestsList(username, eventCode);
		return (!guestList.isEmpty()) ? guestList : service.guestsListFromAPI(eventCode);
	}


	@PostMapping(value="/saveGuest", produces="application/json")
	public void saveGuest(@RequestParam("username") String username,
						  @RequestParam("eventCode") long eventCode,
						  @RequestBody @Valid Guest guest) {

		service.saveGuest(username, eventCode, guest);
		service.saveGuestIntoAPI(eventCode, guest);
	}
}
