package com.poixson.hangarapi.readonly.api.v1;

import java.util.List;


public record Hangar_User(String name, String createdAt,
		String tagline, List<Integer> roles, int projectCount,
		boolean locked, List<Hangar_NameHistory> nameHistory, String avatarUrl, boolean isOrganization) {

}
