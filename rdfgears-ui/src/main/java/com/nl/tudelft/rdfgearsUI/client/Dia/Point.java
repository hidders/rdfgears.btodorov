package com.nl.tudelft.rdfgearsUI.client.Dia;

public class Point {
	private int x;
	private int y;
	public Point(int _x, int _y){
		x = _x;
		y = _y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int _x){
		x = _x;
	}
	public void setY(int _y){
		y = _y;
	}
	public void setXY(int _x, int _y){
		x = _x;
		y = _y;
	}
}
