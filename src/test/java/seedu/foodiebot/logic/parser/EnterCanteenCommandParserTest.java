package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.foodiebot.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.foodiebot.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.foodiebot.logic.commands.EnterCanteenCommand;
import seedu.foodiebot.model.canteen.Canteen;

class EnterCanteenCommandParserTest {

    private EnterCanteenCommandParser parser = new EnterCanteenCommandParser();

    @BeforeAll
    public static void setMainContext() {
        ParserContext.setCurrentContext(ParserContext.MAIN_CONTEXT);
    }

    @Test
    public void parse_validIndex_returnsEnterCanteenCommand() {
        assertParseSuccess(parser, "1", new EnterCanteenCommand(INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidIndex_returnsEnterCanteenCommand() {
        assertParseFailure(
            parser,
            "0",
            Canteen.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validName_returnsCanteenCommand() {
        assertParseSuccess(parser, "The Deck", new EnterCanteenCommand("The Deck"));
    }


    @Test
    public void parse_invalidName_throwsParseException() {
        assertParseFailure(parser, "a", Canteen.MESSAGE_CONSTRAINTS);
    }
}