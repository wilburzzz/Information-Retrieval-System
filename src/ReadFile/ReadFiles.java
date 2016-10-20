/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ReadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author zhujinmeng
 */
public class ReadFiles {    
    
    public static ArrayList<FileMap> readAllFile() throws IOException, FileNotFoundException{
        File file = new File("/Users/zhujinmeng/Desktop/Dataset_Algorithms/");
        File[] fileList = file.listFiles();
        ArrayList<FileMap>txtList = new ArrayList<FileMap>();
        for(int i=0;i<fileList.length;i++){
            File f = new File(fileList[i].getAbsolutePath());
            FileMap fm = new FileMap();
            fm.fileName = f.getName();
            fm.fileContent = ReadOneFile.readOneFile(f);
            txtList.add(fm);
        }
        return txtList; 
    }
}
