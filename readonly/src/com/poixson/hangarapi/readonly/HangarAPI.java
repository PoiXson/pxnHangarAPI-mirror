package com.poixson.hangarapi.readonly;

import static com.poixson.utils.GsonProvider.GSON;
import static com.poixson.utils.Utils.IsEmpty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import com.poixson.hangarapi.readonly.api.v1.Hangar_Namespace;
import com.poixson.hangarapi.readonly.api.v1.Hangar_Platform;
import com.poixson.hangarapi.readonly.api.v1.Hangar_User;
import com.poixson.hangarapi.readonly.api.v1.projects.Hangar_Project;
import com.poixson.hangarapi.readonly.api.v1.projects.Hangar_Projects;
import com.poixson.hangarapi.readonly.api.v1.versions.Hangar_Version;
import com.poixson.hangarapi.readonly.api.v1.versions.Hangar_Versions;
import com.poixson.utils.HttpMethod;


public class HangarAPI {

	public static final String API_URL = "https://hangar.papermc.io/api/v1";
	public static final String API_AGENT = "pxnHangarAPI";

	public static final int DEFAULT_LIMIT = 25;

	protected final String api_url;



	public HangarAPI() {
		this(null);
	}
	public HangarAPI(final String api_url) {
		this.api_url = (IsEmpty(api_url) ? API_URL : api_url);
	}



	// -------------------------------------------------------------------------------



	public HttpClient getHTTP() {
		return (HttpClient.newBuilder())
			.version(HttpClient.Version.HTTP_1_1)
			.build();
	}



	public HttpRequest.Builder getRequestBuilder(final HttpMethod method) {
		final HttpRequest.Builder builder = HttpRequest.newBuilder()
			.header("User-Agent", API_AGENT);
		SWITCH_METHOD:
		switch (method) {
		case GET:  break SWITCH_METHOD;
		case POST: throw new UnsupportedOperationException("POST not supported");
		default: throw new IllegalArgumentException("Invalid method: "+method.toString());
		}
		return builder;
	}



	public CompletableFuture<HttpResponse<String>> sendRequest(final String path) {
		return this.sendRequest(path, HttpMethod.GET);
	}
	public CompletableFuture<HttpResponse<String>> sendRequest(final String path, final HttpMethod method) {
		final HttpRequest request =
			this.getRequestBuilder(method)
				.uri(URI.create(API_URL + path))
				.build();
		return this.getHTTP()
			.sendAsync(request, HttpResponse.BodyHandlers.ofString());
	}



	// -------------------------------------------------------------------------------



	public CompletableFuture<Integer> getTotalProjects() {
		return this.getProjects(1, 0)
			.thenApply( response -> response.pagination().count() );
	}



	// project
	public CompletableFuture<Hangar_Project> getProject(final String slug) {
		return this.sendRequest("/projects/"+slug)
			.thenApply(response ->
				GSON().fromJson(response.body(), Hangar_Project.class)
			);
	}

	// all projects
	public CompletableFuture<Hangar_Projects> getProjects() {
		return this.queryProjects(null, null, false, Hangar_Platform.PAPER, -1, -1);
	}
	public CompletableFuture<Hangar_Projects> getProjects(
			final int limit, final int offset) {
		return this.queryProjects(null, null, false, Hangar_Platform.PAPER, limit, offset);
	}

	// search projects
	public CompletableFuture<Hangar_Projects> searchProjects(
			final String search, final boolean exact) {
		return this.queryProjects(null, search, exact, Hangar_Platform.PAPER, -1, -1);
	}
	public CompletableFuture<Hangar_Projects> searchProjects(
			final String search, final int limit, final int offset) {
		return this.queryProjects(null, search, true, Hangar_Platform.PAPER, limit, offset);
	}
	public CompletableFuture<Hangar_Projects> searchProjects(
			final String search, final boolean exact,
			final int limit, final int offset) {
		return this.queryProjects(null, search, true, Hangar_Platform.PAPER, limit, offset);
	}

	// user projects
	public CompletableFuture<Hangar_Projects> getUserProjects(
			final String user) {
		return this.queryProjects(user, null, true, Hangar_Platform.PAPER, -1, -1);
	}
	public CompletableFuture<Hangar_Projects> getUserProjects(
			final String user, final int limit, final int offset) {
		return this.queryProjects(user, null, true, Hangar_Platform.PAPER, limit, offset);
	}

	public CompletableFuture<Hangar_Projects> getUserProjects(
			final Hangar_User user) {
		return this.getUserProjects(user.name());
	}
	public CompletableFuture<Hangar_Projects> getUserProjects(
			final Hangar_User user, final int limit, final int offset) {
		return this.getUserProjects(user.name(), limit, offset);
	}

	public CompletableFuture<Hangar_Projects> queryProjects(
			final String user, final String search, final boolean exact,
			final Hangar_Platform platform, final int limit, final int offset) {
		if (limit > DEFAULT_LIMIT)
			return this.queryProjects(user, search, exact, platform, DEFAULT_LIMIT, offset);
		final StringBuilder url = new StringBuilder();
		url.append("/projects?prioritizeExactMatch=").append(exact ? "1" : "0");
		if (platform != null) url.append("&platform").append(platform.toString());
		if (!IsEmpty(user  )) url.append("&owner=").append(user  );
		if (!IsEmpty(search)) url.append("&q="    ).append(search);
		if (limit  >  0) url.append("&limit=" ).append(limit );
		if (offset >= 0) url.append("&offset=").append(offset);
		return this.sendRequest(url.toString())
			.thenApply(response ->
				GSON().fromJson(response.body(), Hangar_Projects.class)
			);
	}



	// project versions
	public CompletableFuture<Hangar_Versions> getVersions(final String slug) {
		return this.sendRequest(String.format("/projects/%s/versions", slug))
			.thenApply(response ->
				GSON().fromJson(response.body(), Hangar_Versions.class)
			);
	}
	public CompletableFuture<Hangar_Versions> getVersions(final Hangar_Project project) {
		final Hangar_Namespace namespace = project.namespace();
		return this.getVersions(namespace.toString());
	}



	// version info
	public CompletableFuture<Hangar_Version> getVersion(final String slug, final String version) {
		return this.sendRequest(String.format("/projects/%s/versions/%s", slug, version))
			.thenApply(response ->
				GSON().fromJson(response.body(), Hangar_Version.class)
			);
	}
	public CompletableFuture<Hangar_Version> getVersion(final Hangar_Project project, final String version) {
		final Hangar_Namespace namespace = project.namespace();
		return this.getVersion(namespace.toString(), version);
	}



	// get user
	public CompletableFuture<Hangar_User> getUser(final String user) {
		return this.sendRequest("/users/"+user)
			.thenApply(response ->
				GSON().fromJson(response.body(), Hangar_User.class)
			);
	}



}
