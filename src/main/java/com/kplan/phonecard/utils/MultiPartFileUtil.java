package com.kplan.phonecard.utils;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public class MultiPartFileUtil {
	public static Optional<String> imageFileToBase64Str(MultipartFile multipartFile) {
		if (Objects.isNull(multipartFile)) {
			return Optional.empty();
		}

		try {
			byte[] bytes = multipartFile.getBytes();
			String str = Base64.getEncoder().encodeToString(bytes);
			return Optional.of(str);
		} catch (IOException e) {
			return Optional.empty();
		}

	}
}
