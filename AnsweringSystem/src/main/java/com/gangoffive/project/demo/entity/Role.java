package com.gangoffive.project.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Role {
    //游戏内使用的角色
    private String name;
    private int sex;//0表示女，1表示男
    private List<Nature> natures;
    private Target target;
    private List<Skill> skills;

    public Role (String name,int sex,int nature1,int nature2,int nature3,int nature4,int nature5,int nature6,String targetName,
                 String targetDescription,Double targetprofit,List<Skill> skills) {
        this.name=name;
        this.sex=sex;
        this.natures= new ArrayList<>();
        this.natures.add(new Nature("聪颖",nature1));
        this.natures.add(new Nature("勇敢",nature2));
        this.natures.add(new Nature("顽皮",nature3));
        this.natures.add(new Nature("细腻",nature4));
        this.natures.add(new Nature("坚韧",nature5));
        this.natures.add(new Nature("谨慎",nature6));
        this.target=new Target(targetName,targetDescription,targetprofit);
        this.skills=skills;
    }

    public String toString() {
        String string=name+"为";
        String _sex;
        if(sex==0)
            _sex="女性";
        else
            _sex="男性";
        string+=_sex+"，";
        for(Nature e:natures) {
            string+=e.getName()+"为"+e.getLevel()+"，";
        }
        string+="\n人生目标是"+target.getName()+"："+target.getDescription()+"，收益为"+target.getProfit()+"点心情\n技能：";
        int a = 1;
        for(Skill e:skills) {
            string+=a+"."+e.getName()+"："+e.getDescription()+"\n";
            a++;
        }
        return string;
    }
}
