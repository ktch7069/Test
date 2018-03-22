
package Assignment1;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * class ReadCSV to read in the CSV data
 * @author KARR
 */
public class ReadCSV {
    
    private String fileName;
    private String line;
    private String delimiter;
    private BufferedReader br =null;
    private   ArrayList<DataEntry> aList; 
    private String[] header;
    private boolean EOF = false;
    private int index;
    private TreatZeroEntry changeZero;
    private int lineCount;
    
    public ReadCSV(String aFile,ArrayList<DataEntry> dataList, int aIndex, String[] header, int[] nonZeroCols)
    {
       fileName = aFile;
       line="";
       delimiter =",";
       aList= dataList;
       index = aIndex;
       this.header=header;
       lineCount =0;
    }
    
    /**
     *readFileAndTrain(): read csv data
     * @param arraylist adata, 
     * 
     */
     
     
    public void readFileAndTrain()
    {         
	BufferedReader br = null;
	try {
		br = new BufferedReader(new FileReader(fileName));   
		while ((line = br.readLine()) != null) {
                   
                    //get header
                    if (lineCount==0)
                    {
                        header=line.split(delimiter);  
                    }
                    else{
                         // use comma as separator for csv file
			String[] data = line.split(delimiter);
                        //last element is the classification 
                        String classification =data[index-1];
                        
                        //variable to store numerical value. length is index-1
                        //since last column is classification
                        double[]  dataInDouble = new double[index-1];
		   
                        for(int i =0; i<data.length-1; i++)
                        {
                            dataInDouble[i]= Double.parseDouble(data[i]);
                        }
                       // double[] subDataArray = Arrays.copyOfRange(dataInDouble,0,index-1);
                       aList.add(new DataEntry(dataInDouble, classification));
                    }
                    lineCount++;
             }
 
	} catch (FileNotFoundException e) {
            
                System.out.println("Oooops....NO FOUND!");
		e.printStackTrace();
                
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
   
    }
    
    public int getLineCount(){
        return lineCount-1;
    }
            
}
