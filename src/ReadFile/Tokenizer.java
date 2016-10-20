/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ReadFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import opennlp.tools.tokenize.TokenizerModel;

/**
 *
 * @author zhujinmeng
 */
public class Tokenizer {
 
    @SuppressWarnings("CallToPrintStackTrace")
    public static TokenizerModel loadTokenizer(){
    InputStream modelIn = null;

    try {
        modelIn = new FileInputStream("/Users/zhujinmeng/Desktop/source file/en-token.bin");
        TokenizerModel model = new TokenizerModel(modelIn);
        return model;
    }
    catch (IOException e) {
        e.printStackTrace();
    }
    finally {
        if (modelIn != null) {
          try {
            modelIn.close();
          }
          catch (IOException e) {
          }
        }
    }
            return null;
    }
}
