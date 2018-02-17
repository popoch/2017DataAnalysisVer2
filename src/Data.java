

import java.util.ArrayList;

public class Data {

	public static ArrayList<Pupildata> pData = new ArrayList<>();
	public static ArrayList<Pupildata> mainData = new ArrayList<>();
	public static ArrayList<Pupildata> realData = new ArrayList<>();
	public static ArrayList<String> original = new ArrayList<>();
	public static int[] size = new int[92];
	
	public static Drawmap draw = new Drawmap();
	
	public static String file_name;
	public static String number;
	
	public static float rBig = 156f;
	public static float rSmall = 83.15f;	//94.48 - 83.15 = 11.33
	public static float dBig = 313.6f;
	public static float dSmall = 166.3f;
	public static float startX;
	public static float startY;
	public static int flag = 0;
	public static double change = 3.913;	//degree change
	
	public static int sTime = 0;
	public static int eTime = 0;
}