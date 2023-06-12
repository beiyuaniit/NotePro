package beiyuan

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator


/**
 * @author: beiyuan
 * @date: 2023/5/31  17:11
 */
object SparkRice {
  def main(args: Array[String]): Unit = {
    //https://www.kaggle.com/datasets/elakiricoder/gender-classification-dataset
    val spark=SparkSession.builder()
      .master("local")
      .appName("Spark01")
      .getOrCreate()

    val data=spark.read.format("libsvm").load("rice.txt")


    val Array(trainSet,testSet) =data.randomSplit(Array(0.8,0.2),seed = 1243L)


    val model=new NaiveBayes().fit(trainSet)
    val predictions=model.transform(testSet)

    predictions.show()

    predictions.foreach(row=>{
      println(row)
    })

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
