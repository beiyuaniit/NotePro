package sql_server;

import java.sql.ResultSet;

/**
 * @author: beiyuan
 * @className: results
 * @date: 2021/10/23  11:26
 */
public class results {
    public static void deal(ResultSet result){
        try{
            while (result.next()){
                for(int i =1;i<=5;i++)
                    System.out.print(result.getString(i)+" ");
                System.out.println();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
