package dev.id2r.chatlog.common.connection;

/**
 * @author Akram Louze
 */
public enum ConnectionState {

    /**
     * This will be the default value of return, will be never queried
     * @since 1.0-BETA
     */
    DUMMY,

    /**
     * Connection will return to <b>SUCCESS</b> when the connection works fine
     * @since 1.0-BETA
     */
    SUCCESS,

    /**
     * When the connection takes too long, it will return to <b>TIMED_OUT</b>
     * @since 1.0-BETA
     */
    TIMED_OUT,

    /**
     * If it throws an exception but the reason is unspecified, it will return to <b>FAILED</b>
     * @since 1.0-BETA
     */
    FAILED,

    /**
     * If the library of connection is missing, it will return to <b>LIBRARY_MISSING</b>
     * @since 1.0-BETA
     */
    LIBRARY_MISSING

}