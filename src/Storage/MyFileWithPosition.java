/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Storage;

import java.util.ArrayList;

/**
 *
 * @author zhujinmeng
 */
public class MyFileWithPosition {
    
/*    public class Entry{
    
        public int position;
        public String nextWord;
        
        Entry(int p){
            this.position = p;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getNextWord() {
            return nextWord;
        }

        public void setNextWord(String nextWord) {
            this.nextWord = nextWord;
        }
        
        
}
  */  
    
    
    
    public String fileName;
    public int likecount;
    public ArrayList<Integer> posList;
    
    public MyFileWithPosition(String fN){
        this.fileName = fN;
        this.posList = new ArrayList<Integer>();
    }
    
    public void addPosition(int p){
        this.posList.add(p);                 //??????????????????
    } 
    
    
}
