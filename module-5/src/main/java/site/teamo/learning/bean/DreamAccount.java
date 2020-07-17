package site.teamo.learning.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DreamAccount {
    private String id;
    private String account;
    private Long amount;
    private Date createTime;
    private Date updateTime;
}
