package CryptoMain;

public class CryptorWithOffsetKey {

    private final char[] alphabetCharArray;

    public CryptorWithOffsetKey(char[] alphabetCharArray){
        this.alphabetCharArray=alphabetCharArray;
    }

    public String cryptTextWithOffsetKey(String incomingText, int offsetKey) {
        StringBuilder sb = new StringBuilder();
        char[] incomingTextCharArray = incomingText.toCharArray();
        for (int i = 0; i < incomingTextCharArray.length; i++) {
            if (incomingTextCharArray[i]=='\n'){
                sb.append('\n');
                continue;
            }
            for (int j = 0; j < alphabetCharArray.length; j++) {

                if (Character.isUpperCase(incomingTextCharArray[i])) {
                    char temp = Character.toLowerCase(incomingTextCharArray[i]);
                    if (temp == alphabetCharArray[j]) {
                        char c = alphabetCharArray[(j + offsetKey) % alphabetCharArray.length];
                        sb.append(Character.toUpperCase(c));
                    }
                } else if (incomingTextCharArray[i] == alphabetCharArray[j]) {
                    sb.append(alphabetCharArray[(j + offsetKey) % alphabetCharArray.length]);
                }
            }
        }
        return sb.toString();
    }

}
