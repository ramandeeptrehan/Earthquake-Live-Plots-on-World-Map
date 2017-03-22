package myCodes;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.*;

public class EarthquakeLiveData extends PApplet {
	
	private UnfoldingMap map;
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	public void setup()
	{
		size(950, 600, P2D);
		
		//AbstractMapProvider provider = new OpenStreetMap.OpenStreetMapProvider();
		map = new UnfoldingMap(this, 200, 50, 1500, 1500, new OpenStreetMap.OpenStreetMapProvider());
		// First 2 parameters represent where on canvas we need map to be located
		// Next 2 parameters represent the size of map object(width and height)
		
		map.zoomLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);

		/*
		//Location value = new Location(-38.14f, -73.03f);
		// Now we will add features to this Location
		Feature valueFeature = new PointFeature(value);
		valueFeature.addProperty("Title", "Valdivia, Chile");
		valueFeature.addProperty("Magnitude", "9.5");
		valueFeature.addProperty("Date", "May 22, 1960");
		valueFeature.addProperty("Year", "1960");
		
		Marker marker = new SimplePointMarker(value, valueFeature.getProperties()); // Marker is an interface
		// SimplePointMarker is more specific class.
		
		*/
		List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this,earthquakesURL);
		List<Marker> markers = new ArrayList<Marker>();
		for(PointFeature ft : earthquakes){
		    markers.add(new SimplePointMarker(ft.getLocation(), ft.getProperties()));
		}
		setMarkerColor(markers);
		map.addMarkers(markers);
	
	
	}
	
	
	
	public void setMarkerColor(List<Marker> markers)
	{
		
		int yellow = color(255,255,0);
		int red = color(255,0,0);
		
		for(Marker mk: markers)
		{
			if((float)mk.getProperty("magnitude")>4.5)
			{
				mk.setColor(red);
			}
			else 
			{
				mk.setColor(yellow);
			}
		
		}
	}
	
	public void draw()
	{
		background(10);
		map.draw();
	}

}