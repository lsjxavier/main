package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_YEAR;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_FROM_DATE;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_TO_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.foodiebot.logic.commands.TransactionsCommand;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** Parses input arguments and creates a new TransactionsCommand object */
public class TransactionsCommandParser implements Parser<TransactionsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TransactionsCommand and returns a
     * TransactionsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TransactionsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FROM_DATE, PREFIX_TO_DATE,
                PREFIX_DATE_BY_MONTH, PREFIX_DATE_BY_YEAR);
        try {
            Stream<LocalDate> dateRange = DateRangeFormatter.getDateRange(argMultimap);
            return new TransactionsCommand(dateRange);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }
}
