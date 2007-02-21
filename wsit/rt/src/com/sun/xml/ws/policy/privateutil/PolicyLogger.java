/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the license at
 * https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2006 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.xml.ws.policy.privateutil;

import java.lang.reflect.Field;
import java.util.logging.Level;

import static com.sun.xml.ws.policy.privateutil.PolicyUtils.Commons.getCallerMethodName;
/**
 * This is a helper class that provides some conveniece methods wrapped around the
 * standard {@link java.util.logging.PolicyLogger} interface.
 *
 * @author Marek Potociar
 */
public final class PolicyLogger {
    /**
     * If we run with JAX-WS, we are using its logging domain (appended with ".wspolicy").
     * Otherwise we default to "wspolicy".
     */
    private static final String LOGGING_SUBSYSTEM_NAME;
    private static final Level METHOD_CALL_LEVEL_VALUE = Level.FINEST;
    
    static {
        String loggingSubsystemName = "wspolicy";
        try {
            // Looking up JAX-WS class at run-time, so that we don't need to depend
            // on it at compile-time.
            Class jaxwsConstants = Class.forName("com.sun.xml.ws.util.Constants");
            Field loggingDomainField = jaxwsConstants.getField("LoggingDomain");
            Object loggingDomain = loggingDomainField.get(null);
            loggingSubsystemName = loggingDomain.toString().concat(".wspolicy");
        } catch (Exception e) {
            // If we don't manage to extract the logging domain from JAX-WS, we
            // fall back to a default.
        } finally {
            LOGGING_SUBSYSTEM_NAME = loggingSubsystemName;
        }
    }
    
    
    private String componentClassName;
    private java.util.logging.Logger logger;
    
    /**
     * Prevents creation of a new instance of this PolicyLogger
     */
    private PolicyLogger(final String componentName) {
        this.componentClassName = "[" + componentName + "] ";
        this.logger = java.util.logging.Logger.getLogger(LOGGING_SUBSYSTEM_NAME);
    }
    
    /**
     * The factory method returns preconfigured PolicyLogger wrapper for the class. Since there is no caching implemented,
     * it is advised that the method is called only once per a class in order to initialize a final static logger variable,
     * which is then used through the class to perform actual logging tasks.
     *
     * @param componentClass class of the component that will use the logger instance. Must not be {@code null}.
     * @return logger instance preconfigured for use with the component
     * @throws NullPointerException if the componentClass parameter is {@code null}.
     */
    public static PolicyLogger getLogger(final Class componentClass) {
        return new PolicyLogger(componentClass.getName());
    }
    
    public void log(final Level level, final String message) {
        if (!this.logger.isLoggable(level)) {
            return;
        }
        logger.logp(level, componentClassName, getCallerMethodName(), message);
    }
    
    public void log(final Level level, final String message, final Throwable thrown) {
        if (!this.logger.isLoggable(level)) {
            return;
        }
        logger.logp(level, componentClassName, getCallerMethodName(), message, thrown);
    }
    
    public void finest(final String message) {
        if (!this.logger.isLoggable(Level.FINEST)) {
            return;
        }
        logger.logp(Level.FINEST, componentClassName, getCallerMethodName(), message);
    }
    
    public void finest(final String message, final Throwable thrown) {
        if (!this.logger.isLoggable(Level.FINEST)) {
            return;
        }
        logger.logp(Level.FINEST, componentClassName, getCallerMethodName(), message, thrown);
    }
    
    public void finer(final String message) {
        if (!this.logger.isLoggable(Level.FINER)) {
            return;
        }
        logger.logp(Level.FINER, componentClassName, getCallerMethodName(), message);
    }
    
    public void finer(final String message, final Throwable thrown) {
        if (!this.logger.isLoggable(Level.FINER)) {
            return;
        }
        logger.logp(Level.FINER, componentClassName, getCallerMethodName(), message, thrown);
    }
    
    public void fine(final String message) {
        if (!this.logger.isLoggable(Level.FINE)) {
            return;
        }
        logger.logp(Level.FINE, componentClassName, getCallerMethodName(), message);
    }
    
    public void fine(final String message, final Throwable thrown) {
        if (!this.logger.isLoggable(Level.FINE)) {
            return;
        }
        logger.logp(Level.FINE, componentClassName, getCallerMethodName(), message, thrown);
    }
    
    public void info(final String message) {
        if (!this.logger.isLoggable(Level.INFO)) {
            return;
        }
        logger.logp(Level.INFO, componentClassName, getCallerMethodName(), message);
    }
    
    public void info(final String message, final Throwable thrown) {
        if (!this.logger.isLoggable(Level.INFO)) {
            return;
        }
        logger.logp(Level.INFO, componentClassName, getCallerMethodName(), message, thrown);
    }
    
    public void config(final String message) {
        if (!this.logger.isLoggable(Level.CONFIG)) {
            return;
        }
        logger.logp(Level.CONFIG, componentClassName, getCallerMethodName(), message);
    }
    
    public void config(final String message, final Throwable thrown) {
        if (!this.logger.isLoggable(Level.CONFIG)) {
            return;
        }
        logger.logp(Level.CONFIG, componentClassName, getCallerMethodName(), message, thrown);
    }
    
    public void warning(final String message) {
        if (!this.logger.isLoggable(Level.WARNING)) {
            return;
        }
        logger.logp(Level.WARNING, componentClassName, getCallerMethodName(), message);
    }
    
    public void warning(final String message, final Throwable thrown) {
        if (!this.logger.isLoggable(Level.WARNING)) {
            return;
        }
        logger.logp(Level.WARNING, componentClassName, getCallerMethodName(), message, thrown);
    }
    
    public void severe(final String message) {
        if (!this.logger.isLoggable(Level.SEVERE)) {
            return;
        }
        logger.logp(Level.SEVERE, componentClassName, getCallerMethodName(), message);
    }

    public void severe(final String message, final Throwable thrown) {
        if (!this.logger.isLoggable(Level.SEVERE)) {
            return;
        }
        logger.logp(Level.SEVERE, componentClassName, getCallerMethodName(), message, thrown);
    }
    
    public boolean isMethodCallLoggable() {
        return this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE);
    }
    
    public boolean isLoggable(Level level) {
        return this.logger.isLoggable(level);
    }
    
    public void setLevel(Level level) {
        this.logger.setLevel(level);
    }
    
    public void entering() {
        if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
            return;
        }
        
        logger.entering(componentClassName, getCallerMethodName());
    }
    
    public void entering(final Object... parameters) {
        if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
            return;
        }
        
        logger.entering(componentClassName, getCallerMethodName(), parameters);
    }
    
    public void exiting() {
        if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
            return;
        }
        logger.exiting(componentClassName, getCallerMethodName());
    }
    
    public void exiting(final Object result) {
        if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
            return;
        }
        logger.exiting(componentClassName, getCallerMethodName(), result);
    }
    
    /**
     * Method logs {@code exception}'s message as a {@code SEVERE} logging level
     * message.
     * <p/>
     * If {@code cause} parameter is not {@code null}, it is logged as well and
     * {@code exception} original cause is initialized with instance referenced
     * by {@code cause} parameter.
     *
     * @param exception exception whose message should be logged. Must not be
     *        {@code null}.
     * @param cause initial cause of the exception that should be logged as well
     *        and set as {@code exception}'s original cause. May be {@code null}.
     * @return the same exception instance that was passed in as the {@code exception}
     *         parameter.
     */
    public <T extends Throwable> T logSevereException(final T exception, final Throwable cause) {
        if (this.logger.isLoggable(Level.SEVERE)) {
            if (cause != null) {
                exception.initCause(cause);
                logger.logp(Level.SEVERE, componentClassName, getCallerMethodName(), exception.getMessage(), cause);
            } else {
                logger.logp(Level.SEVERE, componentClassName, getCallerMethodName(), exception.getMessage());
            }
        }
        
        return exception;
    }
    
    /**
     * Method logs {@code exception}'s message as a {@code SEVERE} logging level
     * message.
     * <p/>
     * If {@code logCause} parameter is {@code true}, {@code exception}'s original
     * cause is logged as well (if exists). This may be used in cases when
     * {@code exception}'s class provides constructor to initialize the original
     * cause. In such case you do not need to use
     * {@link #logException(final T, final Throwable)}
     * method version but you might still want to log the original cause as well.
     *
     * @param exception exception whose message should be logged. Must not be
     *        {@code null}.
     * @param logCause deterimnes whether initial cause of the exception should
     *        be logged as well
     * @return the same exception instance that was passed in as the {@code exception}
     *         parameter.
     */
    public <T extends Throwable> T logSevereException(final T exception, final boolean logCause) {
        if (this.logger.isLoggable(Level.SEVERE)) {
            if (logCause && exception.getCause() != null) {
                logger.logp(Level.SEVERE, componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
            } else {
                logger.logp(Level.SEVERE, componentClassName, getCallerMethodName(), exception.getMessage());
            }
        }
        
        return exception;
    }
    
    /**
     * Same as {@code #logSevereException(exception, true)}.
     */
    public <T extends Throwable> T logSevereException(final T exception) {
        if (this.logger.isLoggable(Level.SEVERE)) {
            if (exception.getCause() != null) {
                logger.logp(Level.SEVERE, componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
            } else {
                logger.logp(Level.SEVERE, componentClassName, getCallerMethodName(), exception.getMessage());
            }
        }
        
        return exception;
    }
    
    /**
     * Method logs {@code exception}'s message at the logging level specified by the
     * {@code level} argument.
     * <p/>
     * If {@code cause} parameter is not {@code null}, it is logged as well and
     * {@code exception} original cause is initialized with instance referenced
     * by {@code cause} parameter.
     *
     * @param exception exception whose message should be logged. Must not be
     *        {@code null}.
     * @param cause initial cause of the exception that should be logged as well
     *        and set as {@code exception}'s original cause. May be {@code null}.
     * @param level loging level which should be used for logging
     * @return the same exception instance that was passed in as the {@code exception}
     *         parameter.
     */
    public <T extends Throwable> T logException(final T exception, final Throwable cause, final Level level) {
        if (this.logger.isLoggable(level)) {
            if (cause != null) {
                exception.initCause(cause);
                logger.logp(level, componentClassName, getCallerMethodName(), exception.getMessage(), cause);
            } else {
                logger.logp(level, componentClassName, getCallerMethodName(), exception.getMessage());
            }
        }
        
        return exception;
    }
    
    /**
     * Method logs {@code exception}'s message at the logging level specified by the
     * {@code level} argument.
     * <p/>
     * If {@code logCause} parameter is {@code true}, {@code exception}'s original
     * cause is logged as well (if exists). This may be used in cases when
     * {@code exception}'s class provides constructor to initialize the original
     * cause. In such case you do not need to use
     * {@link #logException(final T, final Throwable)}
     * method version but you might still want to log the original cause as well.
     *
     * @param exception exception whose message should be logged. Must not be
     *        {@code null}.
     * @param logCause deterimnes whether initial cause of the exception should
     *        be logged as well
     * @param level loging level which should be used for logging
     * @return the same exception instance that was passed in as the {@code exception}
     *         parameter.
     */
    public <T extends Throwable> T logSevereException(final T exception, final boolean logCause, final Level level) {
        if (this.logger.isLoggable(level)) {
            if (logCause && exception.getCause() != null) {
                logger.logp(level, componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
            } else {
                logger.logp(level, componentClassName, getCallerMethodName(), exception.getMessage());
            }
        }
        
        return exception;
    }
    
    /**
     * Same as {@code logException(exception, true, level)}.
     */
    public <T extends Throwable> T logSevereException(final T exception, final Level level) {
        if (this.logger.isLoggable(level)) {
            if (exception.getCause() != null) {
                logger.logp(level, componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
            } else {
                logger.logp(level, componentClassName, getCallerMethodName(), exception.getMessage());
            }
        }
        
        return exception;
    }
}
