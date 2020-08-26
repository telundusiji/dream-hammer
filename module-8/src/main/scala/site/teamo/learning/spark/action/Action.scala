package site.teamo.learning.spark.action

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

/**
 * @author 爱做梦的锤子
 * @create 2020/8/26
 */
class Action {

  private val sparkConf =
    new SparkConf()
      .setAppName("动作算子")
      .setMaster("local[6]")
  private val sc: SparkContext = new SparkContext(sparkConf);

  @Test
  def reduceTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 1, 1, 1, 1))

    sourceRdd.collect()

    val result = sourceRdd
      .reduce((agg, curr) => agg * 2 + curr)
    println(result)
    sc.stop()
  }


  @Test
  def collectTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3))
    sourceRdd.collect()
    val result: Array[Int] = sourceRdd.collect()
    result.foreach(println(_))
    sc.stop()
  }

  @Test
  def foreachTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3))
    sourceRdd.collect()
    sourceRdd.foreach(println(_))
    sc.stop()
  }

  @Test
  def countTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3))
    val result = sourceRdd.count()
    println(result)
    sc.stop()
  }

  @Test
  def countByKeyTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(("a", "value"), ("b", "value"), ("b", "value"), ("c", "value"), ("a", "value")))
    val result: collection.Map[String, Long] = sourceRdd.countByKey()
    result.foreach(println(_))
    sc.stop()
  }

  @Test
  def firstTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3))
    val result = sourceRdd.first()
    println(result)
    sc.stop()
  }

  @Test
  def takeTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 3, 2, 1, 5))
    val result = sourceRdd.take(3)
    result.foreach(println(_))
    sc.stop()
  }

  @Test
  def takeSampleTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 3, 2, 1, 5))
    val result = sourceRdd.takeSample(false, 3, 100)
    result.foreach(println(_))
    sc.stop()
  }

}
