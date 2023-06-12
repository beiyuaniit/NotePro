package beiyuan

import org.apache.spark.{SparkConf, SparkContext};
/**
 * @author: beiyuan
 * @date: 2023/5/27  23:03
 */
object WordCount {
  def main(args: Array[String]): Unit ={
    var conf=new SparkConf().setMaster("local").setAppName("wordcount")
    val sc=new SparkContext(conf)
    val lines=sc.textFile("wordcount.txt")
    val words=lines.flatMap(line=>line.split(" "))
    val count=words.map(word=>(word,1)).reduceByKey{case(x,y)=>x+y}
    println(count.collect().mkString("\n"))
  }

}
