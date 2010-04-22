package server.position;


public class FingerPrintHyperPoint 
{
	private FingerPrint fp;
	private double[] hyperPoint;
	
	
	public FingerPrint getFp() {
		return fp;
	}
	public void setFp(FingerPrint fp) {
		this.fp = fp;
	}
	public double[] getHyperPoint() {
		return hyperPoint;
	}
	public void setHyperPoint(double[] hyperPoint) {
		this.hyperPoint = hyperPoint;
	}
	
	
	public FingerPrintHyperPoint(FingerPrint fp, double[] hyperPoint) {
		super();
		this.fp = fp;
		this.hyperPoint = hyperPoint;
	}
	
	public double GetDistance(double[] hyperpoint2) throws Exception
	{
		if (hyperPoint.length != hyperpoint2.length)
		{
			throw new Exception("Hyper point has wrong number of dimensions");
		}
		
		double preSquareRoot = 0.0;
		for(int i=0; i<hyperPoint.length; i++)
		{
			preSquareRoot += Math.pow(hyperPoint[i] - hyperpoint2[i], 2.0);			
		}
	
		return Math.sqrt(preSquareRoot);
	}
	
}
