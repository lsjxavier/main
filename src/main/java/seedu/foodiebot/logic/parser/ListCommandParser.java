package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_FROM;

import java.util.stream.Stream;

import seedu.foodiebot.logic.commands.ListCommand;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GoToCanteenCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(
        ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the GoToCanteenCommand and returns a
     * GoToCanteenCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FROM);
        String enteredText = argMultimap.getPreamble();
        if (arePrefixesPresent(argMultimap, PREFIX_FROM)) {
            String nearestBlockName = ParserUtil.parseBlockName(argMultimap.getValue(PREFIX_FROM).get());
            return new ListCommand(nearestBlockName);
        }

        return new ListCommand("");
    }
}
