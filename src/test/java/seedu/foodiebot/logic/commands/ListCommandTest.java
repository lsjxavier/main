package seedu.foodiebot.logic.commands;

import static seedu.foodiebot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.foodiebot.logic.commands.CommandTestUtil.showCanteenAtIndex;
import static seedu.foodiebot.testutil.TypicalCanteens.getTypicalFoodieBot;
import static seedu.foodiebot.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.foodiebot.model.Model;
import seedu.foodiebot.model.ModelManager;
import seedu.foodiebot.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFoodieBot(), new UserPrefs());
        expectedModel = new ModelManager(model.getFoodieBot(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showCanteenAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}