package seedu.foodiebot.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.foodiebot.model.budget.Budget;
import seedu.foodiebot.model.canteen.Canteen;
import seedu.foodiebot.model.canteen.CanteenStub;
import seedu.foodiebot.model.canteen.Stall;
import seedu.foodiebot.model.canteen.UniqueCanteenList;
import seedu.foodiebot.model.stall.UniqueStallList;

/**
 * Wraps all data at the application level Duplicates are not allowed (by .isSame_____ comparison)
 */
public class FoodieBot implements ReadOnlyFoodieBot {

    private final UniqueCanteenList canteens;
    private final UniqueStallList stalls;
    private Budget budget;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        canteens = new UniqueCanteenList();
        stalls = new UniqueStallList();
        budget = new Budget();
    }

    public FoodieBot() {}

    /** Creates an FoodieBot using the Canteens in the {@code toBeCopied} */
    public FoodieBot(ReadOnlyFoodieBot toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the canteen list with {@code canteens}. {@code canteens} must not
     * contain duplicate canteens.
     */
    public void setCanteens(List<Canteen> canteens) {
        this.canteens.setCanteens(canteens);
    }

    public void setStalls(List<Stall> stalls) {
        this.stalls.setStalls(stalls);
    }


    public void setBudget(Budget budget) {
        this.budget = budget;
    }


    /** Resets the existing data of this {@code FoodieBot} with {@code newData}. */
    public void resetData(ReadOnlyFoodieBot newData) {
        requireNonNull(newData);

        setCanteens(newData.getCanteenList());
        setStalls(newData.getStallList());
    }

    //// canteen-level operations

    /**
     * Returns true if a canteen with the same identity as {@code canteen} exists in the address
     * book.
     */
    public boolean hasCanteen(Canteen canteen) {
        requireNonNull(canteen);
        return canteens.contains(canteen);
    }

    /**
     * Adds a canteen to the foodiebot. The canteen must not already exist in the foodiebot.
     */
    public void addCanteen(Canteen p) {
        canteens.add(p);
    }

    /**
     * Adds a canteen to the foodiebot. The canteen must not already exist in the foodiebot.
     */
    public void addCanteen(CanteenStub p) {
        canteens.add(new Canteen(p.getName(), 0, 0,
            "name", "", "", p.getTags(), ""));
    }

    /**
     * Replaces the given canteen {@code target} in the list with {@code editedCanteen}. {@code
     * target} must exist in the foodiebot. The canteen identity of {@code editedCanteen} must
     * not be the same as another existing canteen in the foodiebot.
     */
    public void setCanteen(Canteen target, Canteen editedCanteen) {
        requireNonNull(editedCanteen);

        canteens.setCanteen(target, editedCanteen);
    }

    /**
     * Removes {@code key} from this {@code FoodieBot}. {@code key} must exist in the address
     * book.
     */
    public void removeCanteen(Canteen key) {
        canteens.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return canteens.asUnmodifiableObservableList().size() + " canteens";
        // TODO: refine later
    }

    @Override
    public ObservableList<Canteen> getCanteenList() {
        return canteens.asUnmodifiableObservableList();
    }

    @Override
    public Budget getBudget() {
        return budget;
    }


    //// stall-level operations

    /**
     * Returns true if a canteen with the same identity as {@code canteen} exists in the address
     * book.
     */

    public boolean hasStall(Stall stall) {
        requireNonNull(stall);
        return stalls.contains(stall);
    }

    public ObservableList<Stall> getStallList() {
        return stalls.asUnmodifiableObservableList();
    }

    /**
     * Adds a stall to the foodiebot. The stall must not already exist in the foodiebot.
     */
    public void addStall(Stall s) {
        stalls.add(s);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoodieBot // instanceof handles nulls
                        && canteens.equals(((FoodieBot) other).canteens) && stalls.equals(((FoodieBot) other).stalls));
    }

    @Override
    public int hashCode() {
        return canteens.hashCode();
    }
}
