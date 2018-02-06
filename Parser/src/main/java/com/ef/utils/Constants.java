package com.ef.utils;

public final class Constants {
	
	public static final String ACCESSLOG = "accesslog";
	public static final String STARDATE = "startDate";
	public static final String HOURLY = "hourly";
	public static final String DAYLY = "daily";
	public static final String DURATION = "duration";
	public static final String THRESHOLD = "threshold";
	public static final String FORMATDATE = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMATDATEDB = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String REGEXDOT = "\\.";
	public static final String REGEXSPACE = " ";

	public static final String MSGPARAMHOURLY = "Parameter example: --startDate=2017-01-01.00:00:00 --duration=daily --threshold=500";
	public static final String MSGPARAMDAILY = "Parameter example: --startDate=2017-01-01.00:00:00 --duration=daily --threshold=500";
	public static final String MSGPARAMDURATION = "Parameter duration required: must be hourtly or daily";
	public static final String MSGQUERYREQUEST = "too many request";

	private Constants() {
		throw new AssertionError();
	}

}
