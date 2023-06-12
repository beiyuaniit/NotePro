package beiyuan

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: beiyuan
 * @date: 2023/5/28  23:25
 */
object RDD {
  def main(args: Array[String]): Unit = {
    var conf=new SparkConf().setMaster("local").setAppName("RDD")
    val sc=new SparkContext(conf)
    var list=List(3,1,2,4)
    var rdd1=sc.parallelize(list)
    rdd1.map(_+2).collect().foreach(println)
  }

}
