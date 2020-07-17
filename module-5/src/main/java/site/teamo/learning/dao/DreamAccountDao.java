package site.teamo.learning.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import site.teamo.learning.bean.DreamAccount;

@Component
public interface DreamAccountDao {

    @Select("select * from dream_account where username=#{username}")
    DreamAccount selectByUsername(@Param("username") String username);

    @Update("update dream_account set amount=#{amount} where username=#{username}")
    Integer updateAmountByUserName(DreamAccount dreamAccount);
}
