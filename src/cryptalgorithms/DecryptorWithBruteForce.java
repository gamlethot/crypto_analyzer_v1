package cryptalgorithms;

import constants.Warnings;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecryptorWithBruteForce {

    private final char[] alphabetCharArray;
    private final String regexDotSpaceUpperCase = "\\.\\s[А-Я]";
    private final String regexComaSpaceLowerCase = "[а-я],\\s[а-я]";
    private final Pattern dotPattern = Pattern.compile(regexDotSpaceUpperCase);
    private final Pattern comaPattern = Pattern.compile(regexComaSpaceLowerCase);

    public DecryptorWithBruteForce(char[] alphabetCharArray) {
        this.alphabetCharArray = alphabetCharArray;
    }

    public String bruteForceDecryptor(String incomingCryptoText) {
        char[] incomingCryptoTextCharArray = incomingCryptoText.toCharArray();
        int offsetKey = 0;
        String result = "Text doesn't contain test criterion (dots and comas)";
        Map<Integer, String> decryptedMap = new HashMap();
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
            decryptedMap.put(offsetKey, sb.toString());
            offsetKey++;
        }

        Map<Integer, String> filteredOneDecryptedMap = new HashMap<>();
        Map<Integer, String> filteredTwoDecryptedMap = new HashMap<>();
        Map<Integer, String> NonFilteredDecryptedMap = new HashMap<>();
        for (int key :
                decryptedMap.keySet()) {
            String value = decryptedMap.get(key);
            Matcher dotMatcher = dotPattern.matcher(value);
            Matcher comaMatcher = comaPattern.matcher(value);
            if (dotMatcher.find() && comaMatcher.find()) {
                filteredOneDecryptedMap.put(key, value);
            } else if (dotMatcher.find() || comaMatcher.find()) {
                filteredTwoDecryptedMap.put(key, value);
            } else {
                //System.out.println("None fits criterion. All variants added.\n");
                NonFilteredDecryptedMap.put(key, value);
            }
        }

        if (filteredOneDecryptedMap.size() > 1) {
            System.out.println("More than 1 variant fits. Choose one manually:");
            printMap(filteredOneDecryptedMap);
            result = chooseCorrectBForceResult(filteredOneDecryptedMap, result);
        } else if (filteredOneDecryptedMap.size() == 1){
            for (var entry : filteredOneDecryptedMap.entrySet()) {
                System.out.println("Your OFFSET KEY is: "+(entry.getKey()));
                result = entry.getValue();
            }
        }
        else if (filteredOneDecryptedMap.isEmpty() && filteredTwoDecryptedMap.size()>0){
            System.out.println("More than 1 variant fits. Choose one manually:");
            printMap(filteredTwoDecryptedMap);
            result = chooseCorrectBForceResult(filteredTwoDecryptedMap, result);
        }
        else if (filteredOneDecryptedMap.isEmpty() && filteredTwoDecryptedMap.size()==1){
            for (var entry : filteredTwoDecryptedMap.entrySet()) {
                System.out.println("Your OFFSET KEY is: "+(entry.getKey()));
                result = entry.getValue();
            }
        }
        else if (filteredOneDecryptedMap.isEmpty() && filteredTwoDecryptedMap.isEmpty() && NonFilteredDecryptedMap.size()>0){
            System.out.println("More than 1 variant fits. Choose one manually:");
            printMap(NonFilteredDecryptedMap);
            result = chooseCorrectBForceResult(NonFilteredDecryptedMap, result);
        }
        return result;
    }

    private String chooseCorrectBForceResult(Map<Integer, String> filteredOneDecryptedMap, String result) {
        boolean choiceRightChecker = false;
        do {
            Scanner scanner = new Scanner(System.in);
            if (!scanner.hasNextInt()) {
                System.out.println(Warnings.ONLYNUM);
            } else {
                int selectedKey = scanner.nextInt();
                if (filteredOneDecryptedMap.containsKey(selectedKey)) {
                    choiceRightChecker=true;
                    System.out.println("Your OFFSET KEY is: "+(selectedKey));
                    result = filteredOneDecryptedMap.get(selectedKey);
                } else {
                    System.out.println(Warnings.VALIDNUM);
                }
            }
        } while (!choiceRightChecker);
        return result;
    }

    private void printMap(Map<Integer, String> filteredOneDecryptedMap) {
        for (var entry : filteredOneDecryptedMap.entrySet()) {
            System.out.println(entry.getKey() + "   :   " + entry.getValue().substring(0, 100) +"......");
        }
    }
}
   /*
    public String bruteForceDecryptor(String incomingCryptoText) {
        char[] incomingCryptoTextCharArray = incomingCryptoText.toCharArray();
        int offsetKey = 0;
        String result = "Text doesn't contain test criterion (dots and comas)";


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
    */
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


