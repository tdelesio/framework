package com.delesio.web.cas;

import org.springframework.security.core.Authentication;

public interface ICASAuthenicationService {

	public Authentication getAuthenicationById(String id);
}
