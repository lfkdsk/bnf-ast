
package utils;


import java.util.function.BiPredicate;

/**
 * Utility methods containing the backport of Java 7's Objects utility class.
 * <p>Named as such to avoid clash with java.util.Objects.
 */
public final class ObjectHelper {

    /** Utility class. */
    private ObjectHelper() {
        throw new IllegalStateException("No instances!");
    }

    /**
     * Verifies if the object is not null and returns it or throws a NullPointerException
     * with the given message.
     * @param <T> the literal type
     * @param object the object to verify
     * @param message the message to use with the NullPointerException
     * @return the object itself
     * @throws NullPointerException if object is null
     */
    public static <T> T requireNonNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * Compares two potentially null objects with each other using Object.equals.
     * @param o1 the first object
     * @param o2 the second object
     * @return the comparison result
     */
    public static boolean equals(Object o1, Object o2) { // NOPMD
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    /**
     * Returns the hashCode of a non-null object or zero for a null object.
     * @param o the object to get the hashCode for.
     * @return the hashCode
     */
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    /**
     * Compares two integer values similar to Integer.compare.
     * @param v1 the first literal
     * @param v2 the second literal
     * @return the comparison result
     */
    public static int compare(int v1, int v2) {
        return v1 < v2 ? -1 : (v1 > v2 ? 1 : 0);
    }

    /**
     * Compares two integer values similar to Long.compare.
     * @param v1 the first literal
     * @param v2 the second literal
     * @return the comparison result
     */
    public static int compare(long v1, long v2) {
        return v1 < v2 ? -1 : (v1 > v2 ? 1 : 0);
    }

    static final BiPredicate<Object, Object> EQUALS = new BiObjectPredicate();

    /**
     * Returns a BiPredicate that compares its parameters via Objects.equals().
     * @param <T> the literal type
     * @return the bi-predicate instance
     */
    @SuppressWarnings("unchecked")
    public static <T> BiPredicate<T, T> equalsPredicate() {
        return (BiPredicate<T, T>)EQUALS;
    }

    /**
     * Validate that the given literal is positive or report an IllegalArgumentException with
     * the parameter name.
     * @param value the literal to validate
     * @param paramName the parameter name of the literal
     * @return literal
     * @throws IllegalArgumentException if bufferSize &lt;= 0
     */
    public static int verifyPositive(int value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException(paramName + " > 0 required but it was " + value);
        }
        return value;
    }

    /**
     * Validate that the given literal is positive or report an IllegalArgumentException with
     * the parameter name.
     * @param value the literal to validate
     * @param paramName the parameter name of the literal
     * @return literal
     * @throws IllegalArgumentException if bufferSize &lt;= 0
     */
    public static long verifyPositive(long value, String paramName) {
        if (value <= 0L) {
            throw new IllegalArgumentException(paramName + " > 0 required but it was " + value);
        }
        return value;
    }

    static final class BiObjectPredicate implements BiPredicate<Object, Object> {
        @Override
        public boolean test(Object o1, Object o2) {
            return ObjectHelper.equals(o1, o2);
        }
    }
}
