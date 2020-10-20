package com.kplan.phonecard.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.IOUtils;

public class HeadImgUtil {
	public static Optional<String> imageFileToBase64Str(File f) {
		if (Objects.isNull(f)) {
			return Optional.empty();
		}
		if (!f.exists()) {
			return Optional.empty();
		}
		if (f.isDirectory()) {
			return Optional.empty();
		}

		try {
			byte[] bytes = IOUtils.toByteArray(new FileInputStream(f));
			String str = Base64.getEncoder().encodeToString(bytes);
			return Optional.of("data:image/jpeg;base64," + str);
		} catch (IOException e) {
			return Optional.empty();
		}

	}

}
