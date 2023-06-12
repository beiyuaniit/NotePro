import java.util.Calendar;

public class Calendar类 {
    /*
    是一个抽象类，继承时要实现所有抽象方法
     */

}


class  Date extends Calendar{

     @Override
     protected void computeTime() {

     }

     @Override
     protected void computeFields() {

     }

     @Override
     public void add(int field, int amount) {

     }

     @Override
     public void roll(int field, boolean up) {

     }

     @Override
     public int getMinimum(int field) {
         return 0;
     }

     @Override
     public int getMaximum(int field) {
         return 0;
     }

     @Override
     public int getGreatestMinimum(int field) {
         return 0;
     }

     @Override
     public int getLeastMaximum(int field) {
         return 0;
     }
 }
