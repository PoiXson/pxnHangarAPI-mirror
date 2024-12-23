package com.poixson.hangarapi.readonly.api.v1.versions;

import java.util.List;


public record Hangar_Version_Channel(String name, String createdAt,
		String color, List<String> flags) {

}
