/**
 * 文件操作工具类
 */
package com.dao;


import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对excel文件操作的工具类
 */
public enum  OperateData {
    INSTANCE;
    private static Cell cell;
    private static Label label;
    private static Sheet DataSheet;
    private static Workbook DataWorkbook;
    private static WritableSheet writableSheet;
    private static WritableWorkbook writableWorkbook;
    private static BufferedReader bufferedReader;

    /**
     * 获取数据库路径，权重参数路径
     * type 0为数据库路径，1为权重参数路径
     */
    public String getDatabasePath(int type){
        String Path = "";
        try {
            if (type == 0){
                Path = System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/database/database.xls";
            }else if(type == 1){
                Path = System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/database/para.xls";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Path;
    }
    /**
     * 获取用户上传文件的路径
     * @param type 0为诊断路径，1为添加路径
     */
    public String getManagerPath(int type){
        String ManagerPath = "";
        try {
            if (type == 0){
                ManagerPath =System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/upload/";
            }else if (type == 1){
                ManagerPath =System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/add/";
            }
            File file1 = new File(ManagerPath);
            String[] str = file1.list();
            ManagerPath = ManagerPath+str[0];
        }catch (Exception e){
            e.printStackTrace();
        }
        return ManagerPath;
    }
    /**
     * csv文件转换xls
     */
    public boolean operateCsv(File file,File wfile) {
    	boolean flag = false;
        String line = null;
        String[] newData = null;
        int j = 0;
        try {
            if (!wfile.exists()){
                writableWorkbook = Workbook.createWorkbook(wfile);
            }else {
                wfile.delete();
                writableWorkbook = Workbook.createWorkbook(wfile);
            }
            writableSheet = writableWorkbook.createSheet("第一页",0);
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));

            while ((line = bufferedReader.readLine()) != null){
                if (line.trim().length() > 1){
                    String[] data = line.split(",");
                    for (int i = 0 ; i < data.length ; i++){
                        data[i] = data[i].replace("\"","").trim();
                    }
                    if ("润燥".equals(data[23]) || "".equals(data[23])){
                        System.out.println("手持设备数据");
                        newData = insert(data,7,"社保卡号");
                        if (j != 0){
                            newData[22] = coatToData(newData[22]);
                            newData[23] = largeToData(newData[23]);
                            newData[25] = moistToData(newData[24]);
                            newData[27] = fatToData(newData[27]);
                            newData[28] = toothToData(newData[28]);
                            newData[30] = creakToData(newData[30]);
                            newData[31] = bruiseToData(newData[31]);
                            newData[35] = tongueToData(newData[35]);
                        }
//                        System.out.println(Arrays.toString(newData));

                    }else if (data[25].length() != 0){
                        System.out.println("台式设备数据");
                        if (j != 0){
                            data[25] = moistToData(data[25]);
                            data[27] = fatToData(data[27]);
                            data[31] = bruiseToData(data[31]);
                        }
                        newData = data;
//                        System.out.println(Arrays.toString(data));
                    }
                    for (int i = 0 ; i < newData.length ; i++){
                        Label label = new Label(i,j,newData[i].trim());
                        writableSheet.addCell(label);
                    }
                    j++;
                }
            }
            flag = true;
            writableWorkbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    if (writableWorkbook != null){
                        writableWorkbook.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    	return flag;
    }
    /**
     * 将文件写入数据库
     */
    public int WriteToDatabase(ManagerData managerData,String[] data){
        int i = 0;
        int length = this.getDataRows(this.getDatabasePath(0));
        try {
            File file = new File(this.getDatabasePath(0));
            DataWorkbook = Workbook.getWorkbook(file);
            writableWorkbook = Workbook.createWorkbook(file,DataWorkbook);
            writableSheet = writableWorkbook.getSheet(0);
            label = null;

            label = new Label(0,length,managerData.getManagerName());
            writableSheet.addCell(label);
            label = new Label(12,length,managerData.getRGB_R());
            writableSheet.addCell(label);
            label = new Label(13,length,managerData.getRGB_G());
            writableSheet.addCell(label);
            label = new Label(14,length,managerData.getRGB_B());
            writableSheet.addCell(label);
            label = new Label(15,length,managerData.getHSV_H());
            writableSheet.addCell(label);
            label = new Label(16,length,managerData.getHSV_S());
            writableSheet.addCell(label);
            label = new Label(17,length,managerData.getHSV_V());
            writableSheet.addCell(label);
            label = new Label(18,length,managerData.getLAB_L());
            writableSheet.addCell(label);
            label = new Label(19,length,managerData.getLAB_L());
            writableSheet.addCell(label);
            label = new Label(20,length,managerData.getLAB_L());
            writableSheet.addCell(label);
            label = new Label(22,length,managerData.getCoat());
            writableSheet.addCell(label);
            label = new Label(23,length,managerData.getLarge());
            writableSheet.addCell(label);
            label = new Label(25,length,managerData.getMoist());
            writableSheet.addCell(label);
            label = new Label(27,length,managerData.getFat());
            writableSheet.addCell(label);
            label = new Label(28,length,managerData.getTooth());
            writableSheet.addCell(label);
            label = new Label(30,length,managerData.getCreak());
            writableSheet.addCell(label);
            label = new Label(31,length,managerData.getBruise());
            writableSheet.addCell(label);
            label = new Label(35,length,managerData.getTongue());
            writableSheet.addCell(label);
            label = new Label(36,length,data[0]);
            writableSheet.addCell(label);
            label = new Label(37,length,data[1]);
            writableSheet.addCell(label);
            label = new Label(38,length,data[2]);
            writableSheet.addCell(label);

            writableWorkbook.write();
            i=1;
            writableWorkbook.close();
            DataWorkbook.close();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return i;
    }
    /**
     * 根据传入的编号获取处方和健康指导
     */
    public String[] getResult(String path,int id){
        String[] result = new String[3];
        try {
            DataWorkbook = Workbook.getWorkbook(new File(path));
            DataSheet = DataWorkbook.getSheet(0);

            Cell[] cells = new Cell[3];

            cells[0] = DataSheet.getCell(36,id);
            cells[1] = DataSheet.getCell(37,id);
            cells[2] = DataSheet.getCell(38,id);

            result[0] = cells[0].getContents().trim();
            result[1] = cells[1].getContents().trim();
            result[2] = cells[2].getContents().trim();

            DataWorkbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取用户上传的文件的方法
     */
    public void ReadManager(String ManagerPath, ManagerData managerData){
        try {
            DataWorkbook = Workbook.getWorkbook(new File(ManagerPath));
            DataSheet = DataWorkbook.getSheet(0);

            Cell[] cells = new Cell[18];

            
            
            cells[0] = DataSheet.getCell(0,DataSheet.getRows()-1);
            cells[1] = DataSheet.getCell(12,DataSheet.getRows()-1);
            cells[2] = DataSheet.getCell(13,DataSheet.getRows()-1);
            cells[3] = DataSheet.getCell(14,DataSheet.getRows()-1);
            cells[4] = DataSheet.getCell(15,DataSheet.getRows()-1);
            cells[5] = DataSheet.getCell(16,DataSheet.getRows()-1);
            cells[6] = DataSheet.getCell(17,DataSheet.getRows()-1);
            cells[7] = DataSheet.getCell(18,DataSheet.getRows()-1);
            cells[8] = DataSheet.getCell(19,DataSheet.getRows()-1);
            cells[9] = DataSheet.getCell(20,DataSheet.getRows()-1);

            cells[10] = DataSheet.getCell(22,DataSheet.getRows()-1);
            cells[11] = DataSheet.getCell(23,DataSheet.getRows()-1);
            cells[12] = DataSheet.getCell(25,DataSheet.getRows()-1);
            cells[13] = DataSheet.getCell(27,DataSheet.getRows()-1);
            cells[14] = DataSheet.getCell(28,DataSheet.getRows()-1);
            cells[15] = DataSheet.getCell(30,DataSheet.getRows()-1);
            cells[16] = DataSheet.getCell(31,DataSheet.getRows()-1);
            cells[17] = DataSheet.getCell(35,DataSheet.getRows()-1);

            managerData.setManagerName(cells[0].getContents().trim());

            managerData.setRGB_R(cells[1].getContents().trim());
            managerData.setRGB_G(cells[2].getContents().trim());
            managerData.setRGB_B(cells[3].getContents().trim());
            managerData.setHSV_H(cells[4].getContents().trim());
            managerData.setHSV_S(cells[5].getContents().trim());
            managerData.setHSV_V(cells[6].getContents().trim());
            managerData.setLAB_L(cells[7].getContents().trim());
            managerData.setLAB_A(cells[8].getContents().trim());
            managerData.setLAB_B(cells[9].getContents().trim());

            managerData.setCoat(cells[10].getContents().trim());
            managerData.setLarge(cells[11].getContents().trim());
            managerData.setMoist(cells[12].getContents().trim());
            managerData.setFat(cells[13].getContents().trim());
            managerData.setTooth(cells[14].getContents().trim());
            managerData.setCreak(cells[15].getContents().trim());
            managerData.setBruise(cells[16].getContents().trim());
            managerData.setTongue(cells[17].getContents().trim());

            DataWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取权重参数文件的方法
     */
    public void ReadPara(String ParaPath, ParaData paraData){
        try {
            DataWorkbook = Workbook.getWorkbook(new File(ParaPath));
            DataSheet = DataWorkbook.getSheet(0);

            Cell[] cells = new Cell[10];

            cells[0] = DataSheet.getCell(11,1);
            cells[1] = DataSheet.getCell(12,1);
            cells[2] = DataSheet.getCell(13,1);
            cells[3] = DataSheet.getCell(14,1);
            cells[4] = DataSheet.getCell(15,1);
            cells[5] = DataSheet.getCell(16,1);
            cells[6] = DataSheet.getCell(17,1);
            cells[7] = DataSheet.getCell(18,1);

            paraData.setParaTongueColor(Double.parseDouble(cells[0].getContents().trim()));
            paraData.setParaCoatColor(Double.parseDouble(cells[1].getContents().trim()));
            paraData.setParaLarge(Double.parseDouble(cells[2].getContents().trim()));
            paraData.setParaMoist(Double.parseDouble(cells[3].getContents().trim()));
            paraData.setParaFat(Double.parseDouble(cells[4].getContents().trim()));
            paraData.setParaTooth(Double.parseDouble(cells[5].getContents().trim()));
            paraData.setParaCreake(Double.parseDouble(cells[6].getContents().trim()));
            paraData.setParaBruise(Double.parseDouble(cells[7].getContents().trim()));

            DataWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 向数据库对象设置数据
     */
    public void ReadDatabase(DataInDatabase dataInDatabase,Map<Integer,List> map,int id){
    	if(id==1) {
    		id += 1;
    	}
        dataInDatabase.setName(map.get(id).get(0).toString().trim());
        dataInDatabase.setRGB_R(map.get(id).get(12).toString().trim());
        dataInDatabase.setRGB_G(map.get(id).get(13).toString().trim());
        dataInDatabase.setRGB_B(map.get(id).get(14).toString().trim());
        dataInDatabase.setHSV_H(map.get(id).get(15).toString().trim());
        dataInDatabase.setHSV_S(map.get(id).get(16).toString().trim());
        dataInDatabase.setHSV_V(map.get(id).get(17).toString().trim());
        dataInDatabase.setLAB_L(map.get(id).get(18).toString().trim());
        dataInDatabase.setLAB_A(map.get(id).get(19).toString().trim());
        dataInDatabase.setLAB_B(map.get(id).get(20).toString().trim());

        dataInDatabase.setCoatColor(map.get(id).get(22).toString().trim());
        dataInDatabase.setLarge(map.get(id).get(23).toString().trim());
        dataInDatabase.setMoist(map.get(id).get(25).toString().trim());
        dataInDatabase.setFat(map.get(id).get(27).toString().trim());
        dataInDatabase.setTooth(map.get(id).get(28).toString().trim());
        dataInDatabase.setCreak(map.get(id).get(30).toString().trim());
        dataInDatabase.setBruise(map.get(id).get(31).toString().trim());
        dataInDatabase.setTongueColor(map.get(id).get(35).toString().trim());

        dataInDatabase.setRecipe(map.get(id).get(36).toString().trim());
        dataInDatabase.setHealth(map.get(id).get(37).toString().trim());
        dataInDatabase.setCartoon(map.get(id).get(38).toString().trim());
    }
    /**
     * 将文件读入内存
     */
    public Map<Integer,List> LoadData(){
        Map<Integer,List> map = new HashMap<Integer, List>();
        try {
            DataWorkbook = Workbook.getWorkbook(new File(this.getDatabasePath(0)));
            DataSheet = DataWorkbook.getSheet(0);
            for (int i = 0 ; i < DataSheet.getRows() ; i++){
                List list = new ArrayList();
                for (int j = 0 ; j < DataSheet.getColumns() ; j++){
                    cell = DataSheet.getCell(j,i);
                    list.add(cell.getContents().trim());
                }
                map.put(i+1,list);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 获取数据库中数据条数
     * @param DatabasePath 数据库文件的路径
     */
    public int getDataRows(String DatabasePath){
        int length = 0;
        try {
            DataWorkbook = Workbook.getWorkbook(new File(DatabasePath));
            length = DataWorkbook.getSheet(0).getRows();
            DataWorkbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return length;
    }
    /**
     * 读取苔色数据并转换成对应数据
     */
    public double[] CoatToNum(String[] Data){
        double[] DataToNum = new double[5];
        for (int i=0 ; i<Data.length ; i++){
            if (Data[i].equals("-")){
                DataToNum[i] = 0;
            }else if (Data[i].equals("白")){
                DataToNum[i] = 1;
            }else if (Data[i].equals("黄白相兼")){
                DataToNum[i] = 2;
            }else if (Data[i].equals("黄")){
                DataToNum[i] = 3;
            }else if (Data[i].equals("灰黑")){
                DataToNum[i] = 4;
            }
        }
        return DataToNum;
    }
    /**
     * 读取厚薄数据并转换成对应数据
     */
    public double LargeToNum(String[] Data){
        double DataToNum = 0;
        if (Data[0].equals("苔无")){
            DataToNum = 0;
        }else if (Data[0].equals("苔少")){
            DataToNum = 1;
        }else if (Data[0].equals("薄")){
            DataToNum = 5;
        }else if (Data[0].equals("厚")){
            DataToNum = 10;
        }
        return DataToNum;
    }
    /**
     * 读取润燥数据并转换成对应数据
     */
    public double MoistToNum(String[] Data){
    	System.out.println(Arrays.toString(Data));
    	System.out.println("OperateData.MoistToNum()");
    	
        double DataToNum = 0;
        if (Data[0].equals("-")){
            DataToNum = 0;
        }else{
            String[] arr = Data[0].split("，");
            
            if ("中".equals(arr[1])||"根".equals(arr[1])){
                DataToNum = 1;
            }else if ("中根".equals(arr[1])){
                DataToNum = 2;
            }else if ("全部".equals(arr[1])){
                DataToNum = 5;
            }
        }
        return DataToNum;
    }
    /**
     * 读取胖瘦数据并转换成对应数据
     */
    public double FatToNum(String[] Data){
        double DataToNum = 0;
        if (Data[0].equals("瘦")){
            DataToNum = 0;
        }else if (Data[0].equals("适中")){
            DataToNum = 1;
        }else if (Data[0].equals("胖")){
            DataToNum = 2;
        }
        return DataToNum;
    }
    /**
     * 读取齿痕数据并转换成对应数据
     */
    public double ToothToNum(String[] Data){
        double DataToNum = 0;
        if (Data[0].equals("无齿痕")){
            DataToNum = 0;
        }else if (Data[0].equals("有齿痕")){
            DataToNum = 1;
        }
        return DataToNum;
    }
    /**
     * 读取裂纹数据并转换成对应数据
     */
    public double CreakToNum(String[] Data){
        double DataToNum = 0;
        if (Data[0].equals("无裂纹")){
            DataToNum = 0;
        }else if (Data[0].equals("有裂纹")){
            DataToNum = 1;
        }
        return DataToNum;
    }
    /**
     * 读取瘀斑数据并转换成对应数据
     */
    public double BruiseToNum(String[] Data){
        double DataToNum = 0;
        if (Data[0].equals("无瘀斑")){
            DataToNum = 0;
        }else if (Data[0].equals("有瘀斑")){
            DataToNum = 1;
        }
        return DataToNum;
    }
    /**
     * 转换苔色数据
     */
    public String coatToData(String data){
        String result = "";
        if (data != null && !"".equals(data)){
            if ("-".equals(data) || "无".equals(data)){
                result = "-|-|-|-|-";
            }else if ("白".equals(data)){
                result = "白|-|-|-|-";
            }else if ("黄白相兼".equals(data)){
                result = "黄白相兼|-|-|-|-";
            }else if ("黄".equals(data)){
                result = "黄|-|-|-|-";
            }else if ("灰黑".equals(data)){
                result = "灰黑|-|-|-|-";
            }else {
            	result = "黄白相兼|-|-|-|-";
            }
        }else {
            result = "黄白相兼|-|-|-|-";
        }
        return result;
    }
    /**
     * 转换厚薄数据
     */
    public String largeToData(String data){
        String result = "";
        if (data != null && !"".equals(data)){
            if ("苔无".equals(data)){
                result = "苔无|1.00";
            }else if ("苔少".equals(data)){
                result = "苔少|1.00";
            }else if ("厚苔".equals(data)){
                result = "厚|3.00";
            }else if ("薄苔".equals(data)){
                result = "薄|3.00";
            }else {
            	result = "苔少|1.00";
            }
        }else {
            result = "薄|3.00";
        }
        return result;
    }
    /**
     * 转换润燥(包含台式设备的处理)
     */
    public String moistToData(String data){
        String result = "";
        if ("".equals(data)){
            result = "-|0.00";
        }else {
            String[] data1 = data.split("\\|");
            if ("无".equals(data1[0])){
                result = "-|0.00";
            }else if ("腻".equals(data1[0])){
                result = "腻，中根|0.00";
            }else {
            	result = "腻，中根|0.00";
            }
        }
        return result;
    }
    /**
     * 转换胖瘦（包含台式设备的处理）
     */
    public String fatToData(String data){
        String result = "";
        if (data != null && !"".equals(data)){
            if (data.length() > 3){
                String[] data1 = data.split("\\|");
                if ("胖瘦适中".equals(data1[0])){
                    result = "适中|1.00";
                }else {
                    return data;
                }
            }
            if ("适中".equals(data)){
                result = "适中|1.00";
            }else if ("胖".equals(data)){
                result = "胖|1.00";
            }else if ("瘦".equals(data)){
                result = "瘦|1.00";
            }else {
            	result = "适中|1.00";
            }
        }else {
            result = "适中|1.00";
        }
        return result;
    }
    /**
     * 转换齿痕数据
     */
    public String toothToData(String data){
        String result = "";
        if (data != null && !"".equals(data)){
            if ("有齿痕".equals(data)){
                result = "有齿痕|1.00";
            }else if ("无齿痕".equals(data)){
                result = "无齿痕|0.00";
            }else {
            	result = "无齿痕|0.00";
            }
        }else {
            result = "无齿痕|0.00";
        }
        return result;
    }
    /**
     * 转换裂纹数据
     */
    public String creakToData(String data){
        String result = "";
        if (data != null && !"".equals(data)){
            if ("有裂纹".equals(data)){
                result = "有裂纹|1.00";
            }else if ("无裂纹".equals(data)){
                result = "无裂纹|0.00";
            }else {
            	result = "无裂纹|0.00";
            }
        }else {
            result = "无裂纹|0.00";
        }
        return result;
    }
    /**
     * 转换瘀斑（包含台式设备的处理）
     */
    public String bruiseToData(String data){
        String result = "";
        if (data != null && !"".equals(data)){
            if (data.length() > 3){
                String[] data1 = data.split("\\|");
                if ("有瘀点".equals(data1[0])){
                    result = "有瘀斑|1.00";
                }else if ("无瘀点".equals(data1[0])){
                    result = "无瘀斑|0.00";
                }
            }
            if ("有瘀点".equals(data)){
                result = "有瘀斑|1.00";
            }else if ("无瘀点".equals(data)){
                result = "无瘀斑|0.00";
            }else {
            	result = "无瘀斑|0.00";
            }
        }else {
            result = "无瘀斑|0.00";
        }
        return result;
    }
    /**
     * 转换整体舌色数据
     */
    public String tongueToData(String data){
        String result = "";
        if (data != null && !"".equals(data)){
            if ("淡".equals(data)){
                result = "舌淡";
            }else if ("淡红".equals(data)){
                result = "舌淡红";
            }else if ("红".equals(data)){
                result = "舌红";
            }else if ("暗红".equals(data)){
                result = "舌暗红";
            }else if ("淡紫".equals(data)){
                result = "舌淡紫";
            }else if ("紫暗".equals(data)){
                result = "舌紫暗";
            }else if ("绛".equals(data)){
                result = "舌绛";
            }else {
            	result = "舌暗红";
            }
        }else {
            result = "舌淡红";
        }
        return result;
    }
    /**
     * 数组扩容
     */
    public String[] insert(String[] data,int i,String result){
        String[] newData = new String[data.length+1];
        for (int j = 0 ; j < data.length ; j++){
            newData[j] = data[j];
        }
        for (int j = newData.length - 2 ; j > i ;j --){
            newData[j+1] = newData[j];
        }
        newData[i+1] = result;
        return newData;
    }
    /**
     * 删除文件
     */
    public void DeleteFiles(String path){
        try {
            File files = new File(path);
            if (files.isDirectory()){
                for (File file : files.listFiles()){
                    if (!file.isDirectory())
                        file.delete();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
