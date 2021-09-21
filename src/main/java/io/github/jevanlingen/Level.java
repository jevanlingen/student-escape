package io.github.jevanlingen;

public interface Level {
	String executeAction(Action action, String value);
	boolean isSolved();

	String intro();
	String outro();
}
