package Assignment1;

/**
 *
 * @author KARR
 */
public class DataEntry {
    //DataEntry to be separate into a proper class.        

	private double[] values;
	private Object className;
	
	public DataEntry(double[] x, Object y){
		this.values = x;
		this.className = y;
	}
	public void setY(Object o){
            className =o;
        }
        public double[] getX(){
                return this.values;
        }

        public Object getY(){
                return this.className;
        }
        
        public double getXVal(int index)
        {
            double[] values =this.getX();
            return values[index];
       }
        
       public void setX(double val, int index){
           values[index]=val; 
       }
   }

