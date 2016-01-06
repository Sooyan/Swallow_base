package soo.swallow.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.res.Resources;
import android.view.View;

/**
 * Help to finds a view that was identified by the id and cast to the type which you want
 *
 * @author Soo
 */
public class ViewFinder {

    /**Find view by id in activity
     * @param activity The view belong to
     * @param id The view`s id
     * @param <V> The type that view will be cast to
     * @return Instance of dest type
     * @throws ClassCastException There is a view that can`t be cast to dest type
     * @throws Resources.NotFoundException There is no view has be found by id
     */
    @SuppressWarnings("unchecked")
    public static <V extends View> V findViewById(Activity activity, int id) {
        View view = activity.findViewById(id);
        if (view != null) {
            try {
                V v = (V) view;
                return v;
            } catch (Exception e) {
                throw new ClassCastException("The view （id=" + id + ") can`t be cast to dest class");
            }
        }
        throw new Resources.NotFoundException("No view has been found which id is:" + id);
    }

    /**Find view by id in parent
     * @param parent The view belong to
     * @param id The view`s id
     * @param <V> The type that view will be cast to
     * @return Instance of dest type
     * @throws ClassCastException There is a view that can`t be cast to dest type
     * @throws Resources.NotFoundException There is no view has be found by id
     */
    @SuppressWarnings("unchecked")
    public static <V extends View> V findViewById(View parent, int id) {
        View view = parent.findViewById(id);
        if (view != null) {
            try {
                V v = (V) view;
                return v;
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassCastException("The view （id=" + id + ") can`t be cast to dest class");
            }
        }
        throw new Resources.NotFoundException("No view has been found which id is:" + id);
    }

    /**Find view by id in dialog
     * @param dialog The view belong to
     * @param id The view`s id
     * @param <V> The type that view will be cast to
     * @return Instance of dest type
     * @throws ClassCastException There is a view that can`t be cast to dest type
     * @throws Resources.NotFoundException There is no view has be found by id
     */
    @SuppressWarnings("unchecked")
    public static <V extends View> V findViewById(Dialog dialog, int id) {
        View view = dialog.findViewById(id);
        if (view != null) {
            try {
                V v = (V) view;
                return v;
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassCastException("The view （id=" + id + ") can`t be cast to dest class");
            }
        }
        throw new Resources.NotFoundException("No view has been found which id is:" + id);
    }

    /**Find view by id in fragment
     * @param fragment The view belong to
     * @param id The view`s id
     * @param <V> The type that view will be cast to
     * @return Instance of dest type
     * @throws ClassCastException There is a view that can`t be cast to dest type
     * @throws Resources.NotFoundException There is no view has be found by id
     */
    public static <V extends View> V findViewById(Fragment fragment, int id) {
        View view = fragment.getView();
        return findViewById(view, id);
    }
}
