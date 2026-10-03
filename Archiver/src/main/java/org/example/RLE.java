package org.example;

public class RLE {
    public String encode(String input) {
        StringBuilder encodedString = new StringBuilder();
        int count;
        for (int i = 0; i < input.length(); i++) {
            count = 1;
            if (Character.isDigit(input.charAt(i))) {
                if (input.charAt(i) == input.charAt(i + 1)) {
                    while (i < input.length() - 1 && input.charAt(i) == input.charAt(i + 1)) {
                        count++;
                        i++;
                    }
                    if (count == 1) {
                        encodedString.append("|");
                        encodedString.append(input.charAt(i));
                        encodedString.append("|");
                    }
                    else{
                        encodedString.append("|");
                        encodedString.append(input.charAt(i));
                        encodedString.append("|");
                        encodedString.append(count);
                    }
                }
            }
            else {
                while (i < input.length() - 1 && input.charAt(i) == input.charAt(i + 1)) {
                    count++;
                    i++;
                }
                if (count == 1) {
                    encodedString.append(input.charAt(i));
                } else {
                    encodedString.append(input.charAt(i)).append(count);
                }
            }
        }
        return encodedString.toString();
    }

    public String decode(String input) {
        StringBuilder decodedString = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if (i != input.length() - 1) {
                i++;
                int count;
                if (character == '|') {
                    if (Character.isDigit(input.charAt(i + 3))) {
                        count = Character.getNumericValue(input.charAt(i + 3));
                        character = input.charAt(i + 1);
                        i+=3;
                    }
                    else {
                        count = 1;
                        character = input.charAt(i + 1);
                        i+=2;
                    }
                } else if (!Character.isDigit(character)) {
                    count = 1;
                    i--;
                }
                else {
                    count = Character.getNumericValue(character);
                }
                for (int j = 0; j < count; j++) {
                    decodedString.append(character);
                }
            }
            else {
                decodedString.append(character);
            }
        }
        return decodedString.toString();
    }
}
