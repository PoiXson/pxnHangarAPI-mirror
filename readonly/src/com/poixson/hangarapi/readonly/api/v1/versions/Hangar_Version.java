package com.poixson.hangarapi.readonly.api.v1.versions;

import java.util.List;
import java.util.Map;

import com.poixson.hangarapi.readonly.api.v1.Hangar_Platform;
import com.poixson.hangarapi.readonly.api.v1.Hangar_PluginDependency;


public record Hangar_Version(String name, String description, String author,
		String createdAt, String visibility, String pinnedStatus,
		Hangar_Version_Channel channel, Hangar_Version_Stats stats, String reviewState,
		Map<String, Hangar_Version_Download> downloads, Map<Hangar_Platform,
		List<Hangar_PluginDependency>> pluginDependencies,
		Map<Hangar_Platform, List<String>> platformDependencies,
		Map<Hangar_Platform, String> platformDependenciesFormatted) {

}
