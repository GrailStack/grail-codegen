package ${project.basePackage}.command.cmo;

import lombok.Data;

/**
 * @author xujin
 *
 */
@Data
public class AddProductCmd {

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品描述
     */
    private String desc;

    /**
     * 产品负责人
     */
    private Long owner;

    /**
     * 产品标识
     */
    private String identifier;


}
