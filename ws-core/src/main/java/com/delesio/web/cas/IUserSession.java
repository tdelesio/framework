package com.delesio.web.cas;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserSession
{

	public void setLoggedInUser(UserDetails user);
	public UserDetails getLoggedInUser();
}
