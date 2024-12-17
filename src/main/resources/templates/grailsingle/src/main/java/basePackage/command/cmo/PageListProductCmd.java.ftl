package ${project.basePackage}.command.cmo;

import lombok.Data;

/**
 * @author xujin
 *
 */
@Data
public class PageListProductCmd {

    private String name;

    private Integer currentPage = 1;

    private Integer pageSize = 10;

    private Long ownerId;

    /**
     * 产品标识
     */
    private String identifier;

}
