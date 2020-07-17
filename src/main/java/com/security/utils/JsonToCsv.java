package com.security.utils;

import com.security.common.utils.GsonUtils;
import com.security.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

/**
 * 为了给南通大学生成csv自己写的工具类
 * @Author: tongq
 * @Date: 2020/5/11 16:03
 * @since：
 */
@Slf4j
public class JsonToCsv {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\tongq\\Desktop\\nthx");
        File[] tempList = file.listFiles();
        ArrayList<Map> arrayList = new ArrayList<>();
        for (File file1 : tempList) {
            if (! file1.getName().endsWith(".json")){
                continue;
            }
            String filename = file.getAbsolutePath() + "\\"+file1.getName();
            String json = FileUtils.readFile(filename);
            if (json.startsWith("{")){
                Map map = GsonUtils.convertToBean(json, Map.class);
                Object hits = map.get("hits");
                String hitss = GsonUtils.convertToString(hits);
                Map map1 = GsonUtils.convertToBean(hitss, Map.class);
                Object hits1 = map1.get("hits");
                json = GsonUtils.convertToString(hits1);
            }
            List<Map<String, Object>> maps = GsonUtils.jsonToListMaps(json);
            ArrayList<Map> list = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                HashMap<Object, Object> mapz = new HashMap<>();
                ArrayList<Map> rebuild = new ArrayList<>();
                ArrayList<Map> all = rebuild(map, mapz, rebuild,"");
                HashMap<Object, Object> hashMap = new HashMap<>();
                for (Map o : all) {
                    hashMap.putAll(o);
                }
                list.add(hashMap);
            }
            for (Map map : list) {
                arrayList.add(map);
            }
            //生成对应的文件
            json = GsonUtils.convertToString(list);
            String json2Csv = FileUtils.Json2Csv(json);
            String targetPath =filename.replace(".json", ".csv");
            FileUtils.writeToString(targetPath,json2Csv);
        }
        //合并成一个文件
        String json = GsonUtils.convertToString(arrayList);
        String json2Csv = FileUtils.Json2Csv(json);
        FileUtils.writeToString(file.getAbsolutePath()+"\\"+"zong.csv",json2Csv);
    }

    public static ArrayList<Map> rebuild(Map map,Map mapz,ArrayList arrayList,String parentKey){
        Set<String> keys = map.keySet();
        for (String key : keys) {
            String keyname = key;
            if (! StringUtils.isEmpty(parentKey)){
                keyname  = parentKey+"."+key;
            }
            Object o = map.get(key);
            if (o instanceof String || o instanceof Integer || o instanceof Double || o instanceof Boolean){
                //没有下层则直接放到mapz
                mapz.put(keyname,String.valueOf(map.get(key)));
                arrayList.add(mapz);
            }else {
                //依然是个对象则继续遍历
                String s = GsonUtils.convertToString(o);
                Map<String, Object> map2 = GsonUtils.jsonToMaps(s);
                rebuild(map2,mapz,arrayList,keyname);
            }
        }
        return arrayList;
    }

}
