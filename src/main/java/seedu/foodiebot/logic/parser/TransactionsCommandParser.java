package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_YEAR;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_FROM_DATE;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_TO_DATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.foodiebot.commons.core.date.DateFormatter;
import seedu.foodiebot.commons.core.date.DateRange;
import seedu.foodiebot.commons.core.date.DateRangeStyle;
import seedu.foodiebot.logic.commands.TransactionsCommand;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** Parses input arguments and creates a new TransactionsCommand object */
public class TransactionsCommandParser implements Parser<TransactionsCommand> {

    private static final List<Prefix> COMBINATION_START_END = List.of(PREFIX_FROM_DATE, PREFIX_TO_DATE);
    private static final List<Prefix> COMBINATION_ONLY_START = List.of(PREFIX_FROM_DATE);
    private static final List<Prefix> COMBINATION_ONLY_END = List.of(PREFIX_TO_DATE);
    private static final List<Prefix> COMBINATION_ONLY_MONTH = List.of(PREFIX_DATE_BY_MONTH);
    private static final List<Prefix> COMBINATION_ONLY_YEAR = List.of(PREFIX_DATE_BY_YEAR);
    private static final List<Prefix> COMBINATION_MONTH_YEAR = List.of(PREFIX_DATE_BY_MONTH, PREFIX_DATE_BY_YEAR);

    /**
     * Parses the given {@code String} of arguments in the context of the TransactionsCommand and returns a
     * TransactionsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TransactionsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FROM_DATE, PREFIX_TO_DATE,
                PREFIX_DATE_BY_MONTH, PREFIX_DATE_BY_YEAR);

        List<Prefix> argPrefixCombination = getArgMultimapCombination(argMultimap);
        if (argPrefixCombination.isEmpty()) {
            return new TransactionsCommand(DateRange.generate());

        } else if (argPrefixCombination.equals(COMBINATION_START_END)) {
            String start = getArgString(argMultimap, PREFIX_FROM_DATE);
            String end = getArgString(argMultimap, PREFIX_TO_DATE);
            return new TransactionsCommand(DateRange.of(start, end, DateRangeStyle.STRICT));

        } else if (argPrefixCombination.equals(COMBINATION_ONLY_START)) {
            String start = getArgString(argMultimap, PREFIX_FROM_DATE);
            return new TransactionsCommand(DateRange.of(start, PREFIX_FROM_DATE));

        } else if (argPrefixCombination.equals(COMBINATION_ONLY_END)) {
            String end = getArgString(argMultimap, PREFIX_TO_DATE);
            return new TransactionsCommand(DateRange.of(end, PREFIX_TO_DATE));

        } else if (argPrefixCombination.equals(COMBINATION_ONLY_MONTH)) {
            String monthString = getArgString(argMultimap, PREFIX_DATE_BY_MONTH);
            int month = DateFormatter.formatMonth(monthString);
            return new TransactionsCommand(DateRange.ofMonth(month, DateRangeStyle.STRICT));

        } else if (argPrefixCombination.equals(COMBINATION_ONLY_YEAR)) {
            String yearString = getArgString(argMultimap, PREFIX_DATE_BY_YEAR);
            int year = DateFormatter.formatYear(yearString);
            return new TransactionsCommand(DateRange.ofYear(year, DateRangeStyle.STRICT));

        } else if (argPrefixCombination.equals(COMBINATION_MONTH_YEAR)) {
            String monthString = getArgString(argMultimap, PREFIX_DATE_BY_MONTH);
            String yearString = getArgString(argMultimap, PREFIX_DATE_BY_YEAR);
            int month = DateFormatter.formatMonth(monthString);
            int year = DateFormatter.formatYear(yearString);
            return new TransactionsCommand(DateRange.ofMonth(month, year, DateRangeStyle.STRICT));

        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TransactionsCommand.MESSAGE_USAGE));
        }
    }

    /** Extracts the argument tagged to the given prefix. Throws {@code} ParseException if no value is present.*/
    public static String getArgString(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        return argMultimap.getValue(prefix)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TransactionsCommand.MESSAGE_USAGE)));
    }

    /** Retrieves the combination of prefixes in the {@code ArgumentMultimap} that has
     * at least one argument.
     * */
    public List<Prefix> getArgMultimapCombination(ArgumentMultimap argumentMultimap) {
        List<Prefix> validPrefixes = List.of(PREFIX_FROM_DATE, PREFIX_TO_DATE,
                PREFIX_DATE_BY_MONTH, PREFIX_DATE_BY_YEAR);

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
