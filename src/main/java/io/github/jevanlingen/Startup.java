package io.github.jevanlingen;

import java.util.Scanner;

import io.github.jevanlingen.level.One;

public class Startup {
	public static void main(String[] args) {
		final var scanner = new Scanner(System.in);
		final var game = new One();
		System.out.println(game.intro());

		while (true) {
			System.out.print("> ");
			final var input = scanner.nextLine().trim().replaceAll(" +", " ");

			if ("exit".equals(input)) {
				System.out.println("Mèh coward, don't give up next time!");
				break;
			}

			final var inputOptions = input.split(" ", 2);
			final var actionText = Action.getByValue(inputOptions[0])
					.map(x -> game.executeAction(x, inputOptions.length == 1 ? "" : inputOptions[1]))
					.orElse("I cannot do that.");

			System.out.println(actionText);

			if (game.isSolved()) {
				System.out.println(game.outro());
				break;
			}
		}
	}
}