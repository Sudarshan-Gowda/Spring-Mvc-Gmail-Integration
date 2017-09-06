package com.star.sud.gmail;

/*@ Sudarshan*/
public interface GmailService {

	public String buildLoginUrl();

	public String getUserInfoJson(String authCode);

}
