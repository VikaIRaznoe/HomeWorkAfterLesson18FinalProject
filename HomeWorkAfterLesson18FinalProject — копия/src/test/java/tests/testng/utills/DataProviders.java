package tests.testng.utills;

import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataProviders {

    public static List<String> findStr(){
        //Считываем содержимое файла listText
        String inputFile = "/src/test/resources/listText";
        String workingDir = System.getProperty("user.dir");
        InputStream inputStream;
        List<String> lines = new ArrayList<String>();
        try {
            inputStream = new FileInputStream(new File(workingDir.substring(0, workingDir.length()) + inputFile));
            DataInputStream in = new DataInputStream(inputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                lines.add(strLine);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    @DataProvider(name= "dataProvider")
    public static Object[] dataProviderMethod() {
        List<String> lines = findStr();
        //Смотрим весь файл lisText
        //и выцепляем строку,которая следует за строкой,
        //которая по регулярке совпала
        String findStr = "";
        for (String str : lines){
            //Для поиска строки LIST + TITLE + MESSAGE + BUTTONS
            Pattern pattern = Pattern.compile("(\\+*[A-Z])");
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()){
                //Предполагаем,что первая строка это заголовок(по названию кнопки)
                //Предполагаем, что после заголовка только одна нужная строка
                findStr = lines.get(1);
                break;
            }
        }
        return new Object[] {lines.get(1)};
    }
}