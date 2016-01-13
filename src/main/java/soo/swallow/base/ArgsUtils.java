package soo.swallow.base;

/**
 * Created by Soo.
 */
public class ArgsUtils {
    private static final String TAG = "ArgsUtils--->";

    /**Throw NullPointerException if the particular object is null
     * @param object The particular object
     * @see #notNull(Object, String)
     */
    public static void notNull(Object object) {
        notNull(object, null);
    }

    /**Throw NullPointerException if the particular object is null
     * @param object The particular object
     * @param message The message which to be wrapped into NullPointerException
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new NullPointerException(message == null ? "" : message);
        }
    }
}
