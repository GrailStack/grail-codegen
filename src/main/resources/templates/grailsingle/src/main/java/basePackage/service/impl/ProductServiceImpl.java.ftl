package ${project.basePackage}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${project.basePackage}.service.ProductService ;
import ${project.basePackage}.command.cmo.AddProductCmd ;
import ${project.basePackage}.command.cmo.PageListProductCmd;
import ${project.basePackage}.command.cmo.UpdateProductCmd;
import ${project.basePackage}.command.co.ProductCO;
import ${project.basePackage}.dataobject.ProductDO;
import ${project.basePackage}.dao.ProductMapper;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductCO> findByLikeName(String name) {
        List<ProductCO> productCOList=null;
        LambdaUpdateWrapper<ProductDO> queryWrapper = new LambdaUpdateWrapper<ProductDO>();
        if(StringUtils.hasText(name)){
            queryWrapper.eq(ProductDO::getName, name);
            List<ProductDO> productDOS= productMapper.selectList(queryWrapper);
            productCOList=BeanMapper.mapList(productDOS,ProductDO.class,ProductCO.class);
        }else {
            List<ProductDO> productDOS= productMapper.selectList(queryWrapper);
            productCOList=BeanMapper.mapList(productDOS,ProductDO.class,ProductCO.class);
        }
        return productCOList;
    }

    @Override
    public void add(AddProductCmd addProductCmd) {
        ProductDO productDO=BeanMapper.map(addProductCmd,ProductDO.class);
        productMapper.insert(productDO);
    }

    @Override
    public void update(UpdateProductCmd updateProductCmd) {
        ProductDO productDO= productMapper.selectById(updateProductCmd.getUid());
        if(null==productDO){
//            throw new NoahException(ApiResponseCode.DATA_NOT_EXIST);
        }
        LambdaUpdateWrapper<ProductDO> updateWrapper = new LambdaUpdateWrapper<ProductDO>();
        updateWrapper.eq(ProductDO::getUid, updateProductCmd.getUid());
        if(StringUtils.hasText(updateProductCmd.getName())){
            updateWrapper.set(ProductDO::getName,updateProductCmd.getName()) ;
        }
        if(StringUtils.hasText(updateProductCmd.getDesc())){
            updateWrapper.set(ProductDO::getDesc,updateProductCmd.getDesc()) ;
        }
        productMapper.update(productDO,updateWrapper);

    }

    @Override
    public PageResult<ProductCO> page(PageListProductCmd cmd) {
        Page pageRequest = new Page(cmd.getCurrentPage(), cmd.getPageSize());
        LambdaQueryWrapper<ProductDO> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasText(cmd.getName())){
            queryWrapper.like(ProductDO::getName, cmd.getName());
        }
        IPage<ProductDO> page = productMapper.selectPage(pageRequest, queryWrapper);
        List<ProductCO> productCOList = new ArrayList<>();
        for (ProductDO productDO : page.getRecords()) {
            try {
                ProductCO productCO = BeanMapper.map(productDO, ProductCO.class);
                productCO.setUid(productDO.getUid());
                productCOList.add(productCO);
            } catch (Exception e) {
                log.info("Product page found exception:{}", e);
            }
        }
        PageResult<ProductCO> pageResult = new PageResult<ProductCO>();
        pageResult.setCurrentPage(page.getCurrent());
        pageResult.setTotalCount(page.getTotal());
        pageResult.setList(productCOList);
        pageResult.setTotalPage(page.getSize());
        return pageResult;
    }

    @Override
    public void delete(String id) {
        productMapper.deleteById(id);
    }
}
