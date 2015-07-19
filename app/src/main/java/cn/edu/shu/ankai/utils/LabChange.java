package cn.edu.shu.ankai.utils;

/**
 * Created by gxyzw_000 on 2015/7/14.
 */
public class LabChange {

    public static String parentTOschool(String raw_item) {
        if (raw_item.equals("") || raw_item.length()<3||raw_item.equals("暂无")) {
            return "暂无";
        }
        else {
            String item = raw_item.substring(0,3);
            if (item.equals("102")) {
                return "音乐学院";
            } else if (item.equals("105")) {
                return "上海温哥华电影学院";
            } else if (item.equals("564")) {
                return "理学院";
            } else if (item.equals("565")) {
                return "文学院";
            } else if (item.equals("566")) {
                return "外国语学院";
            } else if (item.equals("567")) {
                return "法学院";
            } else if (item.equals("568")) {
                return "通信与信息工程学院";
            } else if (item.equals("569")) {
                return "计算机工程与科学学院办公室";
            } else if (item.equals("570")) {
                return "机电工程与自动化学院办公室";
            } else if (item.equals("572")) {
                return "环境与化学工程学院";
            } else if (item.equals("573")) {
                return "生命科学学院";
            } else if (item.equals("574")) {
                return "美术学院";
            } else if (item.equals("575")) {
                return "影视学院";
            } else if (item.equals("576")) {
                return "悉尼工商学院";
            } else if (item.equals("577")) {
                return "社会科学学院";
            } else if (item.equals("578")) {
                return "土木工程系";
            } else if (item.equals("579")) {
                return "体育学院";
            } else if (item.equals("580")) {
                return "继续教育和巴黎时装学院";
            } else if (item.equals("581")) {
                return "国际交流学院";
            } else if (item.equals("582")) {
                return "高等技术学院";
            } else if (item.equals( "583")) {
                return "巴士汽车学院";
            } else if (item.equals("584")) {
                return "房地产学院";
            } else if (item.equals("585")) {
                return "中欧工程技术学院";
            } else if (item.equals("586")) {
                return "行政";
            } else if (item.equals("587")) {
                return "钱伟长学院";
            } else if (item.equals("588")) {
                return "管理学院";
            } else if (item.equals("589")) {
                return "社区学院";
            } else if (item.equals( "590")) {
                return "图书情报档案系";
            } else if (item.equals("591")) {
                return "经济学院";
            } else if (item.equals("592")) {
                return "社会发展研究院办公室等";
            } else if (item.equals("593")) {
                return "科技发展研究院等";
            } else if (item.equals("594")) {
                return "社会学系等";
            } else if (item.equals("595")) {
                return "纳米科学与技术研究中心等";
            } else if (item.equals("596")) {
                return "研究生院等";
            } else {
                return "不确定";
            }
        }
    }


    public static String levelTOname(String item) {
        if (item .equals("") || item.length() < 6||item.equals("0")) {
            return "没有";
        } else {

            if (item.equals( "842000")) {
                return "无";
            } else if (item.equals( "842001")) {
                return "实验教学示范中心(国家级)";
            } else if (item.equals("842002")) {
                return "实验教学示范中心(上海市级)";
            } else if (item.equals("842003")) {
                return "省市级重点实验室";
            } else {
                return "不确定";
            }
        }
    }

    public static String typeTOname(String item) {
        if (item.equals("") || item.length() < 6 ||item.equals("0")||item.equals("0")) {
            return "没有";
        } else {

            if (item.equals("843000")) {
                return "未知";
            } else if (item.equals( "843001")) {
                return "基础实验室";
            } else if (item.equals("843002")) {
                return "专业实验室";
            } else if (item.equals("843003")) {
                return "多媒体教学实验室";
            } else {
                return "不确定";
            }
        }
    }
}


