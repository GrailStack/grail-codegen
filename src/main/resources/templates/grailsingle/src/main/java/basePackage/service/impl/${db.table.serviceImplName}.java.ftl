package ${project.basePackage}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${project.basePackage}.service.${db.table.serviceName} ;
import ${project.basePackage}.command.cmo.AddProductCmd ;
import ${project.basePackage}.command.cmo.PageListProductCmd;
import ${project.basePackage}.command.cmo.UpdateProductCmd;
import ${project.basePackage}.command.co.${db.table.coName};
import ${project.basePackage}.dataobject.${db.table.doName};
import ${project.basePackage}.dao.${db.table.daoName};
import com.itgrail.grail.dto.PageResult;
import com.itgrail.grail.utils.reflect.BeanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ${db.table.serviceImplName} implements ${db.table.serviceName} {

    @Autowired
    private ${db.table.daoName} mapper;

    @Override
    public List<${db.table.coName}> findByLikeName(String name) {
        List<${db.table.coName}> ${db.table.coName}List=null;
        LambdaUpdateWrapper<${db.table.doName}> queryWrapper = new LambdaUpdateWrapper<${db.table.doName}>();
        List<${db.table.doName}> productDOS= mapper.selectList(queryWrapper);
        ${db.table.coName}List=BeanMapper.mapList(productDOS,${db.table.doName}.class,${db.table.coName}.class);
        return ${db.table.coName}List;
    }

    @Override
    public void add(AddProductCmd addProductCmd) {
        ${db.table.doName} ${db.table.doName}=BeanMapper.map(addProductCmd,${db.table.doName}.class);
        mapper.insert(${db.table.doName});
    }

    @Override
    public void update(UpdateProductCmd updateProductCmd) {
        ${db.table.doName} ${db.table.doName}= mapper.selectById(updateProductCmd.getUid());
        if(null==${db.table.doName}){
//            throw new NoahException(ApiResponseCode.DATA_NOT_EXIST);
        }
        LambdaUpdateWrapper<${db.table.doName}> updateWrapper = new LambdaUpdateWrapper<${db.table.doName}>();
        mapper.update(${db.table.doName},updateWrapper);

    }

    @Override
    public PageResult<${db.table.coName}> page(PageListProductCmd cmd) {
        Page pageRequest = new Page(cmd.getCurrentPage(), cmd.getPageSize());
        LambdaQueryWrapper<${db.table.doName}> queryWrapper = new LambdaQueryWrapper<>();
        IPage<${db.table.doName}> page = mapper.selectPage(pageRequest, queryWrapper);
        List<${db.table.coName}> ${db.table.coName}List = new ArrayList<>();
        for (${db.table.doName} ${db.table.doName} : page.getRecords()) {
            try {
                ${db.table.coName} ${db.table.coName} = BeanMapper.map(${db.table.doName}, ${db.table.coName}.class);
                ${db.table.coName}List.add(${db.table.coName});
            } catch (Exception e) {
                log.info("Product page found exception:{}", e);
            }
        }
        PageResult<${db.table.coName}> pageResult = new PageResult<${db.table.coName}>();
        pageResult.setCurrentPage(page.getCurrent());
        pageResult.setTotalCount(page.getTotal());
        pageResult.setList(${db.table.coName}List);
        pageResult.setTotalPage(page.getSize());
        return pageResult;
    }

    @Override
    public void delete(String id) {
        mapper.deleteById(id);
    }
}
