/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ReadFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author zhujinmeng
 */
public class ReadOneFile {
    
    public static String readOneFile(File file) {
       
        
/*
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            StringBuffer contents = new StringBuffer();
            while(br.readLine()!=null){
                String s=br.readLine();
                System.out.println(s); 
                contents.append(s);
            }
            br.close();
            return contents.toString();
*/
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try{
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            return new String(filecontent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
        }
          
        return null;
    }
}
