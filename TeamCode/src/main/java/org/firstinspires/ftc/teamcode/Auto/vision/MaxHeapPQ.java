package org.firstinspires.ftc.teamcode.Auto.vision;


import org.opencv.core.Rect;

import java.util.ArrayList;

public class MaxHeapPQ {
    private ArrayList<PriorityItem> contents;

    public MaxHeapPQ (){
        contents = new ArrayList<>();
        contents.add(null);
    }

    private PriorityItem getElement(int index) {
        if (index >= size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    private void swap(int index1, int index2) {
        PriorityItem element1 = getElement(index1);
        PriorityItem element2 = getElement(index2); //min
        contents.set(index2, element1);
        contents.set(index1, element2);
    }

    private int getLeftOf(int index) {
        return 2 * index;
    }

    private int getRightOf(int index) {
        return (2 * index) + 1;
    }

    private int getParentOf(int index) {
        return (index / 2);
    }

    private int larger(int index1, int index2) {
        if (contents.get(index1).compareTo(contents.get(index2)) > 0) {
            return index1;
        } else {
            return index2;
        }
    }

    private void bubbleUp(int index) {
        int parent = getParentOf(index);
        if (parent != 0 && contents.get(parent).compareTo(contents.get(index)) < 0) {
           swap(parent, index);
           bubbleUp(parent);
        }
    }

    private void bubbleDown(int index) {
        int largerChildIndex = larger(getLeftOf(index), getRightOf(index));
        if (largerChildIndex < contents.size() && contents.get(index).compareTo(contents.get(largerChildIndex)) < 0) {
            swap(index, largerChildIndex);
            bubbleDown(index);
        }
    }

    public int size(){
        return contents.size();
    }

    public Rect peek(){
        return contents.get(1).item();
    }

    public void insert(Rect item, double priorityValue){
        PriorityItem pv = new PriorityItem(item, priorityValue);
        contents.add(pv);
        bubbleUp(contents.size() - 1);
    }

    public Rect poll(){
        PriorityItem smallest = contents.get(1); // the root
        swap(1, contents.size() - 1); // swap with bottom most right
        contents.remove(contents.size() - 1);
        bubbleDown(1); // after swap, bubble down
        return smallest.item();

    }
}
