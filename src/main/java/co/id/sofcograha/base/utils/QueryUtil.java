package co.id.sofcograha.base.utils;

public class QueryUtil {

	public static boolean isAll(String strngPVal) {
		if (strngPVal == null
				|| strngPVal.trim().equals("")
				|| strngPVal.trim().equals("%")
				|| strngPVal.trim().equals("*")) {
			return true ;
		} else {
			return false;
		}
	}
	
	public static String formatStringForLikeFilter(String strngPVal) {
		if (strngPVal == null) {
			return null;
		} else if (strngPVal.trim().equals("")) {
			return "%";
		} else {
			return "%" + strngPVal.trim().replace('*', '%') + "%";
		}
	}
}
