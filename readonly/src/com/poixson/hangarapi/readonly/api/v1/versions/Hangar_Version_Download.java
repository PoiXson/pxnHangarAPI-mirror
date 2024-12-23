package com.poixson.hangarapi.readonly.api.v1.versions;

import com.poixson.hangarapi.readonly.api.v1.Hangar_FileInfo;


public record Hangar_Version_Download(Hangar_FileInfo fileInfo,
		String externalUrl, String downloadUrl) {

}
