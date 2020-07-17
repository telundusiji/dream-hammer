package site.teamo.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.teamo.learning.bean.DreamAccount;
import site.teamo.learning.dao.DreamAccountDao;

@Service
public class DreamAccountService {

    @Autowired
    private DreamAccountDao dreamAccountDao;

    private DreamAccountLogService dreamAccountLogService;

    public void transferAccounts(Long amount,String sourceUsername,String targetUsername) throws Exception {
        //查找转账的源账户
        DreamAccount sourceAccount = dreamAccountDao.selectByUsername(sourceUsername);
        //查找目标账户
        DreamAccount targetAccount = dreamAccountDao.selectByUsername(targetUsername);
        //检验源账户余额是否充足
        if(sourceAccount.getAmount()<amount){
            throw new Exception("账户余额不足");
        }
        //源账户金额减少
        sourceAccount.setAmount(sourceAccount.getAmount()-amount);
        //目标账户增加金额
        targetAccount.setAmount(targetAccount.getAmount()+amount);
        //更新源账户金额
        dreamAccountDao.updateAmountByUserName(sourceAccount);
        //更新目标账户金额
        dreamAccountDao.updateAmountByUserName(targetAccount);
        //记录转账日志
        dreamAccountLogService.addLog("账户:"+sourceUsername+"向账户:"+targetUsername+"转账金额:"+amount+"元");
    }

}
