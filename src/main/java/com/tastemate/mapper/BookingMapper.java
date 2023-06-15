package com.tastemate.mapper;

import com.tastemate.domain.BookingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookingMapper {

    public List<BookingVO> bookingList();

    public int bookingInsert(BookingVO bookingVO);

    public BookingVO bookingToPay(int bookingIdx);

    public int bookingTableCheck(BookingVO bookingVO);

    public int bookingPayAndStarComplete(int bookingIdx);
    public int bookingPayCancel(int bookingIdx);

}
