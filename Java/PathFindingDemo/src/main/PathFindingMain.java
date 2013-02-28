package main;

import java.awt.Color;

import javax.swing.JFrame;

import draw.Canvas;
import draw.RenderModel;
import draw.Renderer;

public class PathFindingMain extends JFrame {

	private static final long serialVersionUID = 4868944791791175161L;

	public static void main(String[] args) throws InterruptedException {
		PathFindingMain mainFrame = new PathFindingMain();
		mainFrame.setName("Path Finding Test");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Canvas canvas = new Canvas();
		canvas.setSize(1000, 800);
		canvas.setBackground(Color.GRAY);
		mainFrame.add(canvas);
		mainFrame.setVisible(true);
		mainFrame.setSize(1000, 800);
		RenderModel model = new RenderModel(canvas);
		Renderer.addModel(model);
		Thread.currentThread().join();
	}
}
