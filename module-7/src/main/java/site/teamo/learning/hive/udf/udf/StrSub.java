package site.teamo.learning.hive.udf.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * 截取指定长度字符串的UDF
 * @author 爱做梦的锤子
 * @create 2020/7/28
 */
public class StrSub extends GenericUDF {
    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        /**
         * 检验参数的个数不等于3个抛出异常，你也可以加入其它的校验，此处作为演示写的比较简略
         */
        if(objectInspectors.length!=3){
            throw new UDFArgumentException("Invalid num of arguments for StrSub");
        }
        /**
         * 返回该函数的返回结果的数据类型
         */
        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
        //取出三个参数，并转换成响应类型
        String sourceString = String.valueOf(deferredObjects[0].get());
        Integer start = Integer.valueOf(String.valueOf(deferredObjects[1].get()));
        Integer end = Integer.valueOf(String.valueOf(deferredObjects[2].get()));
        //对字符串截取，返回截取后的结果
        String targetString = sourceString.substring(start,end);
        return targetString;
    }

    @Override
    public String getDisplayString(String[] strings) {
        return "Function StrSub";
    }
}
