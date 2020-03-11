package seedu.foodiebot.logic;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.foodiebot.commons.core.GuiSettings;
import seedu.foodiebot.logic.commands.CommandResult;
import seedu.foodiebot.logic.commands.exceptions.CommandException;
import seedu.foodiebot.logic.parser.exceptions.ParseException;
import seedu.foodiebot.model.ReadOnlyFoodieBot;
import seedu.foodiebot.model.canteen.Canteen;
import seedu.foodiebot.model.canteen.Stall;

/** API of the Logic component */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, IOException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.foodiebot.model.Model#getFoodieBot()
     */
    ReadOnlyFoodieBot getFoodieBot();

    /** Returns an unmodifiable view of the filtered list of canteens */
    ObservableList<Canteen> getFilteredCanteenList();

    /** Returns the user prefs' address book file path. */
    Path getFoodieBotFilePath();

    /** Returns the user prefs' GUI settings. */
    GuiSettings getGuiSettings();

    /** Set the user prefs' GUI settings. */
    void setGuiSettings(GuiSettings guiSettings);

    ObservableList<Stall> getFilteredStallList(boolean isInitialised);
}
