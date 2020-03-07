package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_DAY;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_WEEK;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.foodiebot.logic.commands.BudgetCommand;
import seedu.foodiebot.logic.parser.exceptions.ParseException;
import seedu.foodiebot.model.budget.Budget;

/** Parses input arguments and creates a new BudgetCommand object */
public class BudgetCommandParser implements Parser<BudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCommand and returns a
     * BudgetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE_BY_DAY,
                PREFIX_DATE_BY_WEEK, PREFIX_DATE_BY_MONTH);
        String action = argMultimap.getPreamble();

        switch(action) {
        case "set":
            try {
                Budget budget = setBudget(argMultimap);
                return new BudgetCommand(budget, action);
            } catch (IndexOutOfBoundsException oobe) {
                break;
            }

        case "view":
            return new BudgetCommand(action);
        default:
            break;
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
    }

    private static boolean isValidPrefix(Prefix duration) {
        return duration.equals(PREFIX_DATE_BY_DAY)
                || duration.equals(PREFIX_DATE_BY_WEEK)
                || duration.equals(PREFIX_DATE_BY_MONTH);
    }

    public Budget setBudget(ArgumentMultimap argMultimap) throws ParseException {

        int argMultimapPrefixCount = getArgMultimapPrefixCount(argMultimap);
        Prefix firstPrefix = argPrefixCombination(argMultimap).get(0);

        if (argMultimapPrefixCount == 1 && isValidPrefix(firstPrefix)) {
            try {
                String argValue = getArgString(argMultimap, firstPrefix);
                float value = Float.parseFloat(argValue);
                return new Budget(value, firstPrefix.toString());

            } catch (NullPointerException | NumberFormatException ne) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
            }
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
    }

    /** Extracts the argument tagged to the given prefix. Throws {@code ParseException}
     * if no value is present.
     * */
    public static String getArgString(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        return argMultimap.getValue(prefix)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE)));
    }

    /** Returns the number of prefixes present in the {@code ArgumentMultimap} that has
     * at least one argument.
     * */
    public int getArgMultimapPrefixCount(ArgumentMultimap argumentMultimap) {
        List<Prefix> validPrefixes = List.of(PREFIX_DATE_BY_DAY,
                PREFIX_DATE_BY_WEEK, PREFIX_DATE_BY_MONTH);

        int count = 0;
        for (Prefix prefix : validPrefixes) {
            if (!argumentMultimap.getAllValues(prefix).isEmpty()) {
                count++;
            }
        }
        return count;
    }

    /** Retrieves the combination of prefixes in the {@code ArgumentMultimap} that has
     * at least one argument.
     * */
    public List<Prefix> argPrefixCombination(ArgumentMultimap argumentMultimap) {
        List<Prefix> validPrefixes = List.of(PREFIX_DATE_BY_DAY,
                PREFIX_DATE_BY_WEEK, PREFIX_DATE_BY_MONTH);

        ArrayList<Prefix> combination = new ArrayList<Prefix>();
        for (Prefix prefix : validPrefixes) {
            Optional<String> value = argumentMultimap.getValue(prefix);
            if (!value.equals(Optional.empty())) {
                combination.add(prefix);
            }
        }
        return combination;
    }


}
