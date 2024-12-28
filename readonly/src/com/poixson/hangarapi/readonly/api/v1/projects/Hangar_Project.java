package com.poixson.hangarapi.readonly.api.v1.projects;

import com.poixson.hangarapi.readonly.api.v1.Hangar_Namespace;
import com.poixson.hangarapi.readonly.api.v1.Hangar_UserActions;


public record Hangar_Project(String name, Hangar_Namespace namespace, String description,
		Hangar_Project_Settings settings, String category, String avatarUrl,
		String visibility, String createdAt, String lastUpdated,
		Hangar_Project_Stats stats, Hangar_UserActions userActions) {

}
