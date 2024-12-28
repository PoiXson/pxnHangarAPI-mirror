package com.poixson.hangarapi.readonly.api.v1;


public record Hangar_Namespace(String owner, String slug) {



	@Override
	public String toString() {
		return String.format("%s/%s", this.owner, this.slug);
	}



}
