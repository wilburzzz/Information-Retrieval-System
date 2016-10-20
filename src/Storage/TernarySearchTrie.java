/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Storage;

/**
 *
 * @author zhujinmeng
 */
public class TernarySearchTrie {
    
    private class Node{
        private char c;
        private MyFileDirectory value;
        private Node left, mid, right;
    }
    
    public Node root;
    
    public void put(String key, MyFileDirectory val){
        root = put(root, key, val, 0);
    }
    
    private Node put(Node x, String key, MyFileDirectory val, int d){
        char c = key.charAt(d);
        if (x == null){
            x = new Node();
            x.c = c;
        }
        if(c<x.c){
            x.left = put(x.left, key, val, d);
        }else if(c > x.c){
            x.right = put(x.right, key, val, d);
        }else if(d<key.length()-1){
            x.mid   = put(x.mid,   key, val, d+1);
        }else{
            x.value  = val;
        }
        return x;
    }
    
    public MyFileDirectory get(String key){
        Node x = get(root, key, 0);
        if (x == null){
            return null;
        }
        return x.value;
    }
    
    private Node get(Node x, String key, int d){
        if(x==null){
            return null;
        }
        char c = key.charAt(d);
        if(c<x.c){
            return get(x.left, key, d);
        }else if(c > x.c){
            return get(x.right, key, d);
        }else if(d<key.length()-1){
            return get(x.mid, key, d+1);
        }else{
            return x;
        }
    }
  
    public boolean contains(String key){
     return get(key) != null;  
    }
    
    
    
    
}
