package com.poixson.hangarapi.readonly.api.v1;

import java.util.List;


public record Hangar_Channel(String name,
		String createdAt, String color, List<String> fags) {

}
