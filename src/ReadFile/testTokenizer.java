/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ReadFile;

import java.io.IOException;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 *
 * @author zhujinmeng
 */
public class testTokenizer {
    

    
    
    public static void main(String[]args) throws IOException{
        TokenizerModel model = Tokenizer.loadTokenizer();
        TokenizerME tokenizer = new TokenizerME(model);
        String tokens[] = tokenizer.tokenize("               "
                + "An input sample sentence. I have 1000000 dollors. ten-minuit-walk But it is not there.( Is it?) I haven't know.!@#$%^&*()|\\");

        for(String s:tokens){
                for(int i=0;i<s.length();i++){
                    char c = s.charAt(i);
                    System.out.print(String.valueOf(c).replaceAll("[^A-Za-z0-9]", ""));
                }

                System.out.println("");




            /*
            if((!s.equals(","))&&(!s.equals("."))&&(!s.equals("("))&&(!s.equals(")"))&&(!s.equals("?"))&&(!s.equals("~"))&&(!s.equals("`"))&&(!s.equals("!"))&&(!s.equals("@"))&&(!s.equals("#"))&&(!s.equals("$"))&&(!s.equals("%"))&&(!s.equals("^"))&&(!s.equals("&"))&&(!s.equals("*"))&&(!s.equals("_"))&&(!s.equals("-"))&&(!s.equals("+"))&&(!s.equals("="))&&(!s.equals("{"))&&(!s.equals("}"))&&(!s.equals("["))&&(!s.equals("]"))&&(!s.equals("|"))&&(!s.equals("\\"))&&(!s.equals(":"))&&(!s.equals("\""))&&(!s.equals("\'"))&&(!s.equals("<"))&&(!s.equals(">"))&&(!s.equals("/"))){

                System.out.println(s);
            }        
            */

            }
    
    
            
    }
}
