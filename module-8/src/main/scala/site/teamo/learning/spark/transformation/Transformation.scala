package site.teamo.learning.spark.transformation

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

/**
 * @author 爱做梦的锤子
 * @create 2020/8/20
 */
class Transformation {

  private val sparkConf =
    new SparkConf()
      .setAppName("转换算子")
      .setMaster("local[6]")
  private val sc: SparkContext = new SparkContext(sparkConf);

  /**
   * map算子演示
   */
  @Test
  def mapTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(
      "Hadoop HBase FLink",
      "Hello world",
      "你好 Spark"
    ))
    sourceRdd
      .map(item => item.split(" "))
      .collect()
      .foreach(item => println(item.length))

    sc.stop()
  }

  @Test
  def flatMapTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(
      "Hadoop HBase FLink",
      "Hello world",
      "你好 Spark"
    ))
    sourceRdd
      .flatMap(item => item.split(" "))
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  /**
   * mapPartitions对一整个分区数据进行转换
   * 与map区别
   * 1.map的func参数时单条数据，mapPartitions的func的参数是一个分区的数据，及一个集合
   * 2.map的func返回值是单条数据，mapPartitions的func的返回值是一个集合
   */
  @Test
  def mapPartitionsTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3, 4), 3)
    sourceRdd
      .mapPartitions(partition => {
        partition.map(item => item * 10)
      })
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  /**
   * mapPartitionsWithIndex和mapPartitions的区别就是再mapPartitionsWithIndex中func的入参多个分区编号
   */
  @Test
  def mapPartitionsWithIndexTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3, 4), 3)
    sourceRdd
      .mapPartitionsWithIndex((index, partition) => {
        partition.map(item => index * item)
      })
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  /**
   * mapValues与map相似，不同的时map作用于整条数据，mapValues作用于每条Key-Value类型数据的value
   */
  @Test
  def mapValuesTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(("a", 1), ("b", 2), ("c", 3), ("d", 4)), 3)
    sourceRdd
      .mapValues(itemValue => itemValue * 10)
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  @Test
  def joinTest(): Unit = {
    val rdd1 = sc.parallelize(Seq(("a", 1), ("a", 2), ("b", 3), ("b", 4)))
    val rdd2 = sc.parallelize(Seq(("a", 5), ("a", 6)))
    rdd1
      .join(rdd2)
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  /**
   * filter过滤掉数据集中不符合要求的元素
   * filter的func的入参是RDD的一个元素，返回值是boolean类型值，true代表保留该数据，false代表去除该数据
   */
  @Test
  def filterTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3, 4, 5), 3)
    sourceRdd
      .filter(item => item > 2)
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  /**
   * sample抽象算子，在尽可能减少数据集规律损失的情况下，把大数据集变小
   * sample的func的第一个入参withReplacement代表抽样出来的数据是否再放回，true代表放回（抽样结果可能有重复），false代表放回
   */
  @Test
  def sampleTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3, 4, 5), 3)
    //无放回
    System.out.println("抽样无放回演示")
    sourceRdd
      .sample(false, 0.8, 100)
      .collect()
      .foreach(item => println(item))
    //有放回
    System.out.println("抽样有放回演示")
    sourceRdd
      .sample(true, 0.8, 100)
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  /**
   * 交集
   *
   */
  @Test
  def intersectionTest(): Unit = {
    val rdd1 = sc.parallelize(Seq(1, 2, 3, 4, 5))
    val rdd2 = sc.parallelize(Seq(4, 5, 6, 7, 8))
    rdd1
      .intersection(rdd2)
      .collect()
      .foreach(item => println(item))

    val rdd3 = sc.parallelize(Seq((1, "a"), (2, "b"), (3, "c")))
    val rdd4 = sc.parallelize(Seq((2, "b"), (3, "d"), (4, "d")))

    rdd3
      .intersection(rdd4)
      .collect()
      .foreach(println(_))

    sc.stop()
  }

  @Test
  def unionTest(): Unit = {
    val rdd1 = sc.parallelize(Seq(1, 2, 3))
    val rdd2 = sc.parallelize(Seq(2, 3, 4))
    rdd1
      .union(rdd2)
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  @Test
  def subtractTest(): Unit = {
    val rdd1 = sc.parallelize(Seq(1, 2, 3, 4, 5))
    val rdd2 = sc.parallelize(Seq(4, 5, 6, 7, 8))
    System.out.println("rdd1.subtract(rdd2)")
    rdd1
      .subtract(rdd2)
      .collect()
      .foreach(item => println(item))

    System.out.println("rdd2.subtract(rdd1)")
    rdd2
      .subtract(rdd1)
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  @Test
  def sortByKeyTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq((2, "a"), (1, "b"), (3, "c")))

    sourceRdd
      .sortByKey(false)
      .collect()
      .foreach(println(_))

    sc.stop()
  }

  @Test
  def sortByTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq((2, "a"), (1, "b"), (3, "c")))

    sourceRdd
      .sortBy(item => item._2, false)
      .collect()
      .foreach(println(_))

    sc.stop()
  }

  @Test
  def reduceByKeyTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(
      ("一年级", "张三"),
      ("二年级", "李四"),
      ("一年级", "王五"),
      ("一年级", "赵六")
    ))
    sourceRdd
      .reduceByKey((agg, curr) => curr + "-" + agg)
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  /**
   * groupByKey在Map端不进行combiner
   */
  @Test
  def groupByKeyTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(
      ("一年级", "张三"),
      ("二年级", "李四"),
      ("一年级", "王五"),
      ("一年级", "赵六")
    ))

    sourceRdd
      .groupByKey()
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  @Test
  def combineByKeyTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(
      ("一年级", "张三"),
      ("二年级", "李四"),
      ("一年级", "王五"),
      ("一年级", "赵六")
    ))
    sourceRdd
      .combineByKey(
        createCombiner = curr => (curr, 1),
        mergeValue = (agg: (String, Int), nextValue) => (agg._1 + nextValue, agg._2 + 1),
        mergeCombiners = (agg: (String, Int), curr: (String, Int)) => (curr._1 + "|" + agg._1, curr._2 + agg._2)
      )
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  @Test
  def foldByKeyTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(
      ("一年级", "张三"),
      ("二年级", "李四"),
      ("一年级", "王五"),
      ("一年级", "赵六")
    ))
    sourceRdd
      .foldByKey("|")((agg, curr) => curr + agg)
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  @Test
  def aggregateByKeyTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(
      ("一年级", "张三"),
      ("二年级", "李四"),
      ("一年级", "王五"),
      ("一年级", "赵六")
    ))
    sourceRdd
      .aggregateByKey("|")((zeroValue, item) => zeroValue + item + zeroValue, (agg, curr) => curr + agg)
      .collect()
      .foreach(item => println(item))

    sc.stop()
  }

  @Test
  def repartitionByKeyTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7), 3)
    System.out.println("调大分区数:" + sourceRdd.repartition(5).getNumPartitions)
    System.out.println("调小分区数" + sourceRdd.repartition(2).getNumPartitions)
    sc.stop()
  }

  @Test
  def coalesceByKeyTest(): Unit = {
    val sourceRdd = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7), 3)
    System.out.println("调大分区数:" + sourceRdd.coalesce(5).getNumPartitions)
    System.out.println("调大分区数:" + sourceRdd.coalesce(5, true).getNumPartitions)
    System.out.println("调小分区数" + sourceRdd.coalesce(2).getNumPartitions)
    sc.stop()
  }

}
