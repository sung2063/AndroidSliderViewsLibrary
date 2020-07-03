package com.sung2063.sliders.exceptions;

/**
 * The SlideOutOfBoundException is for handling num of slide does not meet the condition.
 *
 * @author  Sung Hyun Back
 * @version 1.0
 * @since   2020-07-02
 */
public class SlideOutOfBoundException extends Exception {

    public SlideOutOfBoundException(String message) {
        super(message);
    }
}
