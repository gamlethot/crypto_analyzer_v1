package cryptalgorithms;

public class CryptorWithOffsetKey {

    private final char[] alphabetCharArray;

    public CryptorWithOffsetKey(char[] alphabetCharArray){
        this.alphabetCharArray=alphabetCharArray;
    }

    public String cryptTextWithOffsetKey(String incomingText, int offsetKey) {
        StringBuilder sb = new StringBuilder();
        char[] incomingTextCharArray = incomingText.toCharArray();
        for (char value : incomingTextCharArray) {
            if (value == '\n') {
                sb.append('\n');
                continue;
            }
            for (int j = 0; j < alphabetCharArray.length; j++) {
                if (Character.isUpperCase(value)) {
                    char temp = Character.toLowerCase(value);
                    if (temp == alphabetCharArray[j]) {
                        char c = alphabetCharArray[(j + offsetKey) % alphabetCharArray.length];
                        sb.append(Character.toUpperCase(c));
                    }
                } else if (value == alphabetCharArray[j]) {
                    sb.append(alphabetCharArray[(j + offsetKey) % alphabetCharArray.length]);
                }
            }
        }
        return sb.toString();
    }

}
