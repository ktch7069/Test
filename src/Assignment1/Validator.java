/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Assignment1;

import java.util.ArrayList;

/**
 *Validator class for 10 fold cross validation
 * @author KARR
 */
public class Validator {
    
    ArrayList<DataEntry> data;
    double[] accuracy;
    ArrayList<DataEntry> training;
    ArrayList<DataEntry> testing;
    int[] foldIndex;
    int lineCount;
    int colCount;
    int fold = 10;
    public Validator(ArrayList<DataEntry> data,int lineCount,int colCount)
    {
        this.data = data;
        accuracy = new double[10];
        training = new ArrayList<DataEntry>(data.size());
        testing = new ArrayList<DataEntry>(data.size()/5);
        this.lineCount=lineCount;
        this.colCount=colCount;
        //foldIndex keep the boundary of each folds,
        //need 20 coz 1 indices on each side of the boundary and
        foldIndex = new int[fold+1];
    }
    
    public void run10FoldTest()
    {
        int foldSize = (int)((lineCount)/fold)+1;
        System.out.println("fold size "+foldSize);
        //create an array of index where the folds will occurs
        int j=0;
        System.out.println("linecount "+lineCount);
        for(int i=0; i<=lineCount; i=i+foldSize){
            foldIndex[j]=i;
            j++;
          }
       
        foldIndex[foldIndex.length-1]=lineCount-1;
    for(int m=0; m< foldIndex.length;m++){    
    System.out.println(foldIndex[m]+" ");}

        for(int l=0; l<foldIndex.length;l++){
            
            int index=0;
            index = foldIndex[l];
            //for fold 1-9
            while(l<foldIndex.length){
                for(int m=0; m<data.size();m++) {

                    //special case for first loop
                    if(index==0)
                    {
                        testing.add(data.get(m));
                    }
                    //special case for last segment 
                    if(m<index+1 && m>index-foldSize)
                    {
                        testing.add(data.get(m));

                    }else
                    {
                        training.add(data.get(m));
                    }    
                   }
            }
            
            
    }
  }
}   
  
