package com.gangoffive.project.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class BigProject {
    private String name;
    private List<SmallProject> smallProjects;

    public BigProject (int level) {
        switch (level) {
            case 0:name="体育";smallProjects.add(new SmallProject("球类"));smallProjects.add(new SmallProject("田径"));
            smallProjects.add(new SmallProject("水上项目"));smallProjects.add(new SmallProject("武术"));break;
            case 1:name="音乐";smallProjects.add(new SmallProject("乐器"));smallProjects.add(new SmallProject("声乐"));break;
            case 2:name="文学";smallProjects.add(new SmallProject("小说"));smallProjects.add(new SmallProject("散文"));
                smallProjects.add(new SmallProject("报道"));break;
            case 3:name="美术";smallProjects.add(new SmallProject("绘画"));smallProjects.add(new SmallProject("服饰"));
                smallProjects.add(new SmallProject("建筑"));break;
            case 4:name="自然";smallProjects.add(new SmallProject("动物培育"));smallProjects.add(new SmallProject("园艺"));
                smallProjects.add(new SmallProject("天文"));smallProjects.add(new SmallProject("地理"));break;
            case 5:name="生活";smallProjects.add(new SmallProject("八卦"));smallProjects.add(new SmallProject("手工"));
                smallProjects.add(new SmallProject("厨艺"));smallProjects.add(new SmallProject("游戏"));break;
        }
    }
}
