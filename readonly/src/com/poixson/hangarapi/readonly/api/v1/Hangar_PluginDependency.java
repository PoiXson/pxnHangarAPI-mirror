package com.poixson.hangarapi.readonly.api.v1;


public record Hangar_PluginDependency(String name, String externalUrl,
		Hangar_Platform platform, boolean required) {

}
