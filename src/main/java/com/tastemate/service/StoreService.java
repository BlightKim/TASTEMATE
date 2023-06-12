package com.tastemate.service;

import com.tastemate.domain.StarVO;
import com.tastemate.domain.StoreVO;
import com.tastemate.domain.paging.Criteria;
import com.tastemate.mapper.StoreMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class StoreService {

    @Autowired
    private StoreMapper mapper;

    @Value("${file.dir}")
    private String fileDir;



    public List<StoreVO> store_getList(Map<String, Object> orderMap) {

        //List<StoreVO> storeList = mapper.store_getList();
        List<StoreVO> storeList = mapper.store_getList_withStar_withPaging(orderMap);

        return  storeList;
    }

    public int store_totalCnt(Map<String, Object> orderMap) {

        return mapper.getTotalCount(orderMap);
    }

    public StoreVO store_get(int storeIdx) {

        //StoreVO storeVO = mapper.store_get(storeIdx);
        StoreVO storeVO = mapper.getStoreWithMenu(storeIdx);

        return storeVO;
    }

    public StoreVO store_getWithStar(int storeIdx) {

        StoreVO storeVO = mapper.getStoreWithStar(storeIdx);

        return storeVO;
    }

    public StoreVO store_getWithComment(int storeIdx) {

        StoreVO storeVO = mapper.getStoreWithComment(storeIdx);

        return storeVO;
    }
    public StoreVO getStoreHighestStar() {
        List<StoreVO> list = mapper.getStoreHighestStar();
        StoreVO storeVO = list.get(0);

        return storeVO;
    }


    public void saveFile(StoreVO storeVO, MultipartFile multipartFile) {

        String savedName = null;

        if(multipartFile.isEmpty()){
            savedName = "tastemate.jpg";

        } else {

        String oriFilename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        String extension = oriFilename.substring(oriFilename.lastIndexOf("."));

        oriFilename = oriFilename.substring(oriFilename.lastIndexOf("\\")+1);

        savedName = uuid + "_" + oriFilename;

        String savedPath = fileDir + "/" +savedName;
       
        log.info(savedPath);

        File saveFile = new File(savedPath);

        try {
            multipartFile.transferTo(saveFile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        } //else 끝


        StoreVO storeVO1 = new StoreVO();


        storeVO1.setFilename(savedName);
        //storeVO1.setFilename(savedPath);
        storeVO1.setUserIdx(storeVO.getUserIdx());
        storeVO1.setStoreName(storeVO.getStoreName());
        storeVO1.setCategory1(storeVO.getCategory1());
        storeVO1.setStoreAddress(storeVO.getStoreAddress());
        storeVO1.setStoreLati(storeVO.getStoreLati());
        storeVO1.setStoreLongi(storeVO.getStoreLongi());
        storeVO1.setPhoneNumber(storeVO.getPhoneNumber());




        /* 거리 계산 */
        // 학원과 맛집 거리를 계산합니다.
        double lat1 = 37.49877828305107;
        double lon1 = 127.0316730592617;
        double lat2 = storeVO.getStoreLati();
        double lon2 = storeVO.getStoreLongi();

        double distance = distance(lat1, lon1, lat2, lon2);
        log.info("두 지점 간의 거리: " + distance + "km");

        storeVO1.setDistance(distance*1000);

        log.info("Service 저장될 storeVO1 : " + storeVO1);

        int result = mapper.store_register(storeVO1);

    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // 지구 반지름 (단위: km)
        double dLat = deg2rad(lat2 - lat1);
        double dLon = deg2rad(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c; // 두 지점 간의 거리 (단위: km)
        return distance;
    }

    public static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }






    public int store_delete(int storeIdx) {

        int result = mapper.store_delete(storeIdx);

        return result;
    }

    public void updateFile(StoreVO storeVO, MultipartFile multipartFile) {


        StoreVO storeVO1 = new StoreVO();
        String savedName = null;


        if (multipartFile.getOriginalFilename().equals("")){
            //새 파일 없을때
            savedName = storeVO.getFilename();

        } else if(multipartFile.getOriginalFilename() != null) {

            File saveFile = new File(storeVO.getFilename());

            if (saveFile.exists()){
                saveFile.delete();
            }

            String oriFilename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String extension = oriFilename.substring(oriFilename.lastIndexOf("."));

            oriFilename = oriFilename.substring(oriFilename.lastIndexOf("\\")+1);

            savedName = uuid + "_" + oriFilename;

            String savedPath = fileDir + "/" +savedName;

            log.info(savedPath);

            saveFile = new File(savedPath);

            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }


        storeVO1.setFilename(savedName);
        //storeVO1.setFilename(savedPath);
        storeVO1.setStoreIdx(storeVO.getStoreIdx());
        storeVO1.setUserIdx(storeVO.getUserIdx());
        storeVO1.setStoreName(storeVO.getStoreName());
        storeVO1.setCategory1(storeVO.getCategory1());
        storeVO1.setStoreAddress(storeVO.getStoreAddress());
        storeVO1.setStoreLati(storeVO.getStoreLati());
        storeVO1.setStoreLongi(storeVO.getStoreLongi());
        storeVO1.setPhoneNumber(storeVO.getPhoneNumber());

        /* 거리 계산 */
        // 학원과 맛집 거리를 계산합니다.
        double lat1 = 37.49877828305107;
        double lon1 = 127.0316730592617;
        double lat2 = storeVO.getStoreLati();
        double lon2 = storeVO.getStoreLongi();

        double distance = distance(lat1, lon1, lat2, lon2);
        log.info("두 지점 간의 거리: " + distance + "km");

        storeVO1.setDistance(distance*1000);

        log.info("Service 잘 변환되었나?" + storeVO1);

        int result = mapper.store_update(storeVO1);
    }

    public int store_starComment(StarVO starVO) {

        int result = mapper.store_starComment(starVO);

        return result;
    }


}
