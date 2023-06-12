import java.util.*;


public class Map遍历 {
    public static <HashSetSet> void main(String[] args) {
        /*
        2种方法：
            把Map的key转化为Set
            把Map的key和value转化为Set
         */
        HashMap<Integer,String> mes=new HashMap<Integer,String>();
        mes.put(01,"Ricardo");
        mes.put(02,"Riou");

        //01通过可以变成set(不能是HashSet
        Set<Integer> keys=mes.keySet();
        Iterator<Integer> it= keys.iterator();

        //while和foreach都行
        while(it.hasNext()){
            String str=mes.get(it.next());
        }
        for(Integer key:keys){
            String str1=mes.get(it.next());
        }


        //02用entry方法
        Set<Map .Entry<Integer,String >> set=mes.entrySet();

        for(Map.Entry<Integer,String> elem:set){
            Integer a=elem.getKey();
            String str=elem.getValue();
        }
    }
}
