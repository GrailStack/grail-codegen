package ${project.basePackage}.command.co;

import lombok.Data;

@Data
public class ProductCO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 产品唯一主键
     */
    private String uid;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品OwnerName
     */
    private String ownerName;

    private Long ownerId;

    /**
     * ownerAvatar
     */
    private String ownerAvatar;

    /**
     * 产品描述
     */
    private String desc;

    /**
     * 产品标识
     */
    private String identifier;
}
