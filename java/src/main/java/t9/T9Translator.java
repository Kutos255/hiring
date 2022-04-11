package t9;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class T9Translator {

    /**
     * Translate a stream of bytes containing only T9 keyboard characters to a human-readable text.
     * Only characters 2-9, 0, space are allowed as input using standard layout representation:
     * 2 -> abc
     * 3 -> def
     * 4 -> ghi
     * 5 -> jkl
     * 6 -> mno
     * 7 -> pqrs
     * 8 -> tuc
     * 9 -> wxyz
     * 0 -> a space
     * space -> a "pause"
     * A space is used to represent some time between two presses of the same button.
     * For instance,  to write "hihi": "44 444 44 444"
     * Pauses can be repeated multiple time, including between two different key presses and should not impact output
     */
    public String translate(InputStream stream) throws IOException {
        return "";
    }

}
