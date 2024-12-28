package com.poixson.hangarapi.readonly.api.v1.projects;

import java.util.List;

import com.poixson.hangarapi.readonly.api.v1.Hangar_Pagination;


public record Hangar_Projects(Hangar_Pagination pagination, List<Hangar_Project> result) {

}
