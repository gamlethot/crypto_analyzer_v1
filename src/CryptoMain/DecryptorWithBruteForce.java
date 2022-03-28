package CryptoMain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecryptorWithBruteForce {

    private final char[] alphabetCharArray;

    public DecryptorWithBruteForce(char[] alphabetCharArray) {
        this.alphabetCharArray = alphabetCharArray;
    }

    public String bruteForceDecryptor(String incomingCryptoText) {
        char[] incomingCryptoTextCharArray = incomingCryptoText.toCharArray();
        int offsetKey = 0;
        String result = "Text doesn't contain test criterion (dots and comas)";
        String regexDotSpaceUpperCase = ".\\s[А-Я]";
        String regexComaSpaceLowerCase = "\\.*,\\s[а-я]";
        Pattern dotPattern = Pattern.compile(regexDotSpaceUpperCase);
        Pattern comaPattern = Pattern.compile(regexComaSpaceLowerCase);

        while (offsetKey < alphabetCharArray.length) {
            StringBuilder sb = new StringBuilder();
            for (char cryptoCharValue : incomingCryptoTextCharArray) {
                if (cryptoCharValue == '\n') {
                    sb.append('\n');
                    continue;
                }
                for (int j = 0; j < alphabetCharArray.length; j++) {
                    if (Character.isUpperCase(cryptoCharValue)) {
                        char tempChar = Character.toLowerCase(cryptoCharValue);
                        if (tempChar == alphabetCharArray[j]) {
                            char c = alphabetCharArray[(j + offsetKey) % alphabetCharArray.length];
                            sb.append(Character.toUpperCase(c));
                        }
                    } else if (cryptoCharValue == alphabetCharArray[j]) {
                        sb.append(alphabetCharArray[(j + offsetKey) % alphabetCharArray.length]);
                    }
                }
            }
            Matcher dotMatcher = dotPattern.matcher(sb.toString());
            Matcher comaMatcher = comaPattern.matcher(sb.toString());
            if (dotMatcher.find() && comaMatcher.find()) {
                result = sb.toString();
                System.out.println("Your OFFSET KEY is: "+(alphabetCharArray.length-offsetKey));
            }
            offsetKey++;
        }
        return result;
    }

//    public String bruteForceDecryptor2(String incomingCryptoText) {
//        String result;
//        if (incomingCryptoText.length()<300){
//             result = bruteForceDecryptor(incomingCryptoText);
//        }
//        else{
//            String shortIncomingString = incomingCryptoText.substring(0, 299);
//            int offsetKey = bruteForceOffsetKeyDetector(shortIncomingString);;
//            DecryptorWithOffsetKey decryptorWithOffsetKey = new DecryptorWithOffsetKey(alphabetCharArray);
//             result = decryptorWithOffsetKey.decryptTextWithOffsetKey(incomingCryptoText, offsetKey);
//        }
//        return result;
//    }
//
//    public int bruteForceOffsetKeyDetector(String incomingCryptoText) {
//        char[] incomingCryptoTextCharArray = incomingCryptoText.toCharArray();
//        int offsetKey = 0;
//        String result = "Text doesn't contain test criterion (dots and comas)";
//        String regexDotSpaceUpperCase = ".\\s[А-Я]";
//        String regexComaSpaceLowerCase = "\\.*,\\s[а-я]";
//        Pattern dotPattern = Pattern.compile(regexDotSpaceUpperCase);
//        Pattern comaPattern = Pattern.compile(regexComaSpaceLowerCase);
//
//        while (offsetKey < alphabetCharArray.length) {
//            StringBuilder sb = new StringBuilder();
//            for (char cryptoCharValue : incomingCryptoTextCharArray) {
//                if (cryptoCharValue == '\n') {
//                    sb.append('\n');
//                    continue;
//                }
//                for (int j = 0; j < alphabetCharArray.length; j++) {
//                    if (Character.isUpperCase(cryptoCharValue)) {
//                        char tempChar = Character.toLowerCase(cryptoCharValue);
//                        if (tempChar == alphabetCharArray[j]) {
//                            char c = alphabetCharArray[(j + offsetKey) % alphabetCharArray.length];
//                            sb.append(Character.toUpperCase(c));
//                        }
//                    } else if (cryptoCharValue == alphabetCharArray[j]) {
//                        sb.append(alphabetCharArray[(j + offsetKey) % alphabetCharArray.length]);
//                    }
//                }
//            }
//            Matcher dotMatcher = dotPattern.matcher(sb.toString());
//            Matcher comaMatcher = comaPattern.matcher(sb.toString());
//            if (dotMatcher.find() && comaMatcher.find()) {
//                offsetKey = alphabetCharArray.length-offsetKey;
//            }
//            offsetKey++;
//        }
//        System.out.println("OFFSET KEYYYYY   "+offsetKey);
//        return offsetKey;
//    }

}
