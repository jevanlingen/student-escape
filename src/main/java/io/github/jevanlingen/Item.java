package io.github.jevanlingen;

import io.github.jevanlingen.luwak.function.ƒ;

public record Item(String name, String description, String visualisation, ƒ<String, Boolean> solution, String solvedText, String notSolvedText, boolean solved) {
	public Item(final String name, final String description, String visualisation) {
		this(name, description, visualisation, x -> true, "Nothing interesting happens.", "", true);
	}

	public Item(final String name, final String description, String visualisation, final ƒ<String, Boolean> solution, final String solvedText, final String notSolvedText) {
		this(name, description, visualisation, solution, solvedText, notSolvedText, false);
	}

	public String solutionText() {
		return solved ? solvedText : notSolvedText;
	}

	public Item solve(final String input) {
		return solution.apply(input)
				? new Item(name, description, visualisation, solution, solvedText, notSolvedText, true)
				: this;
	}
}