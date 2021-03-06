package seedu.foodiebot.ui;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import seedu.foodiebot.MainApp;
import seedu.foodiebot.commons.core.LogsCenter;
import seedu.foodiebot.logic.Logic;
import seedu.foodiebot.logic.commands.CommandResult;
import seedu.foodiebot.logic.commands.DirectionsCommandResult;
import seedu.foodiebot.logic.commands.EnterCanteenCommand;
import seedu.foodiebot.logic.commands.ExitCommand;
import seedu.foodiebot.logic.commands.FavoritesCommand;
import seedu.foodiebot.logic.commands.ListCommand;
import seedu.foodiebot.logic.commands.exceptions.CommandException;
import seedu.foodiebot.logic.parser.ParserContext;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** Base class for creating a javafx scene. */
abstract class BaseScene {
    public static final String FXML_FILE_FOLDER = "/view/";
    // Independent Ui parts residing in this Ui container

    protected Logic logic;
    protected Stage primaryStage;

    @FXML
    protected MenuItem helpMenuItem;
    private final Logger logger = LogsCenter.getLogger(getClass());
    @FXML
    private StackPane statusbarPlaceholder;
    @FXML
    private StackPane commandBoxPlaceholder;
    @FXML
    private StackPane listPanelPlaceholder;
    private ResultDisplay resultDisplay;
    @FXML
    private StackPane resultDisplayPlaceholder;


    private HelpWindow helpWindow;


    public BaseScene(Stage primaryStage, Logic logic) {
        this.primaryStage = primaryStage;
        this.logic = logic;
        fillInnerParts();
        helpWindow = new HelpWindow();
    }

    void addToListPanel(UiPart<Region> regionUiPart) {
        listPanelPlaceholder = (StackPane) primaryStage.getScene().lookup("#listPanelPlaceholder");
        listPanelPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().add(regionUiPart.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder = (StackPane) primaryStage.getScene().lookup("#commandBoxPlaceholder");

        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFoodieBotFilePath());
        statusbarPlaceholder = (StackPane) primaryStage.getScene().lookup("#statusbarPlaceholder");
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
        commandBox.getCommandTextField().requestFocus();
    }


    void updateStatusFooter(String message) {
        statusbarPlaceholder = (StackPane) primaryStage.getScene().lookup("#statusbarPlaceholder");
        statusbarPlaceholder.getChildren().clear();
        statusbarPlaceholder.getChildren().add(new Label(message));
    }


    StackPane getResultDisplayPlaceholder() {
        resultDisplayPlaceholder = (StackPane) primaryStage.getScene().lookup("#resultDisplayPlaceholder");
        return resultDisplayPlaceholder;
    }

    ResultDisplay getResultDisplay() {
        resultDisplay = new ResultDisplay();
        return resultDisplay;
    }

    /** .*/
    void updateResultDisplay(String result) {
        resultDisplayPlaceholder = (StackPane) primaryStage.getScene().lookup("#resultDisplayPlaceholder");
        resultDisplay = new ResultDisplay();
        if (!result.isEmpty()) {
            getResultDisplay().setFeedbackToUser(result);
            resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        }
    }

    @FXML
    public void handleListStalls() {
        addToListPanel(new StallsListPanel(logic.getFilteredStallList(true)));
    }

    /**
     * Fills the foodListPanel region.
     */
    @FXML
    public void handleListFood() {
        addToListPanel(new FoodListPanel(logic.getFilteredFoodList(true)));
    }

    /**
     * Fills the foodListPanel region.
     */
    @FXML
    public void handleListFavorites() {
        addToListPanel(new FoodListPanel(logic.getFilteredFavoriteFoodList(false)));
    }

    /** .*/
    @FXML
    public void handleListCanteens(CommandResult commandResult) {
        changeScene("MainScene.fxml");
        new MainScene(primaryStage, logic);
        addToListPanel(new CanteenListPanel(
            commandResult.isLocationSpecified()
                ? logic.getFilteredCanteenListSortedByDistance()
                : logic.getFilteredCanteenList(), commandResult.isLocationSpecified()));
    }


    /** The method passed from logic to UI. */
    protected CommandResult executeCommand(String commandText)
        throws CommandException, ParseException, IOException {
        CommandResult commandResult = null;
        try {
            commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());


            if (commandResult instanceof DirectionsCommandResult) {
                //new DirectionsWindowScene(getPrimaryStage(), logic, (DirectionsCommandResult) commandResult).show();

                VBox pane = (VBox) loadFxmlFile("NoResultDisplayScene.fxml");
                /* This is a negative example to load ui
                Scene scene = new Scene(pane); // optionally specify dimensions too
                getPrimaryStage().setScene(scene);
                */
                ParserContext.setCurrentContext(ParserContext.DIRECTIONS_CONTEXT);
                primaryStage.getScene().setRoot(pane);
                new DirectionsScene(primaryStage, logic, (DirectionsCommandResult) commandResult);

            }

            switch (commandResult.commandName) {
            case ListCommand.COMMAND_WORD:
                if (ParserContext.getCurrentContext().equals(ParserContext.MAIN_CONTEXT)) {
                    handleListCanteens(commandResult);
                    updateResultDisplay(commandResult.getFeedbackToUser());
                } else if (ParserContext.getCurrentContext().equals(ParserContext.DIRECTIONS_CONTEXT)) {
                    handleListCanteens(commandResult);
                    ParserContext.setCurrentContext(ParserContext.MAIN_CONTEXT);
                } else {
                    updateResultDisplay(ParserContext.INVALID_CONTEXT_MESSAGE);
                }
                break;
            case EnterCanteenCommand.COMMAND_WORD:
                updateResultDisplay(commandResult.getFeedbackToUser());
                if (ParserContext.getCurrentContext().equals(ParserContext.MAIN_CONTEXT)) {
                    ParserContext.setCurrentContext(ParserContext.CANTEEN_CONTEXT);
                    handleListStalls();
                } else if (ParserContext.getCurrentContext().equals(ParserContext.CANTEEN_CONTEXT)) {
                    ParserContext.setCurrentContext(ParserContext.STALL_CONTEXT);
                    handleListFood();
                }
                break;
            case FavoritesCommand.COMMAND_WORD:
                updateResultDisplay(commandResult.getFeedbackToUser());
                handleListFavorites();
                break;
            case ExitCommand.COMMAND_WORD:
                updateResultDisplay(commandResult.getFeedbackToUser());
                switch (ParserContext.getCurrentContext()) {
                case ParserContext.MAIN_CONTEXT:
                    handleListCanteens(commandResult);
                    break;
                case ParserContext.CANTEEN_CONTEXT:
                    handleListStalls();
                    break;
                case ParserContext.STALL_CONTEXT:
                    handleListFood();
                    break;
                default:
                    break;
                }
                break;
            default:
                updateResultDisplay(commandResult.getFeedbackToUser());
                break;
            }
            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            return commandResult;
        } catch (CommandException | ParseException | IOException e) {
            updateResultDisplay(e.getMessage());
            throw e;
        }
    }


    /** Switches the scene of the stage by setting the root. */
    protected void changeScene(String layoutName) {
        primaryStage.getScene().setRoot(loadFxmlFile(layoutName));
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /** .*/
    protected Parent loadFxmlFile(String fxmlFileName) {
        FXMLLoader newLoader = new FXMLLoader();
        newLoader.setLocation(getFxmlFileUrl(fxmlFileName));
        newLoader.setController(this);
        try {
            return newLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static URL getFxmlFileUrl(String fxmlFileName) {
        requireNonNull(fxmlFileName);
        String fxmlFileNameWithFolder = FXML_FILE_FOLDER + fxmlFileName;
        URL fxmlFileUrl = MainApp.class.getResource(fxmlFileNameWithFolder);
        return requireNonNull(fxmlFileUrl);
    }
}
