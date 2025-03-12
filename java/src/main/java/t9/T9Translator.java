package t9;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;



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
     * 8 -> tuv
     * 9 -> wxyz
     * 0 -> a space
     * space -> a "pause"
     * A space is used to represent some time between two presses of the same button.
     * For instance,  to write "hihi": "44 444 44 444"
     * Pauses can be repeated multiple time, including between two different key presses and should not impact output
     */
    public String translate(InputStream stream) throws IOException {
        // Initialization of the map that contains the conversions (from string to character) (example "444" -> "i")
        HashMap<String, Character> conversionMap = conversionMapInitialize();

        // StringBuilder to build the current keypresses (chars) between "letters" and another one to build the message
        StringBuilder pressSequence = new StringBuilder();
        StringBuilder messageBuilder = new StringBuilder();
        int byteRead;
        char previousByteValue = '\u0000';

        // Process the stream
        while ((byteRead = stream.read()) != -1) {
            // If we reach a "pause" the current characters contained in the pressSequence are converted to the corresponding character and added to the StringBuilder containing the decoded message
            if ((char) byteRead == ' '){
                if(pressSequence.length() != 0){
                    messageBuilder.append(conversionMap.get(pressSequence.toString()));
                    pressSequence.setLength(0);
                }
            }
            // If we press a new key the current characters contained in the pressSequence are converted to the corresponding character and added to the StringBuilder containing the decoded message
            else if(((char) byteRead != previousByteValue)){
                if(previousByteValue != '\u0000' && pressSequence.length() != 0){
                    messageBuilder.append(conversionMap.get(pressSequence.toString()));
                    pressSequence.setLength(0);
                }
                pressSequence.append((char) byteRead);
                previousByteValue = (char) byteRead;
            }
            // If we pressed the same key again the corresponding byte is added to the pressSequence
            else{
                pressSequence.append((char) byteRead);
            }
        }

        // Process any remaining characters in the queue
        if (pressSequence.length() != 0) {
            messageBuilder.append(conversionMap.get(pressSequence.toString()));
        }
        return messageBuilder.toString();
    }
    private HashMap<String, Character> conversionMapInitialize() {
        HashMap<String, Character> conversionMap = new HashMap<>();

        String[] keys = {
                "2", "22", "222",
                "3", "33", "333",
                "4", "44", "444",
                "5", "55", "555",
                "6", "66", "666",
                "7", "77", "777", "7777",
                "8", "88", "888",
                "9", "99", "999", "9999",
                "0"
        };

        Character[] values = {
                'a', 'b', 'c',
                'd', 'e', 'f',
                'g', 'h', 'i',
                'j', 'k', 'l',
                'm', 'n', 'o', 'p',
                'q', 'r', 's',
                't', 'u', 'v',
                'w', 'x', 'y', 'z',
                ' '  // space for 0
        };

        for (int i = 0; i < keys.length; i++) {
            conversionMap.put(keys[i], values[i]);
        }
        return conversionMap;
    }
}
