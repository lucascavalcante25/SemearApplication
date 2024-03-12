package com.semearApp.semearApp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

	public static String formatarData(LocalDate data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}
