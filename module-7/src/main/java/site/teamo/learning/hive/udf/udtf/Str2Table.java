package site.teamo.learning.hive.udf.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 将字符串按照 ; 和 , 分割成多行
 * @author 爱做梦的锤子
 * @create 2020/7/29
 */
public class Str2Table extends GenericUDTF {
    static final Logger LOG = LoggerFactory.getLogger(Str2Table.class.getName());
    private static final String ROW_SEPARATOR = ";";
    private static final String ATTR_SEPARATOR= ",";
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        //校验参数个数
        List<? extends StructField> inputFields = argOIs.getAllStructFieldRefs();
        if(inputFields.size()!=1){
            throw new UDFArgumentException("Invalid num of arguments for Str2Table");
        }
        //构造输出结果的数据结构，字段名和字段类型
        ArrayList<String> fieldNames = new ArrayList<>();
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<>();
        fieldNames.add("col1");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldNames.add("col2");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,fieldOIs);
    }

    /**
     * 处理输入数据的方法
     * @param args
     * @throws HiveException
     */
    @Override
    public void process(Object[] args) throws HiveException {
        assert (args.length == 1);
        //获取输入数据
        String input = String.valueOf(args[0]);
        //按 ； 进行分割成行
        String[] output= input.split(ROW_SEPARATOR);
        //遍历每一行
        for(int i=0; i<output.length; i++) {
            try {
                //行再按 , 分割获得属性
                String[] result = output[i].split(ATTR_SEPARATOR);
                //调用forward生成一行数据
                forward(result);
            } catch (Exception e) {
                LOG.warn("row format error:{}",output[i]);
            }
        }
    }

    @Override
    public void close() throws HiveException {

    }
}
