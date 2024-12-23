package com.poixson.hangarapi.readonly.api.v1.projects;

import com.poixson.hangarapi.readonly.api.v1.Hangar_Namespace;
import com.poixson.hangarapi.readonly.api.v1.Hangar_UserActions;


public record Hangar_Project(String name, String description, Hangar_Namespace namespace,
		String visibility, String createdAt, String lastUpdated,
		String category, String avatarUrl, Hangar_Project_Stats stats,
		Hangar_UserActions userActions, Hangar_Settings settings) {

}
