package Assignment1;

import Assignment1.DataEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class NearestNeighbour{
   	//k is the number of votes 
        DataEntry  data;
        private int nbrVotes;
        //classess arraylist to store the value of classifications
	private ArrayList<Object> classes;
	private ArrayList<DataEntry> dataSet;
       
        
	/**
	 * Constructor 
	 * @param dataSet The set
	 * @param k The number of neighbours to use
	 */
	public NearestNeighbour(ArrayList<DataEntry> dataSet, int votes){
		this.classes = new ArrayList<Object>(2);
		this.nbrVotes = votes;
		this.dataSet = dataSet;
		
		//Load different classes
		for(DataEntry entry : dataSet){
			if(!classes.contains(entry.getY())) 
                        {
                            {classes.add(entry.getY());}
                        }
		}
	}
	
        /**
         * Computes K nearest neighbours from entry to be classified
         * @param x : the object to be classified
         * @return array of nearest K neighbours
         */
	private DataEntry[] getNearestNeighbourType(DataEntry dat){
            
		//voters[] to hold K number of neighbours
                DataEntry[] voters = new DataEntry[nbrVotes];
                //farest - index of element with farest distance
		double farest = 0;//Double.MIN_VALUE;
		int index = 0;
		
                for(DataEntry entry: this.dataSet){     
                    //caulate distance between other points to "entry"
                     double distance = distance(dat,entry);  
                     //System.out.println("distance "+distance);
                     //first fill up voters and set farest
                     if(voters[voters.length-1] == null){
                         int j = 0;
                         while(j < voters.length){		
                            if(voters[j] == null){   
                                voters[j] = entry; 
                                break;
                             }
                           j++;
                        }//update index so it always points to the dataset
                         //in the array that's furtheset from data to be classified
                        if(distance > farest){
                            index = j;
                            farest = distance;
                        }
                     }//end outer if
                    //When a new dataset with smaller distance, update voters
                    //and find furthest dataset within updated array
                    else{
                            if(distance < farest){
                                voters[index] = entry;
                                double f = 0.0;
                                int ind = 0;
                                for(int j = 0; j < voters.length; j++){ 
                                    double d = distance(voters[j],dat);
                                    //System.out.println("recompute distance "+distance);
                                    if(d > f){
                                         f = d;
                                         ind = j;
                                    }
                                }
                                farest = f;
                                index = ind;
                            }
                    }//end else
                }//end outer for loop
		return voters;
	}
	
	/**
	 * Computes Euclidean distance
	 * @param a From
	 * @param b To
	 * @return Distance 
	 */
	public static double distance(DataEntry a, DataEntry b){
		
                double distance = 0.0;
		int length = a.getX().length;
                for(int i = 0; i < length; i++){	
                    double t = a.getX()[i]-b.getX()[i];
                    //System.out.println("distance difference "+ t);
                    distance = distance+t*t;
		}
		return Math.sqrt(distance);
	}
        
	/**
	 * classify() : use a Hashmap to calculate the weighted distance 
         * each voting element contributes to each classification, the class with largest
         * weight value is chosen for the new dataset * which predicts the class for new dataset 
         * When exact match is found, simply return the class of the matching element
	 * @param e Entry to be classifies
	 * @return The most probable class for the dataset
	 */
	public Object classify(DataEntry e){
		
                //hashmap to store the classes of data and it's related distance
                HashMap<Object,Double> classDictionary = new HashMap<Object,Double>();
                //entry contains all the voting datasets
                DataEntry[] entry = this.getNearestNeighbourType(e);
                
               /*
                for(int i=0; i<2;i++){
                    System.out.print("Voter "+i);
                    for(int j=0 ;j<8; j++)
                    {
                        System.out.print(" : "+entry[i].getXVal(j)+", ");
                    }
                    System.out.println("");
                }*/
                
                //For each classes, add up the total weighted distance between new data and voting datasets
                for(int i = 0; i < entry.length; i++){
                    
                    //Special Case: Exact match found simply return the class
                    double dist =NearestNeighbour.distance(entry[i], e);
                    if(dist==0.0){
                        return entry[i].getY();
                    }
                    else{
                            Object o =entry[i].getY();
                            String className=o.toString();
                            double distance = NearestNeighbour.weighting(distance(entry[i],e));
                            if((classDictionary.containsKey(className))==false){
                                classDictionary.put(entry[i].getY(), distance);
                            }
                            else{
                                 classDictionary.put(entry[i].getY(), classDictionary.get(entry[i].getY())+distance);
                            }
                    }  
		}
		//choose the class with largest weighted distance           
		Object obj = null;
		double max = 0;
		for(Object ob : classDictionary.keySet()){
			if(classDictionary.get(ob) >= max){
                                max = classDictionary.get(ob);
				obj = ob;
			}
		}
		return obj;
	}
           
/**
  * weighting()
  * For applying weight to numerical values
  */
 private static double weighting(double d){
         //System.out.println("Weighted Distance is "+1/d);
         return 1.0/d;
 }       
/*
//DataEntry to be separate into a proper class.        
public static class DataEntry{
	private double[] x;
	private Object y;
	
	public DataEntry(double[] x, Object y){
		this.x = x;
		this.y = y;
	}
	
        public double[] getX(){
                return this.x;
        }

        public Object getY(){
                return this.y;
        }
        
        public double getXVal(int index)
        {
            double[] val =this.getX();
            return val[index];
       }
        
       public void setX(double val, int index){
           x[index]=val; 
       }
   }*/
}

