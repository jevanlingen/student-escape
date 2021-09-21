package io.github.jevanlingen;

import java.util.List;

public record Place(String name, List<Item> items) {
	public boolean isSolved() {
		return items.stream().allMatch(Item::solved);
	}
}