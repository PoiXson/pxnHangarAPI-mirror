package com.poixson.hangarapi.readonly.api.v1;

import java.util.List;


public record Hangar_Link(int id, String type, String title, List<Hangar_SubLink> links) {

}
