package beiyuan

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
object Spark {
  def main(args: Array[String]): Unit = {
    //https://www.kaggle.com/datasets/elakiricoder/gender-classification-dataset
    val spark=SparkSession.builder()
                          .master("local")
                          .appName("Spark SQL basic example ")
                          .getOrCreate()
    import spark.implicits._



    var baseData=spark.read.format("csv").load("gender.csv")

    //1,11.8,6.1,1,0,1,1,Male  先放一条数据，有个类型
    var d1=(0,Vectors.dense(1,11.8,6.1,1,0,1,1),0)
    val data=ListBuffer(d1)
    var x=0
    baseData.collect().foreach(row=>{
      if(x!=0){
        var arr=ArrayBuffer[Double]()
        for(i<- Range(0,row.length-1)){
          arr+=java.lang.Double.parseDouble(row.get(i)+"")
        }
        var feature=Vectors.dense(arr(0),arr(1),arr(2),arr(3),arr(4),arr(5),arr(6))
        //男1 女0
        var tuple=(x,feature,Integer.parseInt(row.get(row.length-1)+""))
        data+=tuple  //这里要2.11版本才行。
        x=1
      }
    })

    var df=spark.createDataset(data).toDF("id","features","label")
    val Array(trainSet,testSet) =df.randomSplit(Array(0.7,0.3),seed = 1243L)


    val model=new NaiveBayes().fit(trainSet)
    val predictions=model.transform(testSet)

    predictions.show(100,109)
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
