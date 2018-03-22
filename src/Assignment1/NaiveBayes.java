/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Assignment1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Naive Bayes Class:
 * Calculate mean and variance for each column
 * Assuming each factor contributing equally to the classification
 * Use a inner class to store each classification type and mean/sd of each column in
 * prima dataset, Use normal distribution formulae to calculate probability
 * @author KARR
 */
public class NaiveBayes {
    
    private ArrayList<DataEntry> data; //this is the prima dataset
    private ArrayList<NaiveBayesObj>  classes; //this holds the statisics info for both class0 and class1
    //Parameters of Prima dataset
    private int nbrOfCols;
    private int nbrOfLines;
    private int nbrOfClasses;
    private String[] classNames;
    private double[] weighting;
    //Mean, Sum and Standard Diveation of each col
    private double colSum =0.0;
    private double colMean =0.0;
    private double colSD = 0.0;
    private final double e=2.71828; //value of natural log
    private final double pie = 3.14159;
    
    
    public NaiveBayes(ArrayList<DataEntry> data,int nbrOfLines,int nbrOfCols,String[] classNames,double[] weighting){
    
        this.data=data;
        this.nbrOfCols = nbrOfCols;
        this.nbrOfLines=nbrOfLines;
        classes = new ArrayList<NaiveBayesObj>();
        this.classNames=classNames;
        this.weighting=weighting;
        createInnerClass();
    }
    
    //create inner class objects
    //inner class object collects statistical information
    private void createInnerClass(){
        
        for(int i=0;i<classNames.length;i++){
            
            NaiveBayesObj objClass = new NaiveBayes.NaiveBayesObj(nbrOfCols, nbrOfLines);
            objClass.setClass(classNames[i]);
            classes.add(objClass);
        }
    }
    
/**
 * 
 * 
 */
  public void caculate(){

    //temp variables for caculation
    double sum =0.0;
    int countMissing =0; //couting how many values are missing
    int classFoundAt =0;
    int colItemCount=0;

     //go through each column minus one coz last col is class
    for(int j =0; j<nbrOfCols-1;j++ ){  
        
        NaiveBayes.NaiveBayesObj tempObj;     
        //go through each row
         for(int i=0;i<nbrOfLines;i++){
            // System.out.println("Row no. "+i);
             //Find the index of classNames[] element that has the
             //same class as the element on data array
            for(int m =0; m<classNames.length;m++){     
                //caculate the sum and mean for each class
                if((data.get(i).getY().toString()).equals(classNames[m].toString())){
                    
                    tempObj = classes.get(m);
                    //we are only interested in non zeros
                    double val =data.get(i).getXVal(j);
                    if(val!=0.0){
                        tempObj.addSum(val,j);
                        tempObj.incrementCount(j);
                   }
                }   
            } 
       }
       colItemCount=0;
       colMean=0;
       colSum=0;
       //end of a column, start next column
    }

    calculateMean();
    //by the end of this function, we will have the mean and count for each class for
    //each columns, need to loop through and get the standard deviation 
    caculateSquareOfDifference();
    caculateSd();
 }
 
public void caculateSquareOfDifference(){
   
   //now caculate square of difference for each row/column
   //m == different class of classes arraylist
   for(int m =0; m<classNames.length;m++){
       
        //j == go through columns of prima dataset
       for(int j=0;j<nbrOfCols-1;j++){
            
           // i go through rows of prima dataset
           for(int i=0;i<nbrOfLines;i++){
         
               NaiveBayes.NaiveBayesObj tempObj = classes.get(m);         
               String className = tempObj.getClassName().toString();
               double val =data.get(i).getXVal(j);
             
               if(((data.get(i).getY().toString()).equals(className))){    
                     //access the innerclass's value 
                     double  tempMean = tempObj.getMean(j);
                     tempObj.setSquareOfDiff(Math.pow((val-tempMean),2),i,j); 
                    // System.out.println("Class" +m);
                     //System.out.println("Row "+i+ " Column "+j);
                     //System.out.println("   Mean is "+tempMean +" Square of Diff "+(val-tempMean)*(val-tempMean) );
                }       
            }//End of for loop we have an array of difference for each class
      }
   }   
}
  
public void caculateSd()
{     
    double tempSumOfSquareOfDiff =0.0;
    int count=0;  
    for(int k =0; k<classes.size();k++)
    {    
        NaiveBayes.NaiveBayesObj obj = classes.get(k);
        
        for(int j=0; j<nbrOfCols-1;j++)
        {    
            for(int i=0; i<nbrOfLines ; i++ ){
             
                count =obj.getCount(j);
                double temp = obj.getSquareOfDiff(i, j);
                tempSumOfSquareOfDiff=tempSumOfSquareOfDiff+ temp;
            }
            double var = tempSumOfSquareOfDiff/count;
            double sd =Math.sqrt(var);
            obj.setSd(sd, j);
            tempSumOfSquareOfDiff=0;
        }
    }      
}


public void calculateMean()
{
    for(int i =0; i<classes.size();i++)
    {
        NaiveBayes.NaiveBayesObj obj = classes.get(i);
        obj.caculateMean(i);
        
    }
}

public Object classify(DataEntry o){
    
    Object obj =null;
    double[] mesurements = o.getX();
    //2D double array to store the probability for each cols
    //first index is for total number of classification
    //2nd index is for total number of columns in prima dataset
    double[][] colProbability = new double[classes.size()][nbrOfCols-1]; 
    double[] overAllProbability=new double[classes.size()];
    double dataMean =0.0;
    double dataSd =0.0;
    //The value to be classified
    double testVal=0.0;
    
    //caculate probably for both class0 and class1 base on obj
    //get sd & mean from each classification and caculate prob. for
    //both class0 and class1
    for (int i=0; i<classes.size();i++){
            //System.out.println("searching classes ");
            NaiveBayes.NaiveBayesObj tempObj = classes.get(i);
            
            for(int j = 0; j<nbrOfCols-1;j++){

                testVal = o.getXVal(j);
                dataMean =tempObj.getMean(j);
                dataSd = tempObj.getSd(j);
                //apply normal distrib formula
                double OneOverSdSqrt2Pie =(1/(dataSd*Math.sqrt(2*pie)));
                double eToPowerOf =  Math.pow(e,(-1*((Math.pow((testVal-dataMean),2))/(2*Math.pow(dataSd, 2)))));
                System.out.println("row "+i+"col "+j);
                System.out.println ("Probablilty " + OneOverSdSqrt2Pie*eToPowerOf);
                colProbability[i][j]= OneOverSdSqrt2Pie*eToPowerOf;
            }

        
    }
      //adding up probability of each col and apply weighting 
          for(int r=0; r<classes.size();r++){
            for(int k=0; k<nbrOfCols-1;k++){
               overAllProbability[r]= overAllProbability[r]+(weighting[k]*colProbability[r][k]);
            }
          }
               
    //fining wich class has larger probability
    double max =0.0;
    int maxIndex=-1;
    for(int l = 0; l<classes.size();l++){
        
        if (overAllProbability[l]>max){
            System.out.println("Overall probablilty " + overAllProbability[l]);
            max=overAllProbability[l];
            maxIndex++;
        }
     }
    System.out.println(maxIndex);
    obj = classes.get(maxIndex).getClassName();
    return obj;
}
  
public void printNaiveBayesData()
{
    for(int i =0;i<classes.size();i++)
    {
        NaiveBayes.NaiveBayesObj obj = classes.get(i);

        System.out.println(" ");
        System.out.println("Means are  ");
        for(int j =0; j<nbrOfCols-1;j++)
        {
            System.out.print(obj.getMean(j)+", ");  
        }
        
        System.out.println(" ");
        System.out.println("SD are  ");
        for(int k=0;k<nbrOfCols-1;k++)
        {
            System.out.print(obj.getSd(k)+", ");
        }
         System.out.println(" ");
        System.out.print("Sum are  ");
         for(int k=0;k<nbrOfCols-1;k++)
        {
            System.out.print(obj.getSum(k)+", ");
        }
         
       
         System.out.println(" ");
        System.out.print("count are  ");
          for(int k=0;k<nbrOfCols-1;k++)
        {
            System.out.print(obj.getCount(k)+", ");
        }
       
        
        System.out.println(" ");
    }
    
}
/**
 * Inner class to store mean/sd of all columns and
 * the corresponding class
 */
public static class NaiveBayesObj{
    
    Object classObj;//classification
    //array to store mean and sd of all column of prima dataset
    int colItemCount[];
    double squareOfDiff[][];
    double colMean[];
    double colSd[];
    double colSum[];
    
    public NaiveBayesObj(int nbrCols, int nbrOfLines){
        
        classObj = null;
        colMean = new double[nbrCols-1];
        colSd = new double[nbrCols-1];
        colItemCount=new int[nbrCols-1];
        squareOfDiff = new double[nbrOfLines][nbrCols]; // 2D array for squqre of diff
        colSum=new double[nbrCols-1];
    }
    
    public void addSum(double num, int ind)
    {
        colSum[ind] =colSum[ind]+num;
    }
    
    
    public Object getClassName(){
        return classObj;
    }
    
    public void setSquareOfDiff(double val,int i, int j)
    {  
        squareOfDiff[i][j]=val;
    }
    public double[][] getSquareOfDiffArray()
    {
        return squareOfDiff;
    }
     
    public double getSquareOfDiff(int i, int j){
        return squareOfDiff[i][j];
    }
            
    public void setClass(Object o){
        classObj =o;
    }
            
    public void setMean(double val,int index){
        colMean[index]=val;
    }
   
    public double getMean(int index){
        return colMean[index];
    }
    public int getCount(int index)
    {
        return colItemCount[index];
    }
    public int[] getCountArray(){
        return colItemCount;
    }
 
            
    public double getSd(int index){
        return colSd[index];
    }
    
    public void setSd(double val, int index){
      
        colSd[index]=val;
         System.out.println("setting sd "+val+"at "+index);
    }
    
    public void setCount(int val, int index){
        colItemCount[index]=val;
    }
    
    public void incrementCount(int index)
    {
        colItemCount[index]=colItemCount[index]+1;
    }
    
    public void caculateMean(int index)
    {
        double sum=0.0;
        
        for(int i =0; i<colSum.length;i++)
        {
            colMean[i]=colSum[i]/colItemCount[i];
        }
    }
    
    public double getSum(int i)
    {
        return colSum[i];
    }
   
}
    
         
}

