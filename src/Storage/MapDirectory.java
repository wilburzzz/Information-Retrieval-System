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
public class MapDirectory {
    
    private class Entry{
        
        private final String fileName;
        private ArrayList<Integer> posList;
        private Entry next;
        
        Entry(String fn){
            this.fileName = fn;
            this.posList = new ArrayList<Integer>();
        }
        
        public void insert(Entry e){
        Entry temp = this;
        while(temp.next!=null){
           temp = temp.next;         
        }
        temp.next = e;
        }
        
        public void addPosition(int position){
            this.posList.add(position);                 //??????????????????
        }
        
        public boolean equals(String key){
            return this.fileName.equals(key);
        }
        
        
    }
    
    public Entry[] mapList;
    
    private static int DEFAULT_CAPACITY=256;    
    private int capacity;  
    private int size; 
    private static double LOAD_FACTOR = 0.75;
    
    public MapDirectory(){
        this.mapList = new Entry[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
        size = 0;
    }
    
    public int hashCode(String key){
        return ((key.hashCode()*31+17)%37)%capacity;
    }
    
    
     public Entry put(String fN){
         Entry entry = new Entry(fN);
         int positionInList = this.hashCode(fN);
         Entry a = mapList[positionInList];
         if(a!=null){
            mapList[positionInList].insert(entry);
        }else{
            mapList[positionInList]=entry;
            size++;
            resize();
            
        }  
        return entry;
     }
    
    public Entry get(String fN){
        int positionInList = hashCode(fN);
        Entry b = mapList[positionInList];
        if(b==null){
            System.out.println("NOT EXIST");
            return null;
        }else{
            Entry temp = b;
            while(!(temp.fileName.equals(fN))){
                if(temp.next==null){
                    System.out.println("NOT EXIST");
                    return null;
                }
                temp = temp.next;
            }
            return temp;            
        } 
    }
    
    public Entry[] resize(){
        if(size>=capacity*LOAD_FACTOR){
            capacity = capacity*2;
            Entry[] copy = new Entry[capacity];
            for(int i=0;i<size;i++){
                copy[i]=mapList[i];
            }
            mapList = copy;
        }
        return mapList;
    }
    
    public boolean isEmpty(){
        return (size==0);
    }
    
}
