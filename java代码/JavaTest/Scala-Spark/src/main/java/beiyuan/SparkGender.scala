package beiyuan

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.ChiSqSelector
import org.apache.spark.ml.linalg.Vectors

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * @author: beiyuan
 * @date: 2023/5/31  17:11
 */
object SparkGender {
  def main(args: Array[String]): Unit = {
    //https://www.kaggle.com/datasets/elakiricoder/gender-classification-dataset

    val spark=SparkSession.builder()
      .master("local")
      .appName("SparkGender")
      .getOrCreate()

    val data=spark.read.format("libsvm").load("gender.txt")


    val Array(trainSet,testSet) =data.randomSplit(Array(0.7,0.3))


    val model=new NaiveBayes().fit(trainSet)
    val predictions=model.transform(testSet)

   //predictions.show(3000)
//    predictions.foreach(row=>{
//      println(row)
//    })


    predictions.show()
    val evaluator=new MulticlassClassificationEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
    val accuracy=evaluator.evaluate(predictions)
    println(s"The set accuracy: ${accuracy}")



    //    var df=spark.read.csv("mushrooms.csv")
    //    df.createOrReplaceTempView("mushrooms")
    //    var sqlDf=spark.sql("select * from mushrooms limit 10")
    //    sqlDf.show()

  }

}
