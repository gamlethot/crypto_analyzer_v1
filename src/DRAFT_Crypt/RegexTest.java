package DRAFT_Crypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    public static void main(String[] args) {
        String regexDotSpaceUpperCase = ".\\s[А-Я]";
        String regexComaSpaceLowerCase = "\\.*,\\s[а-я]";
        String sb = "Всем привет. Я написал этот текст, для проверки.\n" +
                "Всем досвидания!!!\n" +
                "Спасибо, за внимание. Было приятно пообщаться.";
        if ((sb.matches(regexDotSpaceUpperCase)) && (sb.matches(regexComaSpaceLowerCase))) {
            System.out.println("Сработало условие соответсвия");
        }
        Pattern p = Pattern.compile(regexDotSpaceUpperCase);
        Pattern b = Pattern.compile(regexComaSpaceLowerCase);
        Matcher matcher2 = b.matcher(sb);
        Matcher matcher = p.matcher(sb);
        if (matcher.find() && matcher2.find()){
            System.out.println("YES");
        }
        System.out.println(matcher.find()+"___1");
        System.out.println(matcher2.find()+"___2");
    }
}
