package beiyuan

import org.apache.spark.ml.linalg.Vectors

import scala.collection.mutable.ListBuffer

/**
 * @author: beiyuan
 * @date: 2023/6/2  19:32
 */
object Test {
  def main(args: Array[String]): Unit = {

    var data=ListBuffer((0,Vectors.dense(0,2,9),2))

    var e=(1,Vectors.dense(0,2,9),2)
    data+=e

    data+=e

    data+=e

    for(i<-Range(0,1000)){
      data+=e
    }
    println(data.length)
  }
}
