package io.github.jevanlingen.level;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.jevanlingen.Action;
import io.github.jevanlingen.Item;
import io.github.jevanlingen.Level;
import io.github.jevanlingen.Location;
import io.github.jevanlingen.Place;
import io.github.jevanlingen.luwak.function.ƒ;
import io.github.jevanlingen.luwak.util.Do;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod({ Do.class })
public abstract class AbstractLevel implements Level {
	// Only place for stateful parameters (may only be replaced, not changed!)
	Location currentLocation = null;
	List<Place> places;

	@Override
	public String executeAction(final Action action, final String value) {
		return switch (action) {
			case LOOK -> lookAround();
			case GOTO -> move(value);
			case DESCRIBE -> currentLocation.item().description();
			case INSPECT -> currentLocation.item().visualisation();
			case ENTER -> enter(value);
		};
	}

	private String lookAround() {
		return "You are currently in '" + currentLocation.place().name().toLowerCase() + "' at the " + currentLocation.item().name().toLowerCase() + ".\n" +
				"You see the following items around you:\n" +
				currentLocation.place().items().stream().map(x -> "- " + x.name()).collect(Collectors.joining("\n"));
	}

	private String move(final String value) {
		final var moveOptions = value.split("[ ](?=[^ ]*$)", 2);

		final var placeName = moveOptions.length == 1 ? currentLocation.place().name() : moveOptions[0];
		final var itemName = moveOptions.length == 1 ? moveOptions[0] : moveOptions[1];

		final var place = places.findAny(p -> p.name().equalsIgnoreCase(placeName));
		final var item = places.stream().flatMap(p -> p.items().stream()).findAny(i -> i.name().equalsIgnoreCase(itemName));

		if (place.isEmpty()) {
			return "As far as I know, there is no " + placeName + " here.";
		}

		if (item.isEmpty()) {
			return "I don't see a " + itemName + " in this room.";
		}

		currentLocation = new Location(place.get(), item.get());
		return "You walk to the " + item.get().name().toLowerCase() + ".";
	}

	private String enter(final String value) {
		final var oldCurrentItem = currentLocation.item();
		final ƒ<Item, Item> items = i -> i == oldCurrentItem ? currentLocation.item() : i;

		currentLocation = new Location(currentLocation.place(), currentLocation.item().solve(value));
		places = places.map(p -> new Place(p.name(), p.items().map(items)));

		return currentLocation.item().solutionText();
	}

	@Override
	public boolean isSolved() {
		return places.stream().allMatch(Place::isSolved);
	}
}