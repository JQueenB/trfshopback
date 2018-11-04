package com.jqueenb.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jqueenb.common.Const;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.dao.CategoryMapper;
import com.jqueenb.dao.ProductMapper;
import com.jqueenb.pojo.Category;
import com.jqueenb.pojo.Product;
import com.jqueenb.service.ICategoryService;
import com.jqueenb.service.IProductServer;
import com.jqueenb.utils.DateUtils;
import com.jqueenb.utils.PropertiesUtils;
import com.jqueenb.vo.ProductDetailVo;
import com.jqueenb.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductServer {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ICategoryService categoryService;
    @Override
    public ServerResponse saveOrUpdate(Product product) {
        //step1:参数非空校验
        if(product==null){
            return ServerResponse.createByError("参数为空");
        }
        //step2:设置商品的主图
        String subImages=product.getSubImages();
        if(subImages!=null&&!subImages.equals("")){
            String[] subImageArr=subImages.split(",");
            if(subImageArr.length>0){
                product.setMainImage(subImageArr[0]);
            }
        }
        //step3:商品添加或更新
        if(product.getId()==null){
           //添加
            int result=productMapper.insert(product);
            if(result>0){
                return ServerResponse.createBySuccess("添加成功");
            }else {
                return ServerResponse.createByError("添加失败");
            }
        }else {
            //更新
            int result=productMapper.updateByPrimaryKey(product);
            if(result>0){
                return ServerResponse.createBySuccess();
            }else {
                return ServerResponse.createByError("更新失败");
            }
        }
    }

    @Override
    public ServerResponse set_sale_status(Integer productId, Integer status) {
        //step1:参数非空校验
        if(productId==null){
            return ServerResponse.createByError("商品不能为空");
        }
        if(status==null){
            return ServerResponse.createByError("商品状态参数不能为空");
        }
        //step2:更新商品状态
        Product product=new Product();
        product.setId(productId);
        product.setStatus(status);
        int result=productMapper.updateProductKeySelective(product);
        //step3:返回结果
        if(result>0){
            return ServerResponse.createBySuccess();
        }else {
            return ServerResponse.createByError("更新失败");
        }
    }

    @Override
    public ServerResponse detail(Integer productId) {
        //step1:参数非空校验
        if(productId==null){
            return ServerResponse.createByError("商品id参数不能为空");
        }
        //step2：查询product
        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByError("商品不存在");
        }
        //step3：product-->productDetailVO
        ProductDetailVo productDetailVo=assembleProductDetailVO(product);
        //step4：返回结果
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVO(Product product){
        ProductDetailVo productDetailVo=new ProductDetailVo();
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setImageHost(PropertiesUtils.readByKey("imageHost"));
        productDetailVo.setName(product.getName());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setId(product.getId());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category!=null){
            productDetailVo.setParentCategoryId(category.getParentId());
        }else {
            //默认根节点
            productDetailVo.setParentCategoryId(0);
        }
        return productDetailVo;
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList=productMapper.selectAll();
        List<ProductListVo> productListVoList=Lists.newArrayList();
        if(productList!=null&&productList.size()>0){
            for(Product product:productList){
                ProductListVo productListVo=assembleProductListVo((product));
                productListVoList.add(productListVo);
            }
        }
        PageInfo pageInfo=new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo=new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setSubtitle(product.getSubtitle());
        return productListVo;
    }

    @Override
    public ServerResponse search(Integer productId,
                                 String productName,
                                 Integer pageNum,
                                 Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if(productName!=null&&!productName.equals("")){
            productName="%"+productName+"%";
        }
        List<Product> productList=productMapper.findProductByProductIdAndProductName(productId,productName);
        List<ProductListVo> productListVoList=Lists.newArrayList();
        if(productList!=null&&productList.size()>0){
            for(Product product:productList){
                ProductListVo productListVo=assembleProductListVo((product));
                productListVoList.add(productListVo);
            }
        }
        PageInfo pageInfo=new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse upload(MultipartFile file, String path) {
        //获取图片名称
        String orignalFileName=file.getOriginalFilename();
        //获取图片的扩展名
        String exName=orignalFileName.substring(orignalFileName.lastIndexOf("."));//.jsp
        //为图片生成新的唯一的名字
        String newFileName=UUID.randomUUID().toString()+exName;
        File pathFile=new File(path);
        if(!pathFile.exists()){
            pathFile.setWritable(true);
            pathFile.mkdirs();
        }
        File file1=new File(path,newFileName);
        try {
            file.transferTo(file1);
            //上传到图片服务器
            //。。。
            Map<String,String> map=Maps.newHashMap();
            map.put("uri",newFileName);
            map.put("url",PropertiesUtils.readByKey("imageHost")+"/"+newFileName);
            return ServerResponse.createBySuccess(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ServerResponse detail_portal(Integer productId) {
        //step1:参数非空校验
        if(productId==null){
            return ServerResponse.createByError("商品id参数不能为空");
        }
        //step2：查询product
        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByError("商品不存在或已下架");
        }
        //step3：校验商品状态
        if(product.getStatus()!=Const.ProductStatusEnum.PRODUCT_ONLINE.getCode()){
            return ServerResponse.createBySuccess("商品已下或删除");
        }
        //step4：获取productDetailVo
        ProductDetailVo productDetailVo=assembleProductDetailVO(product);
        //step5：返回结果
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse list_portal(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        //step1:参数校验categoryId和keyword不能为空
        if(categoryId==null&&(keyword==null||keyword.equals(""))){
            return ServerResponse.createByError("参数错误");
        }
        //step2:categoryId
        Set<Integer> integerSet=Sets.newHashSet();
        if(categoryId!=null){
            Category category=categoryMapper.selectByPrimaryKey(categoryId);
            if(category==null&&(keyword==null||keyword.equals(""))){
                //说明没有商品数据
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList=Lists.newArrayList();
                PageInfo pageInfo=new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            ServerResponse serverResponse= categoryService.get_deep_category(categoryId);
            if(serverResponse.isSuccess()){
                integerSet=(Set<Integer>) serverResponse.getData();
            }
        }
        //step3:keyword
        if(keyword!=null&&!keyword.equals("")){
            keyword="%"+keyword+"%";
        }
        if(orderBy.equals("")){
            PageHelper.startPage(pageNum,pageSize);
        }else {
            String[] orderByArr=orderBy.split("_");
            if(orderByArr.length>1){
                PageHelper.startPage(pageNum,pageSize,orderByArr[0]+" "+orderByArr[1]);
            }else {
                PageHelper.startPage(pageNum,pageSize);
            }
        }
        //step4:List<Product>-->list<ProductListVo>
        List<Product> productList= productMapper.searchProduct(integerSet,keyword);
        List<ProductListVo> productListVoList=Lists.newArrayList();
        if(productList!=null&&productList.size()>0){
            for(Product product:productList){
                ProductListVo productListVo=assembleProductListVo(product);
                productListVoList.add(productListVo);
            }
        }
        //step5:分页
        PageInfo pageInfo=new PageInfo();
        pageInfo.setList(productListVoList);
        //step6:返回
        return ServerResponse.createBySuccess(pageInfo);
    }
}
