package com.poixson.hangarapi.readonly.api.v1.versions;

import java.util.Map;


public record Hangar_Version_Stats(int totalDownloads, Map<String, Integer> platformDownloads) {

}
