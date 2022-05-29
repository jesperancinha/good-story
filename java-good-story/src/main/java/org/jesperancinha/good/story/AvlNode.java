package org.jesperancinha.good.story;

/**
 * Created by jofisaes on 29/05/2022
 */
public record AvlNode(
        String word,
        AvlNode top,
        AvlNode left,
        AvlNode right
) {
}