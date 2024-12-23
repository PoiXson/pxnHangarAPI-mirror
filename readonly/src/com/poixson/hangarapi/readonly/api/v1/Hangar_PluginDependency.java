package com.poixson.hangarapi.readonly.api.v1;


public record Hangar_PluginDependency(String name, Hangar_Namespace namespace,
		boolean required, String externalUrl, Hangar_Platform platform) {

}
