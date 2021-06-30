package com.eventoCache.configs;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Component
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS, value=WebApplicationContext.SCOPE_APPLICATION)
public class SessionData implements Serializable {
	
	private static final long serialVersionUID = 6008565606165835651L;
	
	private String userName;
}
