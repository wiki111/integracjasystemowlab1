package com.company.lab2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SelectionListener implements ListSelectionListener{

    private MainLayout mainLayout;

    public SelectionListener(MainLayout mainLayout){
        this.mainLayout = mainLayout;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        int first = e.getFirstIndex();
        int last = e.getLastIndex();
        boolean isAdjusting = e.getValueIsAdjusting();
        if(!lsm.isSelectionEmpty()){
            int min = lsm.getMinSelectionIndex();
            int max = lsm.getMaxSelectionIndex();
            for(int i=min; i<=max; i++){
                if(lsm.isSelectedIndex(i)){
                    System.out.println(" " + i);
                }
            }
        }
    }
}
