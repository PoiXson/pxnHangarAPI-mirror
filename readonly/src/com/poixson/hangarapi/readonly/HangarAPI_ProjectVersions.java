package com.poixson.hangarapi.readonly;

import static com.poixson.tools.xURL.XURL;
import static com.poixson.utils.GsonProvider.GSON;
import static com.poixson.utils.Utils.IsEmpty;

import java.util.concurrent.CompletableFuture;

import com.poixson.hangarapi.readonly.api.v1.Hangar_Platform;
import com.poixson.hangarapi.readonly.api.v1.versions.Hangar_Versions;
import com.poixson.tools.xURL;


public class HangarAPI_ProjectVersions extends HangarAPI {

	public static final boolean DEFAULT_INCLUDE_HIDDEN = true;

	public String slug    = "";
	public String channel = "";
	public int    limit   = -1;
	public int    offset  = -1;
	public boolean inc_hidden = true;
	public Hangar_Platform platform = null;



	public HangarAPI_ProjectVersions() {
		super();
	}



	// api query
	public CompletableFuture<Hangar_Versions> query() {
		final xURL url = XURL()
			.path("projects")
			.path(this.slug)
			.path("versions")
			.param("includeHiddenChannels", (this.inc_hidden ? "1" : "0"));
		if (!IsEmpty(this.channel)) url.param("channel",  this.channel);
		if (this.platform != null ) url.param("platform", this.platform.toString());
		if (this.limit  >  0      ) url.param("limit",    this.limit );
		if (this.offset >= 0      ) url.param("offset",   this.offset);
		return this.sendRequest(url.build())
			.thenApply(response ->
				GSON().fromJson(response.body(), Hangar_Versions.class)
			);
	}



	// -------------------------------------------------------------------------------
	// query parameters



	// slug
	public HangarAPI_ProjectVersions slug(final String slug) {
		this.slug = slug;
		return this;
	}
	public String getSlug() {
		return this.slug;
	}



	// channel
	public HangarAPI_ProjectVersions channel(final String channel) {
		this.channel = channel;
		return this;
	}
	public String getChannel() {
		return this.channel;
	}



	// limit
	public HangarAPI_ProjectVersions limit(final int limit) {
		this.limit = limit;
		return this;
	}
	public int getLimit() {
		return this.limit;
	}



	// offset
	public HangarAPI_ProjectVersions offset(final int offset) {
		this.offset = offset;
		return this;
	}
	public int getOffset() {
		return this.offset;
	}



	// include hidden
	public HangarAPI_ProjectVersions incHidden(final boolean incHidden) {
		this.inc_hidden = incHidden;
		return this;
	}
	public boolean getIncludeHidden() {
		return this.inc_hidden;
	}



	// platform
	public HangarAPI_ProjectVersions platform(final String platform) {
		return this.platform(Hangar_Platform.valueOf(platform));
	}
	public HangarAPI_ProjectVersions platform(final Hangar_Platform platform) {
		this.platform = platform;
		return this;
	}
	public Hangar_Platform getPlatform() {
		return this.platform;
	}



}
