package seedu.foodiebot.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.foodiebot.commons.core.GuiSettings;
import seedu.foodiebot.model.budget.Budget;
import seedu.foodiebot.model.canteen.Canteen;
import seedu.foodiebot.model.canteen.Stall;

/** The API of the Model component. */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Canteen> PREDICATE_SHOW_ALL_CANTEEN = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Stall> PREDICATE_SHOW_ALL_STALLS = unused -> true;


    /** Replaces user prefs data with the data in {@code userPrefs}. */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /** Returns the user prefs. */
    ReadOnlyUserPrefs getUserPrefs();

    /** Returns the user prefs' GUI settings. */
    GuiSettings getGuiSettings();

    /** Sets the user prefs' GUI settings. */
    void setGuiSettings(GuiSettings guiSettings);

    /** Returns the user prefs' address book file path. */
    Path getAddressBookFilePath();

    /** Sets the user prefs' address book file path. */
    void setAddressBookFilePath(Path addressBookFilePath);

    /** Replaces address book data with the data in {@code addressBook}. */
    void setFoodieBot(ReadOnlyFoodieBot foodieBot);

    /** Returns the AddressBook */
    ReadOnlyFoodieBot getFoodieBot();

    /**
     * Returns true if a canteen with the same identity as {@code canteen} exists in the address
     * book.
     */
    boolean hasCanteen(Canteen canteen);

    /** Deletes the given canteen. The canteen must exist in the address book. */
    void deleteCanteen(Canteen target);

    /** Adds the given canteen. {@code canteen} must not already exist in the address book. */
    void addCanteen(Canteen canteen);

    /**
     * Replaces the given canteen {@code target} with {@code editedPerson}. {@code target} must
     * exist in the address book. The canteen identity of {@code editedPerson} must not be the same
     * as another existing canteen in the address book.
     */
    void setCanteen(Canteen target, Canteen editedCanteen);

    void setBudget(Budget budget);

    Optional<Budget> getBudget();

    FileReader listOfCanteen() throws FileNotFoundException;

    /** Returns an unmodifiable view of the filtered canteen list */
    ObservableList<Canteen> getFilteredCanteenList();

    /** Returns an unmodifiable view of the filtered canteen list sorted by distance */
    ObservableList<Canteen> getFilteredCanteenListSortedByDistance();

    /**
     * Updates the filter of the filtered canteen list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCanteenList(Predicate<Canteen> predicate);
    ObservableList<Stall> getFilteredStallList();

    ObservableList<Stall> getFilteredStallList(boolean isInitialised);

    /**
     * Updates the filter of the filtered stall list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStallList(Predicate<Stall> predicate);
}
