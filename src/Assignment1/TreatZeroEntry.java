/*
 * 
 */
package Assignment1;
import java.util.ArrayList;

/**
 *
 * @author KARR
 */
public class TreatZeroEntry {
    
    private int[] nonZeroCols;
 //   private  ArrayList<NearestNeighbour.DataEntry> data = new ArrayList<NearestNeighbour.DataEntry>();
    
    public TreatZeroEntry(int[] nonZeroCols){
  
        this.nonZeroCols=nonZeroCols;
   //     this.data=data;
    }
    /**
     * TreatZero(): 
     * @param dataVal is the value of data
     * @return 
     */
    public void treatZero(int nbrNonZero,ArrayList<DataEntry> data){
        
       //dataSetSize is the length of each data set
       int dataRowLength = data.get(0).getX().length;
       int dataTotalNbrOfLines = data.size();
        /**
          * first loop traverse data arraylist
          * second loop traverse each dataset within data arraylist
          * Thrid loop traverses the data set array
          * if data is 0 then look further down the arraylist to find a non-zero value x,
          * then use 1-x to replace the 0 value
          * if last line has 0 value data, look up instead of down
          */
        //First for loop to access data object in arraylist
        for(int i=0;i<dataTotalNbrOfLines;i++){   
            //2nd for loop to access actual data array
            for(int j=1; j<=nbrNonZero;j++){
                    if(j==nonZeroCols[j-1] && data.get(i).getXVal(j)==0.0){                
                        //need to search up if last line of data reached
                        if(i==dataTotalNbrOfLines-1){
                            for(int n=dataTotalNbrOfLines-2; n>0;n--){
                                    double temp =data.get(n).getXVal(j);
                                    if(temp!=0.0){
                                        data.get(i).setX((1.0-temp),j);
                                        break;
                                    }
                                }
                        }
                        else{
                            boolean foundNonZero = false;
                            //if it's not the last line look for the next line
                            for(int k=i+1; k<dataTotalNbrOfLines-1;k++){
                                //access the lth data for the kth data object 
                                double temp =data.get(k).getXVal(j);
                                if(temp!=0.0){
                                   data.get(i).setX((1.0-temp),j);
                                   foundNonZero=true;
                                   break;
                                }
                            }   
                             if(foundNonZero==false){
                                //it might reach EOF and yet still 0! So start searching start of arraylist
                                //since all the 0 are sorted in previous data set ...
                                for(int p=0; p<dataTotalNbrOfLines;p++){        
                                        if(data.get(p).getXVal(j)!=0.0){
                                            double temp1 =data.get(p).getXVal(j);
                                            data.get(i).setX((1.0-temp1),j);
                                            break;
                                        }
                                }       
                             }
                         }
                     }
              }
         }
    }
}
  

