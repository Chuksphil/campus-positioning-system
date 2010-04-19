
/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI
 * for visualizing and manipulating spatial features with geometry and attributes.
 *
 * Copyright (C) 2003 Vivid Solutions
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * For more information, contact:
 *
 * Vivid Solutions
 * Suite #1A
 * 2328 Government Street
 * Victoria BC  V8T 5G5
 * Canada
 *
 * (250)385-6040
 * www.vividsolutions.com
 */

package client;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JPanel;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

/**
 * Controls the area on the model being viewed by a LayerViewPanel.
 */

//<<TODO:NAMING>> Rename to Viewport [Jon Aquino]
public class Viewport implements Java2DConverter.PointConverter {
    static private final int INITIAL_VIEW_ORIGIN_X = 0;
    static private final int INITIAL_VIEW_ORIGIN_Y = 0;
       
    
    private JPanel panel;

    /**
     * Origin of view as perceived by model, that is, in model space
     */
    private Point2D viewOriginAsPerceivedByModel =
        new Point2D.Double(INITIAL_VIEW_ORIGIN_X, INITIAL_VIEW_ORIGIN_Y);
    private double scale = 1;
    private AffineTransform modelToViewTransform;    

    public Viewport(JPanel panel) {
        this.panel = panel;
    }

    public void update() throws NoninvertibleTransformException {
        modelToViewTransform = modelToViewTransform(scale, viewOriginAsPerceivedByModel, panel.getHeight());        
    }

    public static AffineTransform modelToViewTransform(
        double scale,
        Point2D viewOriginAsPerceivedByModel,
        double panelHeight) 
    {
        AffineTransform modelToViewTransform = new AffineTransform();
        modelToViewTransform.translate(0, panelHeight);
        modelToViewTransform.scale(1, -1);
        modelToViewTransform.scale(scale, scale);
        modelToViewTransform.translate(
            -viewOriginAsPerceivedByModel.getX(),
            -viewOriginAsPerceivedByModel.getY());
        return modelToViewTransform;
    }

    public double getScale() {
        return scale;
    }

    /**
     * Set both values but repaint once.
     */
    public void initialize(double newScale, Point2D newViewOriginAsPerceivedByModel) {
        setScale(newScale);
        viewOriginAsPerceivedByModel = newViewOriginAsPerceivedByModel;

        //Don't call #update here, because this method may be called before the
        //panel has been made visible, causing LayerViewPanel#createImage
        //to return null, causing a NullPointerException in
        //LayerViewPanel#updateImageBuffer. [Jon Aquino]
    }

    public Point2D getOriginInModelCoordinates() {
        return viewOriginAsPerceivedByModel;
    }

    
    public Point2D toModelPoint(Point2D viewPoint) throws NoninvertibleTransformException {
        return getModelToViewTransform().inverseTransform(toPoint2DDouble(viewPoint), null);
    }

    private Point2D.Double toPoint2DDouble(Point2D p) {
        //If you pass a non-Double Point2D to an AffineTransform, the AffineTransform
        //will be done using floats instead of doubles. [Jon Aquino]
        if (p instanceof Point2D.Double) {
            return (Point2D.Double) p;
        }
        return new Point2D.Double(p.getX(), p.getY());
    }
    
    public Point2D toViewPoint(Point2D modelPoint) throws NoninvertibleTransformException {
        return getModelToViewTransform().transform(toPoint2DDouble(modelPoint), null);
    }
  
    public Point2D toViewPoint(Coordinate modelCoordinate) 
    throws NoninvertibleTransformException {
        //Optimization recommended by Todd Warnes [Jon Aquino 2004-02-06]
        Point2D.Double pt = new Point2D.Double(modelCoordinate.x, modelCoordinate.y);
        return getModelToViewTransform().transform(pt, pt);
    }


    public AffineTransform getModelToViewTransform() throws NoninvertibleTransformException {
        if (modelToViewTransform == null) {
            update();
        }

        return modelToViewTransform;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
    
  //<<TODO:IMPROVE>> Currently the zoomed image is aligned west in the viewport.
    //It should be centred. [Jon Aquino]
    public void zoom(Envelope modelEnvelope) throws NoninvertibleTransformException {
        if (modelEnvelope.isNull()) {
            return;
        }


        setScale(
            Math.min(
                panel.getWidth() / modelEnvelope.getWidth(),
                panel.getHeight() / modelEnvelope.getHeight()));
        double xCenteringOffset = ((panel.getWidth() / scale) - modelEnvelope.getWidth()) / 2d; 
        double yCenteringOffset = ((panel.getHeight() / scale) - modelEnvelope.getHeight()) / 2d; 
        viewOriginAsPerceivedByModel =
            new Point2D.Double(modelEnvelope.getMinX() - xCenteringOffset, modelEnvelope.getMinY() - yCenteringOffset);
        update();
    }

}
