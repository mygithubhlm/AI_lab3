package knap;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.List;  
  
public class FileIO {  
    public static double weight = 0;
    public List<Object> read(File file,Type type) {  
        BufferedReader br = null;  
        List<Object> data = new ArrayList<Object>();  
        try {  
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));  
            String title = br.readLine(); 
            String[] con = title.split(" ");
            FileIO.weight = Double.parseDouble(con[0]);
            String inputLine = br.readLine();  
            StringBuilder sb = new StringBuilder("");  
            while(inputLine != null) {  
                sb.append(inputLine + " ");  
                inputLine = br.readLine();  
            }  
            String[] arr = sb.toString().split(" ");
            if (type == Type.TSP) {
            	int[] index = new int[arr.length/3];
            	double[] x = new double[arr.length/3];  
	            double[] y = new double[arr.length/3];
	            for(int i = 0; i < arr.length/3; i++) {  
	                index[i] = Integer.parseInt(arr[3*i]);  
	                x[i] = Double.parseDouble(arr[3*i + 1]);
	                y[i] = Double.parseDouble(arr[3*i + 2]);
	            } 
	            data.add(index);
	            data.add(x);  
	            data.add(y);  
	            return data;  
			}else{
				double[] weight = new double[arr.length/2];  
	            double[] profit = new double[arr.length/2]; 
	            for(int i = 0; i < arr.length/2; i++) {  
	                weight[i] = Double.parseDouble(arr[2*i]);  
	                profit[i] = Double.parseDouble(arr[2*i + 1]);  
	            }
	            data.add(weight);  
	            data.add(profit);  
	            return data;  
			}
            
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                br.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;  
    }  
      
    public void writer(String name,String content) { 
        File file = new File(name);  
        try {  
            file.createNewFile(); // 创建文件  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    
        byte bt[] = new byte[1024];  
        bt = content.getBytes();  
        try {  
            FileOutputStream in = new FileOutputStream(file);  
            try {  
                in.write(bt, 0, bt.length);  
                in.close();  
                 System.out.println("写入文件成功");  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    
    //test  
    public static void main(String[] args) {  
        File file = new File("src/GeneticAlgrithms/tsp.txt");  
        FileIO reader = new FileIO();
        List<Object> data = reader.read(file,Type.TSP);
        int[] index = (int[])data.get(0);
        double[] weight = (double[])data.get(1);  
        double[] profit = (double[])data.get(2); 
        for(int i = 0; i < index.length; i++) {  
            System.out.print(index[i] + "\t");  
        }  
        System.out.println();  
        for(int i = 0; i < weight.length; i++) {  
            System.out.print(weight[i] + "\t");  
        }  
        System.out.println();  
        for(int i = 0; i < profit.length; i++) {  
            System.out.print(profit[i] + "\t");  
        }  
    }  
  
}  