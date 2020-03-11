package seedu.foodiebot.model.budget;

import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_DAY;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_WEEK;

import java.time.LocalDate;
import java.util.Objects;

import seedu.foodiebot.commons.core.date.DateRange;
import seedu.foodiebot.commons.core.date.DefiniteDate;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/**
 * Represents a person's budget.
 */
public class Budget {
    private static final String DAILY = PREFIX_DATE_BY_DAY.toString();
    private static final String WEEKLY = PREFIX_DATE_BY_WEEK.toString();
    private static final String MONTHLY = PREFIX_DATE_BY_MONTH.toString();

    private final float totalBudget;
    private float remainingBudget;
    private final String duration;
    private final LocalDate dateOfCreation;
    private DateRange cycleRange;


    /**
     * Constructs a {@code Budget} object. */
    public Budget(float totalBudget, float remainingBudget,
                  String duration, LocalDate dateOfCreation, DateRange cycleRange) {
        this.totalBudget = totalBudget;
        this.remainingBudget = remainingBudget;
        this.duration = duration;
        this.dateOfCreation = dateOfCreation;
        this.cycleRange = cycleRange;
    }

    public Budget(float totalBudget, float remainingBudget,
                  String duration, LocalDate dateOfCreation) {
        this.totalBudget = totalBudget;
        this.remainingBudget = remainingBudget;
        this.duration = duration;
        this.dateOfCreation = dateOfCreation;
        this.cycleRange = setCycleRange(duration);
    }

    public Budget(float totalBudget, float remainingBudget,
                  String duration, LocalDate dateOfCreation, LocalDate startDate, LocalDate endDate) {
        this.totalBudget = totalBudget;
        this.remainingBudget = remainingBudget;
        this.duration = duration;
        this.dateOfCreation = dateOfCreation;

        DateRange range;
        try {
            range = DateRange.of(startDate, endDate);
        } catch (ParseException pe) {
            range = null;
        }
        this.cycleRange = range;
    }


    /**
     * Constructs a {@code Budget} object.
     * @param totalBudget The value of the budget.
     * @param duration The duration cycle of the budget.
     */
    public Budget(float totalBudget, String duration) {
        this(totalBudget, totalBudget, duration, DefiniteDate.TODAY);
    }

    public Budget() {
        this(Float.MAX_VALUE, DAILY);
    }

    /** Sets a DateRange based on the duration of the budget cycle and the system date. */
    private DateRange setCycleRange(String duration) {
        try {
            if (duration.equals(DAILY)) {
                return DateRange.ofSingle(DefiniteDate.TODAY);

            } else if (duration.equals(WEEKLY)) {
                return DateRange.of(DefiniteDate.TODAY, DefiniteDate.TODAY.plusWeeks(1).minusDays(1));

            } else if (duration.equals(MONTHLY)) {
                return DateRange.of(DefiniteDate.TODAY, DefiniteDate.TODAY.plusMonths(1).minusDays(1));

            }

        } catch (ParseException pe) {
            return null;
        }
        return null;
    }

    /** Resets the remaining budget to  */
    public void resetRemainingBudget() {
        this.remainingBudget = this.totalBudget;
        this.cycleRange = setCycleRange(this.duration);
    }

    /**
     * Multiplies the remaining budget by 5 if set to daily, or divide by 4 if set to monthly,
     * to get an average daily budget.
     * @return An average daily budget.
     */
    public float getRemainingWeeklyBudget() {
        if (duration.equals(DAILY)) {
            return this.remainingBudget * 5;
        } else if (duration.equals(MONTHLY)) {
            return this.remainingBudget / 4;
        } else {
            return this.remainingBudget;
        }
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

    public String getDurationAsString() {
        return this.duration.equals(DAILY)
                ? "daily"
                : this.duration.equals(WEEKLY)
                    ? "weekly"
                    : "monthly";
    }

    public LocalDate getDateOfCreation() {
        return this.dateOfCreation;
    }

    public DateRange getCycleRange() {
        return this.cycleRange;
    }

    public void setRemainingBudget(float expenses) {
        this.remainingBudget -= expenses;
    }

    public boolean isDefaultBudget() {
        return this.totalBudget == Float.MAX_VALUE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.getTotalBudget() == totalBudget
                && otherBudget.getRemainingBudget() == remainingBudget
                && otherBudget.getDuration().equals(duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalBudget, remainingBudget, duration, dateOfCreation, cycleRange);
    }




}
