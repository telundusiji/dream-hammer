package site.teamo.learning.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import site.teamo.learning.bean.DreamAccount;
import site.teamo.learning.bean.DreamAccountLog;

@Component
public interface DreamAccountLogDao {

    @Insert("insert into dream_account_log(id,content) values(#{id},#{content})")
    Integer insertLog(DreamAccountLog dreamAccountLog);
}
