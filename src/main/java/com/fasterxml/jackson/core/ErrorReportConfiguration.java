package com.fasterxml.jackson.core;

import java.io.Serializable;

/**
 * Container for configuration values used when handling errorneous token inputs. 
 * For example, unquoted text segments.
 * <p>
 * Currently default settings are
 * <ul>
 *     <li>Maximum length of token to include in error messages (see {@link #_maxErrorTokenLength})
 *     <li>Maximum length of raw content to include in error messages (see {@link #_maxRawContentLength})
 * </ul>
 *
 * @since 2.16
 */
public class ErrorReportConfiguration
        implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Default value for {@link #_maxErrorTokenLength}.
     */
    public static final int DEFAULT_MAX_ERROR_TOKEN_LENGTH = 256;

    /**
     * Previous was {@link com.fasterxml.jackson.core.io.ContentReference#DEFAULT_MAX_CONTENT_SNIPPET}.
     * Default value for {@link #_maxRawContentLength}.
     */
    public static final int DEFAULT_MAX_RAW_CONTENT_LENGTH = 500;
    
    /**
     * Maximum length of token to include in error messages
     *
     * @see Builder#maxErrorTokenLength(int)
     */
    protected final int _maxErrorTokenLength;

    /**
     * Maximum length of raw content to include in error messages
     * 
     * @see Builder#maxRawContentLength(int) 
     */
    protected final int _maxRawContentLength;

    private static ErrorReportConfiguration DEFAULT =
            new ErrorReportConfiguration(DEFAULT_MAX_ERROR_TOKEN_LENGTH, DEFAULT_MAX_RAW_CONTENT_LENGTH);

    public static void overrideDefaultErrorReportConfiguration(final ErrorReportConfiguration errorReportConfiguration) {
        if (errorReportConfiguration == null) {
            DEFAULT = new ErrorReportConfiguration(DEFAULT_MAX_ERROR_TOKEN_LENGTH, DEFAULT_MAX_RAW_CONTENT_LENGTH);
        } else {
            DEFAULT = errorReportConfiguration;
        }
    }
    
    /*
    /**********************************************************************
    /* Builder
    /**********************************************************************
     */

    public static final class Builder {
        private int maxErrorTokenLength;
        private int maxRawContentLength;

        /**
         * @param maxErrorTokenLength Constraints
         * @return This factory instance (to allow call chaining)
         * @throws IllegalArgumentException if {@code maxErrorTokenLength} is less than 0
         */
        public Builder maxErrorTokenLength(final int maxErrorTokenLength) {
            validateMaxErrorTokenLength(maxErrorTokenLength);
            this.maxErrorTokenLength = maxErrorTokenLength;
            return this;
        }

        /**
         * 
         * @see ErrorReportConfiguration#_maxRawContentLength
         * @return This factory instance (to allow call chaining)
         */
        public Builder maxRawContentLength(final int maxRawContentLength) {
            validateMaxRawContentLength(maxRawContentLength);
            this.maxRawContentLength = maxRawContentLength;
            return this;
        }
        
        Builder() {
            this(DEFAULT_MAX_ERROR_TOKEN_LENGTH, DEFAULT_MAX_RAW_CONTENT_LENGTH);
        }

        Builder(final int maxErrorTokenLength, final int maxRawContentLength) {
            this.maxErrorTokenLength = maxErrorTokenLength;
            this.maxRawContentLength = maxRawContentLength;
        }

        Builder(ErrorReportConfiguration src) {
            this.maxErrorTokenLength = src._maxErrorTokenLength;
            this.maxRawContentLength = src._maxRawContentLength;
        }

        public ErrorReportConfiguration build() {
            return new ErrorReportConfiguration(maxErrorTokenLength, maxRawContentLength);
        }
    }
    
    /*
    /**********************************************************************
    /* Life-cycle
    /**********************************************************************
     */

    protected ErrorReportConfiguration(final int maxErrorTokenLength, final int maxRawContentLength) {
        _maxErrorTokenLength = maxErrorTokenLength;
        _maxRawContentLength = maxRawContentLength;
    }

    public static ErrorReportConfiguration.Builder builder() {
        return new ErrorReportConfiguration.Builder();
    }

    /**
     * @return the default {@link ErrorReportConfiguration} (when none is set on the {@link JsonFactory} explicitly)
     * @see #overrideDefaultErrorReportConfiguration(ErrorReportConfiguration)
     */
    public static ErrorReportConfiguration defaults() {
        return DEFAULT;
    }

    /**
     * @return New {@link ErrorReportConfiguration.Builder} initialized with settings of configuration
     * instance
     */
    public ErrorReportConfiguration.Builder rebuild() {
        return new ErrorReportConfiguration.Builder(this);
    }
    
    /*
    /**********************************************************************
    /* Accessors
    /**********************************************************************
     */

    /**
     * Accessor for {@link #_maxErrorTokenLength}
     *
     * @return Maximum length of token to include in error messages
     * @see Builder#maxErrorTokenLength(int)
     */
    public int getMaxErrorTokenLength() {
        return _maxErrorTokenLength;
    }

    /**
     * Accessor for {@link #_maxRawContentLength}
     *
     * @return Maximum length of token to include in error messages
     * @see Builder#maxRawContentLength
     */
    public int getMaxRawContentLength() {
        return _maxRawContentLength;
    }

    /*
    /**********************************************************************
    /* Convenience methods for validation
    /**********************************************************************
     */

    /**
     * Convenience method that can be used verify valid {@link #_maxErrorTokenLength}.
     * If invalid value is passed in, {@link IllegalArgumentException} is thrown.
     *
     * @param maxErrorTokenLength Maximum length of token to include in error messages
     */
    private static void validateMaxErrorTokenLength(int maxErrorTokenLength) throws IllegalArgumentException {
        if (maxErrorTokenLength < 0) {
            throw new IllegalArgumentException(
                    String.format("Value of maxErrorTokenLength (%d) cannot be negative", maxErrorTokenLength));
        }
    }

    private static void validateMaxRawContentLength(int maxRawContentLength) {
        if (maxRawContentLength < 0) {
            throw new IllegalArgumentException(
                    String.format("Value of maxRawContentLength (%d) cannot be negative", maxRawContentLength));
        }
    }

}
