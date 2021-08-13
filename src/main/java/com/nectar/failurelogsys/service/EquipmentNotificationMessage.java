package com.nectar.failurelogsys.service;

import java.util.HashMap;
import java.util.Map;


public enum EquipmentNotificationMessage  {

	NO_COMMUNICATION(101, "NO_COMMUNICATION", "No data communication from the meter"),
	STATIC(102, "STATIC", "Difference of maximum and minimum of current aggregated data is zero"),

	NON_CUMULATIVE(103, "NON_CUMULATIVE",
			"Current data is less than previous data. As these are the meters we expect cumulative data from the meters, data should be greater than previous data."),

	SPIKE(104, "SPIKE", "Current aggregation is five time than average of the previous aggregation"),

	LEAKAGE(105, "LEAKAGE", "Current aggregation is double than average of the previous aggregation"),

	OUT_OF_BOUND(106, "OUT_OF_BOUND", "Current Data is outside the Range given"),
	SCHEDULER_ERROR(107, "SCHEDULER_ERROR", "Error ouccered on scheduler");



	
	private Integer code;
	private String message;
	private String description;

	private EquipmentNotificationMessage(Integer code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	private static final Map<Integer, EquipmentNotificationMessage> map;
	static {
		map = new HashMap<Integer, EquipmentNotificationMessage>();
		for (EquipmentNotificationMessage v : EquipmentNotificationMessage.values()) {
			map.put(v.getCode(), v);
		}
	}

	public static EquipmentNotificationMessage getNotificationCode(Integer code) {
		EquipmentNotificationMessage result = map.get(code);
		if (result == null) {
			throw new IllegalArgumentException("No Notification Code Exists");
		}
		return result;
	}
}
