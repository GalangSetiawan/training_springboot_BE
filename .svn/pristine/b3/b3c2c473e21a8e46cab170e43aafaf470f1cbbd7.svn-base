package co.id.sofcograha.base.utils;

import co.id.sofcograha.base.exceptions.BusinessException;

public class VersionUtil {

	public static void check(Long yourVersion, Long dbVersion) {
		if (dbVersion == null || yourVersion.longValue() != dbVersion.longValue()) {
			throw new BusinessException("data.obsolete.version");
		}
	}

	public static void checkRoot(Long yourVersion, Long dbVersion) {
		if (dbVersion == null || yourVersion.longValue() != dbVersion.longValue()) {
			throw new BusinessException("root.data.obsolete.version");
		}
	}
}
