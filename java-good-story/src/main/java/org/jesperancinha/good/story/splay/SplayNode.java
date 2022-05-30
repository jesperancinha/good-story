package org.jesperancinha.good.story.splay;

/**
 * Created by jofisaes on 30/05/2022
 */
public class SplayNode {
    SplayNode parent;
    SplayNode left;
    SplayNode right;

   public String word;

    public SplayNode(String key) {
        this.word = key;
    }
}
