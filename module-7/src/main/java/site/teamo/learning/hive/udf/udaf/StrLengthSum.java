package site.teamo.learning.hive.udf.udaf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.plan.ptf.WindowFrameDef;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.util.JavaDataModel;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.apache.hadoop.io.IntWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计算字符串长度和
 * @author 爱做梦的锤子
 * @create 2020/7/28
 */
public class StrLengthSum extends AbstractGenericUDAFResolver {
    static final Logger LOG = LoggerFactory.getLogger(StrLengthSum.class.getName());

    @Override
    public GenericUDAFEvaluator getEvaluator(TypeInfo[] info) throws SemanticException {
        //检验参数个数
        if (info.length != 1) {
            throw new UDFArgumentTypeException(info.length - 1, "Exactly one argument is expected.");
        }
        //校验参数类型，仅支持基础数据类型
        ObjectInspector oi = TypeInfoUtils.getStandardJavaObjectInspectorFromTypeInfo(info[0]);
        if (oi.getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentTypeException(0, "Only primitive type arguments are accepted but " + info[0].getTypeName() + " was passed as parameter 1.");
        }
        return new StrLengthSumEvaluator();
    }

    public static class StrLengthSumEvaluator extends GenericUDAFEvaluator {

        protected PrimitiveObjectInspector inputOI;
        protected PrimitiveObjectInspector outputOI;

        /**
         * 初始化，参数校验，定义输出类型
         */
        @Override
        public ObjectInspector init(Mode m, ObjectInspector[] parameters)
                throws HiveException {
            //检验参数个数
            assert (parameters.length == 1);
            super.init(m, parameters);
            //将输入参数赋值给inputOI
            inputOI = (PrimitiveObjectInspector) parameters[0];
            //设置输出结果数据类型
            outputOI = PrimitiveObjectInspectorFactory.writableIntObjectInspector;
            return outputOI;
        }

        /**
         * 缓冲区用来保存中间结果
         */
        @AggregationType(estimable = true)
        static class SumAgg extends AbstractAggregationBuffer {
            /**
             * 累加和
             */
            IntWritable sum;

            public void add(Integer integer) {
                sum.set(sum.get() + integer);
            }

            /**
             * 缓存区预分配内存大小
             * @return
             */
            @Override
            public int estimate() {
                return JavaDataModel.PRIMITIVES1;
            }
        }

        /**
         * 获取存放中间结果的缓冲对象
         */
        @Override
        public AggregationBuffer getNewAggregationBuffer() throws HiveException {
            SumAgg result = new SumAgg();
            reset(result);
            return result;
        }

        /**
         * 重置存放中间结果的缓冲类
         */
        @Override
        public void reset(AggregationBuffer agg) throws HiveException {
            SumAgg myagg = (SumAgg) agg;
            myagg.sum = new IntWritable(0);
        }

        /**
         * 处理一行数据
         */
        @Override
        public void iterate(AggregationBuffer agg, Object[] parameters)
                throws HiveException {
            //判断参数个数
            assert (parameters.length == 1);
            if (parameters[0] != null) {
                //取出参数和中间结果存储类，将参数转换成java原始类型，计算长度然后累加
                SumAgg myagg = (SumAgg) agg;
                Object primitiveJavaObject = inputOI.getPrimitiveJavaObject(parameters[0]);
                myagg.add(String.valueOf(primitiveJavaObject).length());
            }

        }

        /**
         * 返回部分聚合数据的持久化对象。
         * 因为调用这个方法时，说明已经是map或者combine的结束了，必须将数据持久化以后交给reduce进行处理。
         * 只支持JAVA原始数据类型及其封装类型、HADOOP Writable类型、List、Map，不支持自定义的类
         */
        @Override
        public Object terminatePartial(AggregationBuffer agg) throws HiveException {
            return terminate(agg);
        }

        /**
         * 将terminatePartial返回的部分聚合数据进行合并
         */
        @Override
        public void merge(AggregationBuffer agg, Object partial)
                throws HiveException {
            if (partial != null) {
                SumAgg myagg = (SumAgg) agg;
                Integer partialSum = PrimitiveObjectInspectorUtils.getInt(partial, outputOI);
                myagg.add(partialSum);
            }
        }

        /**
         * 生成最终结果
         */
        @Override
        public Object terminate(AggregationBuffer agg) throws HiveException {
            SumAgg myagg = (SumAgg) agg;
            return myagg.sum;
        }

        @Override
        public GenericUDAFEvaluator getWindowingEvaluator(WindowFrameDef wFrmDef) {
            return null;
        }
    }
}
