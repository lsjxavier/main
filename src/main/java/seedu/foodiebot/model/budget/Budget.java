package seedu.foodiebot.model.budget;

import static java.util.Objects.requireNonNull;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_PREFIX;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_DAY;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_WEEK;

import java.time.LocalDate;

import seedu.foodiebot.logic.parser.exceptions.ParseException;

/**
 * Represents a person's budget.
 */
public class Budget {
    private static final String DAILY = "daily";
    private static final String WEEKLY = "weekly";
    private static final String MONTHLY = "monthly";

    private final float totalBudget;
    private float remainingBudget;
    private final String duration;
    private final LocalDate dateOfCreation;


    /**
     * Constructs a {@code Budget}.
     * @param totalBudget The value of the budget.
     * @param duration The duration cycle of the budget.
     */
    public Budget(float totalBudget, String duration) throws ParseException {
        requireNonNull(duration);
        this.totalBudget = totalBudget;
        this.remainingBudget = totalBudget;
        this.duration = setDuration(duration);
        this.dateOfCreation = LocalDate.now();
    }

    public Budget(float totalBudget, float remainingBudget, String duration, LocalDate dateOfCreation) {
        this.totalBudget = totalBudget;
        this.remainingBudget = remainingBudget;
        this.duration = duration;
        this.dateOfCreation = dateOfCreation;
    }

    public Budget() {
        this(Float.MAX_VALUE, Float.MAX_VALUE, PREFIX_DATE_BY_DAY.toString(), LocalDate.now());
    }

    /**
     * Consolidates the possible entries for the budget duration into a simple String.
     * @param duration The duration cycle of the budget.
     * @return A consolidated duration cycle of the budget.
     */
    private String setDuration(String duration) throws ParseException {
        boolean setToDaily = duration.equals(PREFIX_DATE_BY_DAY.toString());
        boolean setToWeekly = duration.equals(PREFIX_DATE_BY_WEEK.toString());
        boolean setToMonthly = duration.equals(PREFIX_DATE_BY_MONTH.toString());

        if (setToDaily) {
            return DAILY;
        } else if (setToWeekly) {
            return WEEKLY;
        } else if (setToMonthly) {
            return MONTHLY;
        } else {
            throw new ParseException(MESSAGE_INVALID_PREFIX);
        }
    }

    /**
     * Divides the remaining budget by 7 if set to weekly, or by 30 if set to monthly,
     * to get an average daily budget.
     * @return An average daily budget.
     */
    public float getRemainingWeeklyBudget() {
        if (duration.equals("daily")) {
            return this.remainingBudget * 5;
        } else if (duration.equals("monthly")) {
            return this.remainingBudget / 4;
        } else {
            return this.remainingBudget;
        }
    }

    /**
     * Resets any spending of the budget if the duration of the budget has been reached.
     * @return A new budget.
     */
    public Budget reset() throws ParseException {
        return new Budget(this.totalBudget, this.duration);
    }

    public float getTotalBudget() {
        return this.totalBudget;
    }

    public float getRemainingBudget() {
        return this.remainingBudget;
    }

    public String getDuration() {
        return this.duration;
    }

    public LocalDate getDateOfCreation() {
        return this.dateOfCreation;
    }

    public void setRemainingBudget(float expenses) {
        this.remainingBudget -= expenses;
    }

    public boolean isDefaultBudget() {
        return this.totalBudget == Float.MAX_VALUE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Budget // instanceof handles nulls
                && totalBudget == (((Budget) other).totalBudget)); // state check
    }

    @Override
    public int hashCode() {
        return Float.valueOf(totalBudget).hashCode();
    }




}
