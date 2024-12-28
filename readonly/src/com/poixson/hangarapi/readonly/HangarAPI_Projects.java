package com.poixson.hangarapi.readonly;

import static com.poixson.tools.xURL.XURL;
import static com.poixson.utils.GsonProvider.GSON;
import static com.poixson.utils.Utils.IsEmpty;

import java.util.concurrent.CompletableFuture;

import com.poixson.hangarapi.readonly.api.v1.Hangar_Platform;
import com.poixson.hangarapi.readonly.api.v1.projects.Hangar_Projects;
import com.poixson.tools.xURL;


public class HangarAPI_Projects extends HangarAPI {

	public String owner  = "";
	public String slug   = "";
	public int    limit  = -1;
	public int    offset = -1;
	public boolean exact = true;
	public Hangar_Platform platform = null;



	public HangarAPI_Projects() {
		super();
	}



	// api query
	public CompletableFuture<Hangar_Projects> query() {
		final xURL url = XURL()
			.path("projects")
			.param("prioritizeExactMatch", (this.exact ? "1" : "0"));
		if (this.platform != null) url.param("platform", this.platform.toString());
		if (!IsEmpty(this.owner) ) url.param("owner",    this.owner );
		if (!IsEmpty(this.slug)  ) url.param("query",    this.slug  );
		if (this.limit  >  0     ) url.param("limit",    this.limit );
		if (this.offset >= 0     ) url.param("offset",   this.offset);
		return this.sendRequest(url.build())
			.thenApply(response ->
				GSON().fromJson(response.body(), Hangar_Projects.class)
			);
	}



	// -------------------------------------------------------------------------------
	// query parameters



	// owner
	public HangarAPI_Projects owner(final String owner) {
		this.owner = owner;
		return this;
	}
	public String getOwner() {
		return this.owner;
	}



	// slug
	public HangarAPI_Projects slug(final String slug) {
		this.slug = slug;
		return this;
	}
	public String getSlug() {
		return this.slug;
	}



	// limit
	public HangarAPI_Projects limit(final int limit) {
		this.limit = limit;
		return this;
	}
	public int getLimit() {
		return this.limit;
	}



	// offset
	public HangarAPI_Projects offset(final int offset) {
		this.offset = offset;
		return this;
	}
	public int getOffset() {
		return this.offset;
	}



	// exact
	public HangarAPI_Projects exact(final boolean exact) {
		this.exact = exact;
		return this;
	}
	public boolean getExact() {
		return this.exact;
	}



	// platform
	public HangarAPI_Projects platform(final String platform) {
		return this.platform(Hangar_Platform.valueOf(platform));
	}
	public HangarAPI_Projects platform(final Hangar_Platform platform) {
		this.platform = platform;
		return this;
	}
	public Hangar_Platform getPlatform() {
		return this.platform;
	}



}
