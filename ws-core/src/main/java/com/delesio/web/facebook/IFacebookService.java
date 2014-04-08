package com.delesio.web.facebook;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;


public interface IFacebookService {
	
	public UserDetails getUserDetailsFromFacebookCookie(HttpServletRequest request) throws NotEnoughSharedException;
	public FacebookUserDetail  getFaceBookUserDetailsFromFacebookCookie(HttpServletRequest request) throws NotEnoughSharedException;
}
