package site.teamo.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.teamo.learning.bean.DreamAccountLog;
import site.teamo.learning.dao.DreamAccountLogDao;

@Service
public class DreamAccountLogService {

    @Autowired
    private DreamAccountLogDao dreamAccountLogDao;

    @Transactional(propagation = Propagation.MANDATORY,isolation = Isolation.DEFAULT)
    public void addLog(String content) throws Exception {
        if(content.length()>16){
            throw new Exception("日志内容太长");
        }
        DreamAccountLog dreamAccountLog = DreamAccountLog.builder()
                .content(content)
                .build();

        dreamAccountLogDao.insertLog(dreamAccountLog);
    }
}
