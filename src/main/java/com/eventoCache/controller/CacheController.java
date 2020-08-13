package com.eventoCache.controller;

import static java.util.Objects.isNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventoCache.model.Event;
import com.eventoCache.service.CacheService;

@RestController
@RequestMapping("/eventoCache")
public class CacheController {
	
	@Autowired
	private CacheService service;
	
	
	@GetMapping(produces="application/json")
	public @ResponseBody List<Event> listEvents() {
		List<Event> eventList = service.listEvents();
		
		//Envia requisiçao para EventoWS
		if (isNull(eventList) || eventList.isEmpty()) {
			
			
			
		}
		
		
		return eventList;
	}
	
	
	@GetMapping(value="/searchEvent/{code}", produces="application/json")
	public @ResponseBody Event searchEvent(@PathVariable("code") long code) {
		Event event = service.searchEvent(code);
		
		//Envia requisiçao para EventoWS
		if (isNull(event)) {
			
			
			
		}
		
		
		return event;
	}
}
