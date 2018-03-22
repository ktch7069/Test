package Assignment1;
import java.util.ArrayList;

/**
 *
 * @author KARR
 */

public class Main {

    static final int NBR_NEAREST_NEIGHBOUR=4;
    static final int NBR_NON_ZERO_COLS=6;
    static final int[] NONE_ZERO_COLS={1,2,3,4,5,6}; //col count starts from 0
    //Need to change the file path !!!! 
    static final String FILE_NAME = "E:\\Karr_Documents\\NetBeansProjects\\NearestNeighbour\\src\\Assignment1\\prima.csv";
    static final int NBR_COLS=9; //number of columns
    final int NBR_CLASS_COL = 9; //The column where class is
    static final String[] CLASS_NAMES={"class0","class1"};
    //For Naive Bayes : probability weighting of each measurement for NB algorithm - using columns 4, 5, 6, 7 to classify
    static final double[] NB_WEIGHTING ={0,0,0,0.3,0.3,0.2,0.2,0}; 
    
    public static void main(String[] args){
       
  
        int nbrOfLines=0;
        //Arraylist to store entire dataset
        ArrayList<DataEntry> data = new ArrayList<DataEntry>();
        TreatZeroEntry noZero=new TreatZeroEntry(NONE_ZERO_COLS);
        String[] header=null;

        //filereader to insert training data
         ReadCSV read = new ReadCSV(FILE_NAME,data,NBR_COLS,header,NONE_ZERO_COLS);
         read.readFileAndTrain();
         nbrOfLines = read.getLineCount();
         noZero.treatZero(NBR_NON_ZERO_COLS,data);
       
        //create objects for NN and NB
        //NearestNeighbour nn = new NearestNeighbour(data, NBR_NEAREST_NEIGHBOUR); 
        //NaiveBayes nb = new NaiveBayes(data,nbrOfLines,NBR_COLS,CLASS_NAMES,NB_WEIGHTING);
        //nb.caculate();
        //System.out.println(nb.classify(new DataEntry(new double[]{0.058824,0.447236,0.540984,0.232323,0.111111,0.418778,0.038002,0},"Ignore")));
        //nb.printNaiveBayesData();
      
        
        Validator valid = new Validator(data,nbrOfLines,NBR_COLS);
        valid.run10FoldTest();
        
        
        //System.out.println("Classified as: "+nn.classify(new DataEntry(new double[]{0.058824,0.427136,0.540984,0.292929,0,0.396423,0.116567,0.166667},"Ignore")));
    
  
       /* for(DataEntry dataset :data)
        {
            for(int i=0;i<NBR_COLS-1;i++)
            System.out.print(dataset.getXVal(i)+", ");
            System.out.println("  ");
        }*/
    }
}
