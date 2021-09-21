package io.github.jevanlingen;

import java.util.Arrays;

import io.github.jevanlingen.luwak.wrapper.Ø;

public enum Action {
	LOOK, GOTO, DESCRIBE, INSPECT, ENTER;

	public static Ø<Action> getByValue(final String value) {
		return Ø.of(Arrays.stream(Action.values())
				.filter(a -> a.name().equalsIgnoreCase(value))
				.findAny());
	}
}
