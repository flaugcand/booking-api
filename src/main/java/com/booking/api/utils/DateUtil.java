package com.booking.api.utils;

import java.time.LocalDate;
import java.util.Objects;

public class DateUtil {

	public static boolean dateValidation(LocalDate arg0, LocalDate arg1) {
		if (Objects.nonNull(arg0) && Objects.nonNull(arg1))
			return arg0.isBefore(arg1) || arg0.equals(arg1);

		return Boolean.FALSE;
	}

}
