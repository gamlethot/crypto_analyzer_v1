package DRAFT_Crypt;

public class DRAFTArrayAlphabet {

    public static char[] alphabetCharArray = alphabetToCharArray();
    public static String inComeText = "ТУТ БУДЕТ ТЕКСТ! Исходный текст тут. Он должен быть объемным и содержать точки, и запятые";

    private static String alphabet() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch = 'а'; ch <= 'я'; ++ch) {
            stringBuilder.append(ch);
        }
        stringBuilder.append(".,”:-!? ");
        return stringBuilder.toString();
    }

    private static char[] alphabetToCharArray() {
        return alphabet().toCharArray();
    }

    private static String stringCryptographer(String inComeText, int key) {
        StringBuilder sb = new StringBuilder();
        char[] inComeTextCharArray = inComeText.toCharArray();
        for (int i = 0; i < inComeTextCharArray.length; i++) {
            int letterChecker = 0;
            //   System.out.println("Буква "+inComeTextCharArray[i]+" большая? - "+ Character.isUpperCase(inComeTextCharArray[i]));
            if (Character.isUpperCase(inComeTextCharArray[i])) {
                letterChecker = 1;
            }
            for (int j = 0; j < alphabetCharArray.length; j++) {
                if (letterChecker == 1) {
                    inComeTextCharArray[i] = Character.toLowerCase(inComeTextCharArray[i]);
                    if (inComeTextCharArray[i] == alphabetCharArray[j]) {
                        char c = alphabetCharArray[(j + key) % alphabetCharArray.length];
                        sb.append(Character.toUpperCase(c));
                    }
                } else if (inComeTextCharArray[i] == alphabetCharArray[j]) {
                    sb.append(alphabetCharArray[(j + key) % alphabetCharArray.length]);
                }
            }
        }
        return sb.toString();
    }

    private static void fixedKeyDecryptor(String CryptedText, int key) {
        StringBuilder sb = new StringBuilder();
        int letterchecker = 0;
        char[] CryptedTextArray = CryptedText.toCharArray();
        for (int i = 0; i < CryptedTextArray.length; i++) {
            for (int j = 0; j < alphabetCharArray.length; j++) {
                if (Character.isUpperCase(CryptedTextArray[i])) {
                    char temp = Character.toLowerCase(CryptedTextArray[i]);
                    if (temp == alphabetCharArray[j]) {
                        if ((j - key) % alphabetCharArray.length < 0) {
                            char c = alphabetCharArray[(j + alphabetCharArray.length - key) % alphabetCharArray.length];
                            sb.append(Character.toUpperCase(c));
                        } else {
                            char c = alphabetCharArray[(j - key) % alphabetCharArray.length];
                            sb.append(Character.toUpperCase(c));
                        }
                    }
                } else {
                    if (CryptedTextArray[i] == alphabetCharArray[j]) {
                        if ((j - key) % alphabetCharArray.length < 0) {
                            char c = alphabetCharArray[(j + alphabetCharArray.length - key) % alphabetCharArray.length];
                            sb.append(c);
                        } else {
                            char c = alphabetCharArray[(j - key) % alphabetCharArray.length];
                            sb.append(c);
                        }
                    }
                }
            }
        }
        System.out.println(sb);
    }


    private static void testDecryptor(String incomeCryptoText) {

        char[] inComeCryptoTextCharArray = incomeCryptoText.toCharArray();
        int key = 0;
        String patternDot = ".*\\.\\s[А-Я].*";
        String patternComa = ".*,\\s[а-я].*";
        while (key < alphabetCharArray.length) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < inComeCryptoTextCharArray.length; i++) {
                int letterChecker = 0;
                if (Character.isUpperCase(inComeCryptoTextCharArray[i])) {
                    letterChecker = 1;
                }
                for (int j = 0; j < alphabetCharArray.length; j++) {
                    if (letterChecker == 1) {
                        char tempChar = Character.toLowerCase(inComeCryptoTextCharArray[i]);
                        if (tempChar == alphabetCharArray[j]) {
                            char c = alphabetCharArray[(j + key) % alphabetCharArray.length];
                            sb.append(Character.toUpperCase(c));
                        }
                    } else if (inComeCryptoTextCharArray[i] == alphabetCharArray[j]) {
                        sb.append(alphabetCharArray[(j + key) % alphabetCharArray.length]);
                    }
                }
            }
            if ((sb.toString().matches(patternDot)) && (sb.toString().matches(patternComa))) {
                System.out.println(sb);
            }
            key++;
        }
    }


    public static void main(String[] args) {
        System.out.println("АЛФАВИТ: ");
        System.out.println(alphabetCharArray);
        System.out.println("\nВходящий текст");
        System.out.println(inComeText);
        System.out.println("\nЗашифрованный текст: ");
        String cryptoText = stringCryptographer(inComeText, 3);
        System.out.println(cryptoText);
        System.out.println("\nDecrypt with bruteforce: ");
        testDecryptor(cryptoText);
        System.out.println();
        System.out.println("Decrypt with fixed same key:");
        fixedKeyDecryptor(cryptoText, 3);
    }
}
