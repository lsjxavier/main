package seedu.foodiebot.commons.core.date;

/** Enumeration of different ways to resolve a date range. */
public enum DateRangeStyle {

    /** Style to resolve a date range leniently. */
    LENIENT,

    /** Style to resolve a date range smartly. */
    SMART,

    /** Style to resolve a date range strictly. */
    STRICT;
}
