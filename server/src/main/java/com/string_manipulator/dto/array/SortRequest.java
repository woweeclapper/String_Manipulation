package com.string_manipulator.dto.array;

import java.util.List;

public record SortRequest(List<Number> numbersList, String orderType) {}
