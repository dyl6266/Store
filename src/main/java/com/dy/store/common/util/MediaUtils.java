package com.dy.store.common.util;

import java.util.HashMap;

import org.springframework.http.MediaType;

public class MediaUtils {

	private static HashMap<String, MediaType> mediaMap;

	static {
		mediaMap = new HashMap<>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
	}

	public static MediaType getMediaType(String mediaType) {
		return mediaMap.get(mediaType.toUpperCase());
	}

}
