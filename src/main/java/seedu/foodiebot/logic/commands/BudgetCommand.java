package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.foodiebot.commons.core.Messages.MESSAGE_BUDGET_VIEW;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;

import seedu.foodiebot.commons.core.date.DefiniteDate;
import seedu.foodiebot.model.FoodieBot;
import seedu.foodiebot.model.Model;
import seedu.foodiebot.model.budget.Budget;

/** Manages the budget commands, e.g. view, set. */
public class BudgetCommand extends Command {
    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + " VIEW : View the budget. \n"
            + "SET : Set the budget"
            + "Parameters: "
            + PREFIX_DATE_BY_MONTH
            + "AMOUNT \n"
            + "Example: "
            + COMMAND_WORD
            + " "
            + PREFIX_DATE_BY_MONTH
            + "100 ";

    public static final String MESSAGE_SUCCESS = MESSAGE_BUDGET_VIEW;
    public static final String MESSAGE_FAILURE = "No budget stored!";

    private final Budget budget;
    private final String action;

    public BudgetCommand(Budget budget, String action) {
        this.budget = budget;
        this.action = action;
    }

    public BudgetCommand(String action) {
        this.budget = new Budget();
        this.action = action;
    }

    /** A boolean check if the current system date falls inside the budget range */
    public static boolean systemDateIsInCycleRange(Budget budget) {
        return budget.getCycleRange().contains(DefiniteDate.TODAY);
    }

    /** Helper function to write the budget to the model. */
    public static void saveBudget(Model model, Budget budget) {
        model.setFoodieBot(new FoodieBot());
        model.setBudget(budget);
    }

    /** Helper function to read the budget from the model. */
    public static Budget loadBudget(Model model) {
        return model.getBudget().isPresent()
                ? model.getBudget().get()
                : new Budget();
    }

    /** Helper function to hold a successful return message. */
    public static CommandResult commandSuccess(Budget budget) {
        return new CommandResult(COMMAND_WORD, String.format(MESSAGE_SUCCESS,
                budget.getDurationAsString(), budget.getTotalBudget(), budget.getRemainingWeeklyBudget()));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (action.equals("set")) {
            saveBudget(model, budget);
            return commandSuccess(budget);

        } else {
            Budget savedBudget = loadBudget(model);

            if (!savedBudget.equals(new Budget())) {
                if (!systemDateIsInCycleRange(savedBudget)) {
                    savedBudget.resetRemainingBudget();
                }
                saveBudget(model, savedBudget);
                return commandSuccess(savedBudget);
            }

            return new CommandResult(COMMAND_WORD, MESSAGE_FAILURE);
        }

    }
}
