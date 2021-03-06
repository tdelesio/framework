package com.delesio.web.facebook;

/**
 * Holds all the various Facebook permissions that you can request.
 * Handles building a permissions request. Requesting too many
 * permissions will probably cause a user to avoid your app so request as
 * few permissions as possible.
 * 
 * A full description of what each permission does can be found here:
 * http://developers.facebook.com/docs/authentication/permissions/
 * 
 * @author Andres Alonso
 */
public class FacebookPermissions {
 
	// User Permissions
	public static final String USER_ABOUT_ME = "user_about_me";
	public static final String USER_ACTIVITIES = "user_activities";
	public static final String USER_BIRTHDAY = "user_birthday";
	public static final String USER_EDUCATION_HISTORY = "user_education_history"; 	
	public static final String USER_EVENTS = "user_events"; 	
	public static final String USER_GROUPS = "user_groups";	
	public static final String USER_HOMETOWN = "user_hometown"; 	
	public static final String USER_INTERESTS = "user_interests"; 	
	public static final String USER_LIKES = "user_likes"; 	
	public static final String USER_LOCATION = "user_location"; 	
	public static final String USER_NOTES = "user_notes"; 	
	public static final String USER_ONLINE_PRESENCE = "user_online_presence"; 	
	public static final String USER_PHOTO_VIDEO_TAGS = "user_photo_video_tags"; 	
	public static final String USER_PHOTOS = "user_photos";
	public static final String USER_RELATIONSHIPS = "user_relationships"; 	
	public static final String USER_RELATIONSHIP_DETAILS = "user_relationship_details"; 	
	public static final String USER_RELIGION_POLITICS = "user_religion_politics";
	public static final String USER_STATUS = "user_status";
	public static final String USER_VIDEOS = "user_videos"; 	
	public static final String USER_WEBSITE = "user_website"; 	
	public static final String USER_WORK_HISTORY = "user_work_history";
	
	public static final String EMAIL = "email";
	public static final String READ_FRIENDLISTS = "read_friendlists";
	public static final String READ_INSIGHTS = "read_insights";
	public static final String READ_MAILBOX = "read_mailbox";
	public static final String READ_REQUESTS = "read_requests";
	public static final String READ_STREAM = "read_stream";
	public static final String XMPP_LOGIN = "xmpp_login";
	public static final String ADS_MANAGEMENT = "ads_management";
	public static final String USER_CHECKINS = "user_checkins";
	
	// Friends Permissions 
	public static final String FRIENDS_ABOUT_ME = "friends_about_me";
	public static final String FRIENDS_ACTIVITIES = "friends_activities";
	public static final String FRIENDS_BIRTHDAY = "friends_birthday";
	public static final String FRIENDS_EDUCATION_HISTORY = "friends_education_history"; 	
	public static final String FRIENDS_EVENTS = "friends_events"; 	
	public static final String FRIENDS_GROUPS = "friends_groups";	
	public static final String FRIENDS_HOMETOWN = "friends_hometown"; 	
	public static final String FRIENDS_INTERESTS = "friends_interests"; 	
	public static final String FRIENDS_LIKES = "friends_likes"; 	
	public static final String FRIENDS_LOCATION = "friends_location"; 	
	public static final String FRIENDS_NOTES = "friends_notes"; 	
	public static final String FRIENDS_ONLINE_PRESENCE = "friends_online_presence"; 	
	public static final String FRIENDS_PHOTO_VIDEO_TAGS = "friends_photo_video_tags"; 	
	public static final String FRIENDS_PHOTOS = "friends_photos";
	public static final String FRIENDS_RELATIONSHIPS = "friends_relationships"; 	
	public static final String FRIENDS_RELATIONSHIP_DETAILS = "friends_relationship_details"; 	
	public static final String FRIENDS_RELIGION_POLITICS = "friends_religion_politics";
	public static final String FRIENDS_STATUS = "friends_status";
	public static final String FRIENDS_VIDEOS = "friends_videos"; 	
	public static final String FRIENDS_WEBSITE = "friends_website"; 	
	public static final String FRIENDS_WORK_HISTORY = "friends_work_history";
	public static final String FRIENDS_CHECKINS = "friends_checkins";
	
	public static final String MANAGE_FRIENDLISTS = "manage_friendlists";
	
	// Publishing Permissions
	public static final String PUBLISH_STREAM = "publish_stream";
	public static final String CREATE_EVENT = "create_event";
	public static final String RSVP_EVENT = "rsvp_event";
	public static final String SMS = "sms";
	public static final String OFFLINE_ACCESS = "offline_access";
	public static final String PUBLISH_CHECKINS = "publish_checkins";
	
	// Page Permissions
	public static final String MANAGE_PAGES = "manage_pages";
	
}
