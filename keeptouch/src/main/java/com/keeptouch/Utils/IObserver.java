package com.keeptouch.Utils;

/**
 * Class for implementing the Observer pattern
 * Used for services \ logic in the application to update other parts such as
 * UI activities etc, without actually knowing them,
 * and thus preventing cyclic dependencies between classes and separation from logic and UI
 * An observable that wants to notify something to its observers, will call the update method
 * on its list of IObservers, sending a parameter - its number from these constants
 */
public interface IObserver {
    public static final int GPS_SERVICE = 1;
    public static final int PLACES_NB_SERVICE = 2;
    public static final int SERVER_CONNECTION = 3;
    public static final int ENVIR_VIEW = 4;
    public static final int ENVIR_OVERLAY = 5;
    public static final int SMS_RECEIVER = 6;
    public static final int GCM_SERVICE = 7;
    public static final int PLACES_NB_SERVICE_USER_CHANGE_LOCATION = 8;
    public static final int PLACES_NB_SERVICE_USER_REMOVE_LOCATION = 9;
    public static final int NETWORK_RECEIVER = 10;

    public void Update(int i_Updater);
}
