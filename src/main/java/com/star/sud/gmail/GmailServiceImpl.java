package com.star.sud.gmail;

/*@ Sudarshan*/
import java.security.SecureRandom;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

@Service("gmailService")
public class GmailServiceImpl implements GmailService {

	private final GoogleAuthorizationCodeFlow flow;

	private String stateToken;

	private static final String CLIENT_ID = ""; //change accordingly
	private static final String CLIENT_SECRET = "";//change accordingly

	private static final Iterable<String> SCOPE = Arrays
			.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email"
					.split(";"));
	private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	public GmailServiceImpl() {
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE)
				.build();

		generateStateToken();
	}

	public void generateStateToken() {
		SecureRandom sr1 = new SecureRandom();

		stateToken = "google;" + sr1.nextInt();
	}

	public String buildLoginUrl() {

		String genieRestUrl = "http://localhost:8085/gmail-integration/google/result";

		final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();

		GoogleAuthorizationCodeRequestUrl setRedirectUri = url.setRedirectUri(genieRestUrl);
		GoogleAuthorizationCodeRequestUrl setState = setRedirectUri.setState(stateToken);

		String build = setState.build();
		return build;
	}

	public String getStateToken() {
		return stateToken;
	}

	public String getUserInfoJson(String authCode) {
		String jsonIdentity = null;
		try {

			String genieRestUrl = "http://localhost:8085/gmail-integration/google/result";

			final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(genieRestUrl).execute();
			final Credential credential = flow.createAndStoreCredential(response, null);
			final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
			// Make an authenticated request
			final GenericUrl url = new GenericUrl(USER_INFO_URL);
			final HttpRequest request = requestFactory.buildGetRequest(url);
			request.getHeaders().setContentType("application/json");
			jsonIdentity = request.execute().parseAsString();
		} catch (Exception e) {

			System.out.println(e);
		}
		return jsonIdentity;

	}
}
