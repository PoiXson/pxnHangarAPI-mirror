package com.poixson.hangarapi.readonly.api.v1.versions;

import java.util.List;

import com.poixson.hangarapi.readonly.api.v1.Hangar_Pagination;


public record Hangar_Versions(Hangar_Pagination pagination, List<Hangar_Version> result) {

}
