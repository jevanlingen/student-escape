package io.github.jevanlingen.level;

import static io.github.jevanlingen.luwak.function.Recursable.recurse;

import java.util.List;

import io.github.jevanlingen.Item;
import io.github.jevanlingen.Level;
import io.github.jevanlingen.Location;
import io.github.jevanlingen.Place;
import io.github.jevanlingen.luwak.function.$;
import io.github.jevanlingen.luwak.function.ƒ;

public class One extends AbstractLevel implements Level {
	public One() {
		final var bed = new Item("Bed", "'Tis my own bed...", """
				  ()___
				()//__/)_________________()
				||(___)//#/_/#/_/#/_/#()/||
				||----|#| |#|_|#|_|#|_|| ||
				||____|_|#|_|#|_|#|_|#||/||
				||    |#|_|#|_|#|_|#|_||""");

		final var closet = new Item("Closet", "Gotta need something to store my clothes.", """
				,-------,
				|___|___|
				|___|___|
				|___|___|
				|___|___|
				|:::::::|""");

		final var whiteboard = new Item("Whiteboard", "Here I write down all the important stuff.", """
				,----------------------------------------------------------------------------,
				|                                                                            |
				|    Buy present                                   Hehe                      |
				|     for mom!                                         nobody                |
				|                                                            knows!          |
				|                     f(x)= sin(x^{2}+1)}                                    |
				|                                                  |\\/\\/\\/|                  |
				|                                                  |      |                  |
				|                                                  | (o)(o)                  |
				|                                                  C      _)                 |
				|   “A reader lives a thousand                     | ,___|                   |
				|        lives before he dies....”                 |   /                     |
				|                                                  /____\\                    |
				|                                                                            |
				`----------------------------------------------------------------------------'""");

		final var computer = new Item("Computer", "My big ass computer. Luckily it's password protected, so my housemates can't use it.", """
				              .----.
				  .---------. | == |
				  |.-""\"""-.| |----|
				  ||  LOG  || | == |
				  ||   IN  || |----|
				  |'-.....-'| |::::|
				  `"")---(""` |___.|
				 /:::::::::::\\" _  "
				/:::=======:::\\`\\`\\
				`""\"""\"""\"""\""`  '-'""",
				s -> s.equals("George R.R. Martin"), "Password correct!\n You print your assignment.", "Incorrect password, try again.");

		final var place = new Place("Dorm room", List.of(bed, closet, whiteboard, computer));

		super.places = List.of(place);
		super.currentLocation = new Location(place, closet);
	}

	public static void main(String[] args) {
		ƒ<$<Human>, Boolean> saveAHuman = humanAction -> humanAction.get().alive() && Math.random() > 0.5;

		System.out.println(davidWinsTheBattleF(2, saveAHuman));
	}

	boolean davidWinsTheBattle(final int stepsBetweenDavidAndGoliath, final ƒ<$<Human>, Boolean> doDivineMagic) {
		final ƒ<Integer, Boolean> davidDies = steps -> steps * 0.3 < 34;

		return doDivineMagic.apply(() -> new Human("David", davidDies.apply(stepsBetweenDavidAndGoliath)));
	}

	static boolean davidWinsTheBattleF(final int stepsBetweenDavidAndGoliath, final ƒ<$<Human>, Boolean> doDivineMagic) {
		final ƒ<Integer, Boolean> davidDies = steps -> steps * 0.3 < 34;
		final ƒ<Boolean, $<Human>> convert = heDies -> () -> new Human("David", heDies);

		return davidDies.andThen(convert).andThen(doDivineMagic).apply(stepsBetweenDavidAndGoliath);
	}

	public record Human(String name, boolean alive){};

	@Override
	public String intro() {
		return """
				You wake up in your dorm room after a long night of writing a paper. After you put on your glasses, you LOOK around you.
				You GOTO your closet to put on some clothes. You INSPECT them and change to your favourite shirt and jeans.
				If someone would have asked you to DESCRIBE yourself, you would say you are a nerdy second-year student at MIT.
				Before you are ready to ENTER college life again, you'll still have to print out the Lambda Calculus assignment for Professor X...""";
	}

	@Override
	public String outro() {
		return "Now let's go to class!";
	}
}



