package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_FROM_DATE;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_TO_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.foodiebot.model.Model;

/** Get the latest expenses within a date range and output in report format */
public class ReportCommand extends Command {
    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " "
            + PREFIX_FROM_DATE
            + " FROM_DATE "
            + PREFIX_TO_DATE
            + " TO_DATE\n"
            + "Example: "
            + COMMAND_WORD
            + " "
            + PREFIX_FROM_DATE
            + "14/2/2020 "
            + PREFIX_TO_DATE + "24/2/2020";

    public static final String MESSAGE_SUCCESS = "Here are your expenses for ";

    private final Stream<LocalDate> dateRange;

    public ReportCommand(Stream<LocalDate> dateRange) {
        this.dateRange = dateRange;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS);
    }
}
