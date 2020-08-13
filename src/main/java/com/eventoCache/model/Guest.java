package com.eventoCache.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Guest implements Serializable {

	private static final long serialVersionUID = 1195850478566890335L;

	private String id;
	
	private String guestName;
	
	private Event event;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((guestName == null) ? 0 : guestName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Guest other = (Guest) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (guestName == null) {
			if (other.guestName != null)
				return false;
		} else if (!guestName.equals(other.guestName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Guest [id=" + id + ", guestName=" + guestName + ", event=" + event + "]";
	}
}
