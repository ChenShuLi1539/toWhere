package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.CSOperateBiz;
import com.gangoffive.project.demo.mapper.CSMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSOperateimpl implements CSOperateBiz {

    @Autowired
    CSMapper csMapper;

    @Override
    public ArrayList<ArrayList<String>> addCsTable(ArrayList<ArrayList<String>> table,String tableName) {
        int sucessNum=0;
        int repyNum=0;
        int notRegistNum=0;
        ArrayList<ArrayList<String>> returnTable=new ArrayList<>();
        ArrayList<String> repyRow=new ArrayList<>();
        ArrayList<String> notRegistRow=new ArrayList<>();
        ArrayList<String> sucessRow=new ArrayList<>();
        int couresId=Integer.parseInt(tableName.substring(0, tableName.indexOf(".")));
        for (int i = 0; i <table.size() ; i++) {
            List<String> row = table.get(i);
            try {
                sucessNum++;
                System.out.println("this is:"+i);
                csMapper.addCSTabbleByline(couresId,Integer.parseInt(row.get(0)));
            }catch (DuplicateKeyException e){
                sucessNum--;
                repyNum++;
                System.out.println("已经存在"+"Id:"+Integer.parseInt(row.get(0)));
                repyRow.add(row.get(0));
            }catch (DataIntegrityViolationException e){
                sucessNum--;
                notRegistNum++;
                System.out.println("学生没注册"+"Id:"+Integer.parseInt(row.get(0)));
                notRegistRow.add(row.get(0));
            }
        }
        sucessRow.add(Integer.toString(sucessNum));
        returnTable.add(repyRow);
        returnTable.add(notRegistRow);
        returnTable.add(sucessRow);
        //第一个数组是重复的Id，第二个是没注册学生的Id,第三个是成功的数量
        return returnTable;
    }
}
