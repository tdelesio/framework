package com.delesio.model;

import org.joda.time.DateTime;

import com.delesio.exception.ValidationException;

public interface IGenericSynchroinzedLifeCycle
{

	public void validate() throws ValidationException;
	public String getEmailForAuthorization();
	public void setModifiedDateZone(DateTime dateTime);
	public String getId();
	public long getModifiedDate();
	public DateTime getClientUpdateTimeStamp();
	public void markForDeletion();
}
