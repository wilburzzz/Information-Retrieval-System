/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ReadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;



/**
 *
 * @author zhujinmeng
 */
public class testReadOneFile {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        File file = new File("/Users/zhujinmeng/Desktop/test file/A Study of Bible.txt");
        String string =  ReadOneFile.readOneFile(file);
        System.out.println(string);
    }
}
