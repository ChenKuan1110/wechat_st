/**
 * 数据诊断实现类
 */
package com.dao;

import com.dao.ManagerData;
import com.dao.ParaData;

import java.util.List;
import java.util.Map;

public class ContrastData {
    private static ContrastData contrastData = new ContrastData();

    private ContrastData(){
    }
    public static ContrastData getInstance(){
        return contrastData;
    }
    /**
     * 用户和数据库进行比较，并得出数据库最小编号
     * @param managerData 用户数据对象
     * @param paraData 权重参数对象
     */
    public int CompareData(ManagerData managerData, ParaData paraData){
        Map<Integer,List> map = OperateData.INSTANCE.LoadData();

        int minId = -1;
        double[] Mcoat,Dcoat;
        double Mlarge,Mmoist,Mfat,Mtooth,Mcreak,Mbruise,Dlarge,Dmoist,Dfat,Dtooth,Dcreak,Dbruise;
        double colorTem,minTotal = 0;
        double[] distTSColor = new double[map.size()],
                distChar = new double[map.size()],
                disTotal = new double[map.size()];

        OperateData.INSTANCE.ReadManager(OperateData.INSTANCE.getManagerPath(0),managerData);
        OperateData.INSTANCE.ReadPara(OperateData.INSTANCE.getDatabasePath(1),paraData);

        Mcoat = new double[5];
        Dcoat = new double[5];
        Mcoat = OperateData.INSTANCE.CoatToNum(managerData.getCoat().split("\\|"));
        Mlarge = OperateData.INSTANCE.LargeToNum(managerData.getLarge().split("\\|"));
        Mmoist = OperateData.INSTANCE.MoistToNum(managerData.getMoist().split("\\|"));
        Mfat = OperateData.INSTANCE.FatToNum(managerData.getFat().split("\\|"));
        Mtooth = OperateData.INSTANCE.ToothToNum(managerData.getTooth().split("\\|"));
        Mcreak = OperateData.INSTANCE.CreakToNum(managerData.getCreak().split("\\|"));
        Mbruise = OperateData.INSTANCE.BruiseToNum(managerData.getBruise().split("\\|"));


        for (int i = 2 ; i < map.size() ; i++){
            if (managerData.getTongue().length()==map.get(i).get(35).toString().length()){
                if (managerData.getTongue().equals(map.get(i).get(35).toString())){
                    Dcoat = OperateData.INSTANCE.CoatToNum(map.get(i).get(22).toString().split("\\|"));
                    Dlarge = OperateData.INSTANCE.LargeToNum(map.get(i).get(23).toString().split("\\|"));
                    Dmoist = OperateData.INSTANCE.MoistToNum(map.get(i).get(25).toString().split("\\|"));
                    Dfat = OperateData.INSTANCE.FatToNum(map.get(i).get(27).toString().split("\\|"));
                    Dtooth = OperateData.INSTANCE.ToothToNum(map.get(i).get(28).toString().split("\\|"));
                    Dcreak = OperateData.INSTANCE.CreakToNum(map.get(i).get(30).toString().split("\\|"));
                    Dbruise = OperateData.INSTANCE.BruiseToNum(map.get(i).get(31).toString().split("\\|"));

                    colorTem = (Mcoat[0] + Mcoat[1] + Mcoat[2] + Mcoat[3] + Mcoat[4]) - (Dcoat[0] + Dcoat[1] + Dcoat[2] + Dcoat[3] + Dcoat[4]);
                    distTSColor[i-2] = paraData.getParaTongueColor() * Math.abs(colorTem);

                    colorTem = Math.abs(Mcoat[0] - Dcoat[0]) + Math.abs(Mcoat[1] - Dcoat[1]) + Math.abs(Mcoat[2] - Dcoat[2])
                            + Math.abs(Mcoat[3] - Dcoat[3]) + Math.abs(Mcoat[4] - Dcoat[4]);
                    distTSColor[i-2] = distTSColor[i] + paraData.getParaCoatColor() * Math.abs(colorTem);

                    distChar[i-2] = paraData.getParaLarge() * Math.abs(Mlarge - Dlarge) + paraData.getParaMoist() * Math.abs(Mmoist - Dmoist) +
                            paraData.getParaFat() * Math.abs(Mfat - Dfat) + paraData.getParaTooth() * Math.abs(Mtooth - Dtooth) +
                            paraData.getParaCreake() * Math.abs(Mcreak - Dcreak) + paraData.getParaBruise() * Math.abs(Mbruise - Dbruise);
                    disTotal[i-2] = distTSColor[i-2] + distChar[i-2];

                    if (minId == -1){
                        minId = i;
                        minTotal = disTotal[i-2];
                    }else {
                        if (disTotal[i-2] < minTotal){
                            minTotal = disTotal[i-2];
                            minId = i;
                        }
                    }
                }else
                    disTotal[i-2] = 500;
            }else
                disTotal[i-2] = 500;
        }
        if (minTotal == 500 || minId == -1){
            return 2;
        }else {
            return minId;
        }
    }
}
