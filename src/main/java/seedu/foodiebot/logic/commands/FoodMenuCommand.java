package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.foodiebot.model.FoodieBot;
import seedu.foodiebot.model.Model;

/** Display the food items for the food stall */
public class FoodMenuCommand extends Command {
    public static final String COMMAND_WORD = "menu";

    public static final String MESSAGE_USAGE = "Usage :" + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully Printed Menu";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFoodieBot(new FoodieBot());
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS);
    }
}
