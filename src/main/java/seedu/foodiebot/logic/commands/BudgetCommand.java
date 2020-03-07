package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.foodiebot.commons.core.Messages.MESSAGE_BUDGET_VIEW;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;

import java.util.Optional;

import seedu.foodiebot.logic.parser.exceptions.ParseException;
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

    public BudgetCommand(String action) throws ParseException {
        this.budget = new Budget();
        this.action = action;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (action.equals("set")) {
            model.setBudget(budget);
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_SUCCESS,
                    budget.getDuration(), budget.getTotalBudget(), budget.getRemainingWeeklyBudget()));
        } else {
            Optional<Budget> savedBudget = model.getBudget();
            if (savedBudget.equals(Optional.empty())) {
                return new CommandResult(COMMAND_WORD, MESSAGE_FAILURE);
            }
            model.setBudget(savedBudget.get());

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_SUCCESS,
                    savedBudget.get().getDuration(), savedBudget.get().getTotalBudget(),
                    savedBudget.get().getRemainingWeeklyBudget()));
        }


    }
}
