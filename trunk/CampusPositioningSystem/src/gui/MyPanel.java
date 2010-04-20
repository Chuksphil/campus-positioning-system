package gui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.NoninvertibleTransformException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class MyPanel extends JPanel 
{	
	private ArrayList<Shape> m_backgroundShapes = new ArrayList<Shape>();
	private ArrayList<Shape> m_foregroundShapes = new ArrayList<Shape>();
	
	private ArrayList<Geometry> m_backgroundGeoms = new ArrayList<Geometry>();
	private ArrayList<Geometry> m_foregroundGeoms = new ArrayList<Geometry>();
	
	
	private Viewport m_viewport;
	private Java2DConverter m_converter;
	
	public Viewport GetViewport()
	{
		return m_viewport;
	}
	
	public MyPanel()
	{
		m_viewport = new Viewport(this);
		m_converter = new Java2DConverter(m_viewport);
	}
	
	
	public void SetBackgroundShapes(String background) throws ParseException, NoninvertibleTransformException
	{					
		Reader backgroundReader = new StringReader(background);
		
	    WKTReader reader = new WKTReader();
	    m_backgroundGeoms = new ArrayList<Geometry>();	    
	    Geometry geom = reader.read(backgroundReader);
	    while (geom != null)
	    {
	    	m_backgroundGeoms.add(geom);
	    	geom = reader.read(backgroundReader);
	    }
	        
	    RemakeShapes();
	}
	
	public void SetForegroundShapes(String foreground) throws ParseException, NoninvertibleTransformException
	{				
		Reader foregroundReader = new StringReader(foreground);
		
	    WKTReader reader = new WKTReader();	    
	    m_foregroundGeoms = new ArrayList<Geometry>();	    
	    Geometry geom = reader.read(foregroundReader);
	    while (geom != null)
	    {
	    	m_foregroundGeoms.add(geom);
	    	geom = reader.read(foregroundReader);
	    }
	        
	    RemakeShapes();		
	}
	
	
	private void RemakeShapes() throws NoninvertibleTransformException
	{
		Envelope env = new Envelope();
		for(Geometry geom : m_foregroundGeoms)
		{
			env.expandToInclude(geom.getEnvelopeInternal());			
		}
		for(Geometry geom : m_backgroundGeoms)
		{
			env.expandToInclude(geom.getEnvelopeInternal());			
		}
	    
        m_viewport.zoom(env);
        
        m_backgroundShapes = new ArrayList<Shape>();
        for(Geometry geom : m_backgroundGeoms)
		{	        
	        Shape shape = m_converter.toShape(geom);
	        m_backgroundShapes.add(shape);	        
        }

        m_foregroundShapes = new ArrayList<Shape>();
        for(Geometry geom : m_foregroundGeoms)
		{	        	
        	Shape shape = m_converter.toShape(geom);
        	m_foregroundShapes.add(shape);	        
        }
	}
	
	
	protected void paintComponent(Graphics graphics) {
                
        try 
        {	
        	RemakeShapes();
        	Graphics2D g = (Graphics2D)graphics;
        	        	
        	g.setColor(Color.YELLOW);
        	g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        	for(Shape shape : m_backgroundShapes)
        	{
        		g.fill(shape);        		
        	}

        	g.setColor(Color.ORANGE);
        	g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        	for(Shape shape : m_backgroundShapes)
        	{
        		g.draw(shape);        		
        	}
        	
        	g.setColor(Color.BLUE);
        	g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        	for(Shape shape : m_foregroundShapes)
        	{
        		g.draw(shape);
        	}
        }
        catch (Exception e) 
        {
        
        }
    }
	
}
