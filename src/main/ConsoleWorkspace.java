package main;

import alphabet.AlphabetProvider;
import constants.ColorText;
import constants.Warnings;
import cryptalgorithms.CryptorWithOffsetKey;
import cryptalgorithms.DecryptorWithBruteForce;
import cryptalgorithms.DecryptorWithOffsetKey;
import file_io.FileInputOutput;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

public class ConsoleWorkspace {

    private final FileInputOutput fileInputOutput = new FileInputOutput();
    private final AlphabetProvider alphabetProviderObj = new AlphabetProvider();
    private final int alphabetLength = alphabetProviderObj.getAlphabetCharArray().length;
    private final char[] alphabetCharArray = alphabetProviderObj.getAlphabetCharArray();

    public void printMainMenu() throws IOException {

        {
            boolean choiceRightChecker = false;
            int choice = -1;
            do {
                printEntryMenu();
                Scanner scanner = new Scanner(System.in);
                if (!scanner.hasNextInt()) {
                    System.out.println(Warnings.ONLYNUM);
                } else {
                    choice = scanner.nextInt();
                    choiceRightChecker = makeChoiceMethod(choiceRightChecker, choice);
                }
            } while (choice!=0);
        }
    }

    // МЕТОД ВЫБОРА ЗАШИФРОВАТЬ ФАЙЛ ИЛИ РАСШИФРОВАТЬ ФАЙЛ ХОЧЕТ ПОЛЬЗОВАТЕЛЬ. ВЫБИРАЕТ 1 ИЛИ 2.
    private boolean makeChoiceMethod(boolean rightChoice, int choice) throws IOException {
        if (choice==1 || choice==2) {
            rightChoice = true;
            System.out.println("Enter the PATH of your source txt file:");
            inputPathFileCheck(choice);
        }
            else if (choice == 0){
                System.out.println(Warnings.EXIT);
        }
            else {
            System.out.println(Warnings.VALIDNUM);
        }
        return rightChoice;
    }

    // МЕТОД ПРОВЕРКИ ПРАВИЛЬНОСТИ ВВОДА ДИРЕКТОРИИ ФАЙЛА И ЕГО СУЩЕСТВОВАНИЯ.
    // ДАЕТ 3 ПОПЫТКИ НЕУДАЧНОГО ВВОДА ПОСЛЕ ЧЕГО ПРОГРАМА ДОЛЖНА ЗАВЕРШАТЬСЯ
    private void inputPathFileCheck(int choice) throws IOException {
        boolean whileTrue = true;
        String textFromFile = null;
        int count = 3;
        while(true) {
            try {
                inputAndReadFile(choice);
                break;
            } catch (IOException e) {
                // handle exception
                if (count>0) {
                    System.out.println("TRY AGAIN\n You have " + count + " more tries.");
                    count--;
                }
                else if (count == 0) {
                    System.out.println(Warnings.PATHINV);
                    break;
                }
            }
        }
    }

    //МЕТОД ЧТЕНИЯ ТЕКСТА ИЗ ПЕРЕДАННОГО ФАЙЛА И ЗАПИСИ ЕГО В СТРИНГУ
    private void inputAndReadFile(int choice) throws IOException {
        Path pathOfInputFile =  fileInputOutput.inputFilePath();
        String incomingTEXT =fileInputOutput.fileTextToString(pathOfInputFile);
        System.out.println(Warnings.FILEREADSUCCSSES);
        switch (choice){
            case 1:
                offsetKeyChooser(incomingTEXT, pathOfInputFile);
                break;
            case 2:
                decryptionSTART(pathOfInputFile, incomingTEXT);
                break;
        }
    }

    private void decryptionSTART(Path pathOfInputFile, String incomingTEXT) {
        System.out.println("CHOOSE DECRYPTION METHOD: ");
        System.out.println("1 - I know my decryption key");
        System.out.println("2 - Try to decrypt with Brute Force"+Warnings.BRUTEFORCEWARNING);
        boolean choiceRightChecker = false;
        do {
            Scanner scanner = new Scanner(System.in);
            if (!scanner.hasNextInt()) {
                System.out.println(Warnings.ONLYNUM);
            } else {
                int choice = scanner.nextInt();
                switch (choice){
                    case 1 :
                        decryptWithOffsetKey(pathOfInputFile, incomingTEXT, scanner);
                        choiceRightChecker=true;
                        break;
                    case 2:
                        bruteForceDecryption(pathOfInputFile, incomingTEXT);
                        choiceRightChecker=true;
                        break;

                    default:
                        System.out.println(Warnings.VALIDNUM);
                }
            }
        } while (!choiceRightChecker);
    }

    private void bruteForceDecryption(Path pathOfInputFile, String incomingTEXT) {
        System.out.println(Warnings.DECRYPTSTART);
        DecryptorWithBruteForce decryptorWithBruteForce = new DecryptorWithBruteForce(alphabetCharArray);
        String bruteForceRESULT = decryptorWithBruteForce.bruteForceDecryptor(incomingTEXT);
        writeResultInFile(pathOfInputFile, bruteForceRESULT);
    }

    private void decryptWithOffsetKey(Path pathOfInputFile, String incomingTEXT, Scanner scanner) {
        System.out.println("Type in your decryption key:");
        boolean choiceRightChecker2 = false;
        Scanner scanner2 = new Scanner(System.in);
        do {
            if (!scanner.hasNextInt()) {
                System.out.println(Warnings.ONLYNUM);
                scanner.next();
            }
            else {
                int offsetKey = scanner.nextInt();
                if (offsetKey >= 0 && offsetKey <= alphabetLength) {
                    DecryptorWithOffsetKey decryptorWithOffsetKey = new DecryptorWithOffsetKey(alphabetCharArray);
                    String decryptorWithOffsetKeyRESULT = decryptorWithOffsetKey.decryptTextWithOffsetKey(incomingTEXT, offsetKey);
                    writeResultInFile(pathOfInputFile, decryptorWithOffsetKeyRESULT);
                    choiceRightChecker2 = true;
                }
                else{
                    System.out.println(Warnings.INTERVALINV);
                    //scanner.next();
                }
            }
        } while (!choiceRightChecker2);
    }

    //МЕТОД ВЫБОРА КЛЮЧА СМЕЩЕНИЯ. ПОЛЬЗОВАТЕЛЬ ВЫБИРАЕТ ХОЧЕТ ВВЕСТИ ВРУЧНУЮ
    //ИЛИ СГЕНЕРИРОВАТЬ РАНДОМНЫЙ КЛЮЧ
    private void offsetKeyChooser(String incomingTEXT, Path pathOfInputFile) {
        System.out.println(Warnings.OFFSETHEAD);
        System.out.println("offset key is an index of offset to crypt your text");
        System.out.println("Choose an OFFSET option:");
        System.out.println("1 - type offset key manualy");
        System.out.println("2 - generate random offset key");
        boolean choiceRightChecker = false;
        do {
            Scanner scanner = new Scanner(System.in);
            if (!scanner.hasNextInt()) {
                System.out.println(Warnings.ONLYNUM);
            } else {
                int choice = scanner.nextInt();
                switch (choice){
                    case 1 :
                        cryptWithOffsetKey(incomingTEXT, pathOfInputFile, scanner);
                        choiceRightChecker=true;
                        break;
                    case 2:
                        Random r = new Random();
                        int low = 1;
                        int high = 39;
                        int offsetKey = r.nextInt(high-low) + low;
                        System.out.println("Your random generated offset key is: "+ ColorText.GREEN+offsetKey+ColorText.RESET);
                        cryptWithReadyKey(incomingTEXT, offsetKey, pathOfInputFile);
                        choiceRightChecker=true;
                        break;

                    default:
                        System.out.println(Warnings.VALIDNUM);
                }
            }
        } while (!choiceRightChecker);
    }

    // МЕТОД ПРОВЕРКИ КОРРЕКТНОСТИ ВВЕДЕННОГО ВРУЧНУЮ КЛЮЧА СМЕЩЕНИЯ
    private void cryptWithOffsetKey(String incomingTEXT, Path pathOfInputFile, Scanner scanner) {


        System.out.println("Type in a number from 1 to " + (alphabetLength-1)+":");
        boolean choiceRightChecker2 = false;
        Scanner scanner2 = new Scanner(System.in);
        do {
            if (!scanner.hasNextInt()) {
                System.out.println(Warnings.ONLYNUM);
                scanner.next();
            }
            else {
                int offsetKey = scanner.nextInt();
                if (offsetKey >= 1 && offsetKey <= (alphabetLength-1)) {
                  //  char[] alphabetCharArray = alphabetProviderObj.getAlphabetCharArray();
                    cryptWithReadyKey(incomingTEXT, offsetKey, pathOfInputFile);
                    choiceRightChecker2 = true;
                }
                else{
                    System.out.println(Warnings.INTERVALINV);
                    //scanner.next();
                }
            }
        } while (!choiceRightChecker2);
    }

    private void cryptWithReadyKey(String incomingTEXT, int offsetKey, Path pathOfInputFile) {
        CryptorWithOffsetKey cryptorWithOffsetKey = new CryptorWithOffsetKey(alphabetCharArray);
        String cryptorWithOffsetKeyRESULT = cryptorWithOffsetKey.cryptTextWithOffsetKey(incomingTEXT, offsetKey);
        writeResultInFile(pathOfInputFile, cryptorWithOffsetKeyRESULT);
    }

    //МЕТОД ЗАПИСИ РЕЗУЛЬТАТА ШИФРОВКИ В ФАЙЛ
    private void writeResultInFile(Path pathOfInputFile, String cryptorWithOffsetKeyRESULT) {
        try {
            fileInputOutput.writeToFile(pathOfInputFile, cryptorWithOffsetKeyRESULT);
            System.out.println(Warnings.SUCCSSES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ВЫВОД ПЕРВОГО МЕНЮ С ОПЦИЯМИ ШИФРОВКИ ИЛИ РАСШИФРОВКИ
    private void printEntryMenu() {
        System.out.println(Warnings.WELOCMECRYPTOR);
        System.out.println("This software crypts and decrypts any txt files using Ceaser crypt\n");
        System.out.println("1 - crypt my txt file");
        System.out.println("2 - decrypt my txt file\n");
        System.out.println("0 - EXIT\n");
        System.out.println("TYPE your variant: ");
    }
}
