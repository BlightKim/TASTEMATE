package com.tastemate.mapper;

import com.tastemate.domain.StarVO;
import com.tastemate.domain.StoreVO;
import com.tastemate.domain.paging.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StoreMapper {

    //List<StoreVO> store_getList();
    List<StoreVO> store_getList_withStar_withPaging(Map<String,Object> orderMap);
    int getTotalCount(Map<String,Object> orderMap);
    StoreVO store_get(int storeIdx);



    int store_register(StoreVO storeVO);
    int store_update(StoreVO storeVO1);
    int store_delete(int storeIdx);

    int store_starComment(StarVO starVO);



    //join
    StoreVO getStoreWithMenu(int storeIdx);
    StoreVO getStoreWithComment(int storeIdx);
    StoreVO getStoreWithStar(int storeIdx);

    //main
    List<StoreVO> getStoreHighestStar();



}
