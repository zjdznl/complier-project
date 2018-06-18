package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static int getIndexByProductionString(String production) {
        //根据 产生式 得到对应的标号 -1 未找到，>=0 对应标号
        //这里的标号尽量和 原版 保持一致
        try (BufferedReader br = new BufferedReader( new FileReader( new File( Config.PRODUCTIOS_FILE_TMP ) ) );
        ) {
            List<String> productions = new ArrayList<>();

            String line = "";
            String tmp = "";
            while ((line = br.readLine()) != null) {
                String[] strings = line.split( ":" )[1].trim().split( "->" );
                strings[0] = strings[0].trim();
                strings[1] = strings[1].trim();

                tmp = String.join( "->", strings );
                productions.add( tmp );
            }

            String[] strings = production.trim().split( "->" );
            strings[0] = strings[0].trim();
            strings[1] = strings[1].trim();
            tmp = String.join( "->", strings );

            if (productions.contains( tmp )) {
                return productions.indexOf( tmp );
            } else {
                return -1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }
}