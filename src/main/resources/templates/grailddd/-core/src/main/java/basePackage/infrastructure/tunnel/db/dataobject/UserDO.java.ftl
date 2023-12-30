package ${project.basePackage}.infrastructure.tunnel.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

/**
 * @author Grail codegen
 * for demo
 **/
@Data
@TableName("t_user")
public class UserDO {

    /**
     * 主键Id
     */
    @TableId(value = "id", type = AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 角色
     */
    @TableField(value = "role")
    private String role;

}