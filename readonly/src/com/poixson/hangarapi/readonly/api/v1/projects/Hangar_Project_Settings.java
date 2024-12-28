package com.poixson.hangarapi.readonly.api.v1.projects;

import java.util.List;

import com.poixson.hangarapi.readonly.api.v1.Hangar_Donation;
import com.poixson.hangarapi.readonly.api.v1.Hangar_License;
import com.poixson.hangarapi.readonly.api.v1.Hangar_Link;


public record Hangar_Project_Settings(List<Hangar_Link> links, List<String> tags,
		Hangar_License license, List<String> keywords,
		String sponsors, Hangar_Donation donation) {

}
