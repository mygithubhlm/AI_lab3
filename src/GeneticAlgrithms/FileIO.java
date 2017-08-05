package GeneticAlgrithms;
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
    //文件读取
    public List<Object> read(File file,Type type) {  
        BufferedReader br = null;  
        List<Object> data = new ArrayList<Object>();  
        try {  
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));  
            String title = br.readLine(); 
            String[] con = title.split(" ");
            FileIO.weight = Double.parseDouble(con[0]);
            String inputfile = br.readLine();  
            StringBuilder sb = new StringBuilder("");  
            while(inputfile != null) {  
                sb.append(inputfile + " ");  
                inputfile = br.readLine();  
            }  
            String[] aStrings = sb.toString().split(" ");
//            根据不同的类型来生成不同的数据结构，当类型为旅行商时
            if (type == Type.TSP) {
            	int[] index = new int[aStrings.length/3];
            	double[] x = new double[aStrings.length/3];  
	            double[] y = new double[aStrings.length/3];
	            for(int i = 0; i < aStrings.length/3; i++) {  
	                index[i] = Integer.parseInt(aStrings[3*i]);  
	                x[i] = Double.parseDouble(aStrings[3*i + 1]);
	                y[i] = Double.parseDouble(aStrings[3*i + 2]);
	            } 
	            data.add(index);
	            data.add(x);  
	            data.add(y);  
	            return data;  
			}else{//根据不同的类型来生成不同的数据结构，当类型为背包时
				double[] weight = new double[aStrings.length/2];  
	            double[] profit = new double[aStrings.length/2]; 
	            for(int i = 0; i < aStrings.length/2; i++) {  
	                weight[i] = Double.parseDouble(aStrings[2*i]);  
	                profit[i] = Double.parseDouble(aStrings[2*i + 1]);  
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
//      写文件
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
                 System.out.println("write sucessful!");  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
}  