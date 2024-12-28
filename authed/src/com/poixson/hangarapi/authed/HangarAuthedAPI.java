package com.poixson.hangarapi.authed;

import static com.poixson.utils.GsonProvider.GSON;
import static com.poixson.utils.Utils.IsEmpty;

import java.net.http.HttpRequest;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.poixson.hangarapi.readonly.HangarAPI;
import com.poixson.utils.HttpMethod;


public class HangarAuthedAPI extends HangarAPI {

	protected final String api_key;

	protected DecodedJWT jwt = null;



	public HangarAuthedAPI(final String api_key) {
		super();
		this.api_key = api_key;
	}



	@Override
	protected void buildGET(final HttpRequest.Builder builder) {
		super.buildGET(builder);
		if (!IsEmpty(this.api_key)) {
			if (this.jwt == null || this.isExpiredJWT()) {
				final DecodedJWT temp_jwt = this.newJWT().join();
				this.setJWT(temp_jwt);
			}
			builder.header("Authorization", this.jwt.getToken());
		}
	}



	// -------------------------------------------------------------------------------
	// jwt - java web token



	public void setJWT(final DecodedJWT jwt) {
		this.jwt = jwt;
	}



	public CompletableFuture<DecodedJWT> newJWT() {
		return this.sendRequest("authenticate", HttpMethod.POST, "apiKey", this.api_key)
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



	// -------------------------------------------------------------------------------



//TODO



}
