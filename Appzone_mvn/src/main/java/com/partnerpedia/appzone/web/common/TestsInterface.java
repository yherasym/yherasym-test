package com.partnerpedia.appzone.web.common;

public interface TestsInterface {
	//wait timeout maximum in seconds
	public static final Long TIMEOUT_MAX = 20L;
	
	//
	public static final String ENVIRONMENT = System.getProperty("environment") == null ? "http://my-qa.enterpriseappzone.com" : System.getProperty("environment");
	public static final String BROWSER = System.getProperty("browser") == null ? "FireFox" : System.getProperty("browser");
	public static final String SCREEN_RESOLUTION = System.getProperty("screenResolution") == null ? "1024x768" : System.getProperty("screenResolution");
	//
	public static final String RESOURCE_PATH = System.getProperty("user.dir") + "\\Resources\\";
	//
	public static final String STORE_ID = System.getProperty("store") == null ? "test-store-01" : System.getProperty("store");
	
	public static final String BASE_URL = ENVIRONMENT + "/" + STORE_ID +"/"; 
	//
	public static final String LOGIN_PATH = "account/new";
	public static final String LANDING_STORE_ADMIN_PATH = "manage/applications";
	public static final String LANDING_STORE_USER_PATH = "employee/products";
	public static final String LANDING_MP_ADMIN_PATH = "manage/applications";
	
	public static final String TITLE_STORE_ADMIN_PAGE = "Manage Applications";
	public static final String TITLE_STORE_USER_PAGE = "My Applications";
	public static final String TITLE_MP_ADMIN_PAGE = "Manage Applications";
	
	public static final String STORE_CONFIGURATION_PAGE = "manage/store/edit";
	
	
	//...
}
