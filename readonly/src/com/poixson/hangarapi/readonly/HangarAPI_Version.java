package com.poixson.hangarapi.readonly;

import static com.poixson.tools.xURL.XURL;
import static com.poixson.utils.GsonProvider.GSON;

import java.util.concurrent.CompletableFuture;

import com.poixson.hangarapi.readonly.api.v1.versions.Hangar_Version;
import com.poixson.tools.xURL;


public class HangarAPI_Version extends HangarAPI {

	public String slug    = "";
	public String version = "";



	public HangarAPI_Version() {
		super();
	}



	// api query
	public CompletableFuture<Hangar_Version> query() {
		final xURL url = XURL()
			.path("projects")
			.path(this.slug)
			.path("versions")
			.path(this.version);
		return this.sendRequest(url.build())
			.thenApply(response ->
				GSON().fromJson(response.body(), Hangar_Version.class)
			);
	}



	// -------------------------------------------------------------------------------
	// query parameters



	// slug
	public HangarAPI_Version slug(final String slug) {
		this.slug = slug;
		return this;
	}
	public String getSlug() {
		return this.slug;
	}



	// version
	public HangarAPI_Version version(final String version) {
		this.version = version;
		return this;
	}
	public String getVersion() {
		return this.version;
	}



}
