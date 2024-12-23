package com.poixson.hangarapi.authed;

import static com.poixson.utils.GsonProvider.GSON;
import static com.poixson.utils.Utils.IsEmpty;

import java.net.http.HttpRequest;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.poixson.utils.HttpMethod;


public class HangarAPI extends com.poixson.hangarapi.readonly.HangarAPI {

	protected final String api_key;

	protected DecodedJWT jwt = null;



	public HangarAPI(final String api_key) {
		super();
		this.api_key = api_key;
	}
	public HangarAPI(final String api_key, final String api_url) {
		super(api_url);
		this.api_key = api_key;
	}



	// -------------------------------------------------------------------------------
	// jwt - java web token



	public void setJWT(final DecodedJWT jwt) {
		this.jwt = jwt;
	}



	public CompletableFuture<DecodedJWT> newJWT() {
		return this.sendRequest("/authenticate?apiKey="+this.api_key, HttpMethod.POST)
			.thenApply(response -> {
				final JsonObject obj = GSON().fromJson(response.body(), JsonObject.class);
				return JWT.decode(obj.get("token").getAsString());
			});
	}

	public boolean isExpiredJWT() {
		final DecodedJWT jwt = this.jwt;
		if (jwt == null) return true;
		final Date expires = jwt.getExpiresAt();
		return (expires==null ? true : expires.before(new Date()));
	}



	@Override
	public HttpRequest.Builder getRequestBuilder(final HttpMethod method) {
		final HttpRequest.Builder builder = HttpRequest.newBuilder()
			.header("User-Agent", API_AGENT);
		SWITCH_METHOD:
		switch (method) {
		case GET:
			if (!IsEmpty(this.api_key)) {
				if (this.jwt == null || this.isExpiredJWT()) {
					final DecodedJWT temp_jwt = this.newJWT().join();
					this.setJWT(temp_jwt);
				}
				builder.header("Authorization", this.jwt.getToken());
			}
			break SWITCH_METHOD;
		case POST: builder.POST(HttpRequest.BodyPublishers.noBody()); break SWITCH_METHOD;
		default: throw new IllegalArgumentException("Invalid method: "+method.toString());
		}
		return builder;
	}



	// -------------------------------------------------------------------------------



//TODO



}
