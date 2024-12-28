package com.poixson.hangarapi.readonly;

import static com.poixson.tools.xDebug.Debug;
import static com.poixson.tools.xURL.XURL;
import static com.poixson.utils.ArrayUtils.ssArrayToMap;
import static com.poixson.utils.Utils.IsEmpty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.poixson.utils.HttpMethod;


public abstract class HangarAPI {
	public static final String DEFAULT_API_URL   = "https://hangar.papermc.io/api/v1";
	public static final String DEFAULT_API_AGENT = "pxnHangarAPI";

	public String api_url = null;
	public String agent   = null;



	public HangarAPI() {
	}



	// -------------------------------------------------------------------------------
	// http client / request



	// http client
	public HttpClient getClientHTTP() {
		return (HttpClient.newBuilder())
			.version(HttpClient.Version.HTTP_1_1)
			.build();
	}



	// request builder
	public HttpRequest.Builder getRequestBuilder(final HttpMethod method) {
		final HttpRequest.Builder builder = HttpRequest.newBuilder()
			.header("User-Agent", this.getAgent());
		SWITCH_METHOD:
		switch (method) {
		case GET:  this.buildGET (builder); break SWITCH_METHOD;
		case POST: this.buildPOST(builder); break SWITCH_METHOD;
		default: throw new IllegalArgumentException("Invalid method: "+method.toString());
		}
		return builder;
	}

	protected void buildGET(final HttpRequest.Builder builder) {
	}
	protected void buildPOST(final HttpRequest.Builder builder) {
		builder.POST(HttpRequest.BodyPublishers.noBody());
	}



	// send request
	public CompletableFuture<HttpResponse<String>> sendRequest(final String path, final String...params) {
		return this.sendRequest(path, ssArrayToMap(params));
	}
	public CompletableFuture<HttpResponse<String>> sendRequest(final String path, final Map<String, String> params) {
		return this.sendRequest(path, HttpMethod.GET, params);
	}
	public CompletableFuture<HttpResponse<String>> sendRequest(final String path, final HttpMethod method, final String...params) {
		return this.sendRequest(path, method, ssArrayToMap(params));
	}
	public CompletableFuture<HttpResponse<String>> sendRequest(final String path, final HttpMethod method, final Map<String, String> params) {
		final String url = XURL(this.getApiUrl())
			.path(path)
			.setParams(params)
			.build();
		if (Debug())
			System.out.println("API REQUEST: "+url);
		final HttpRequest request = this.getRequestBuilder(method).uri(URI.create(url)).build();
		return this.getClientHTTP()
			.sendAsync(request, HttpResponse.BodyHandlers.ofString());
	}



	// -------------------------------------------------------------------------------
	// parameters



	public HangarAPI api_url(final String url) {
		this.api_url = url;
		return this;
	}
	public String getApiUrl() {
		return (IsEmpty(this.api_url) ? DEFAULT_API_URL : this.api_url);
	}



	public HangarAPI agent(final String agent) {
		this.agent = agent;
		return this;
	}
	public String getAgent() {
		return (IsEmpty(this.agent) ? DEFAULT_API_AGENT : this.agent);
	}



}
